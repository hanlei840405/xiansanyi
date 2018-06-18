package com.bird.framework.xsy.mall.view;

import com.bird.framework.xsy.mall.entity.JdCinema;
import com.bird.framework.xsy.mall.entity.JdRegion;
import com.bird.framework.xsy.mall.service.JdCinemaService;
import com.bird.framework.xsy.mall.service.JdRegionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class HomeView {
    @Value(value = "classpath:json.json")
    private Resource json;
    @Value(value = "classpath:jd.json")
    private Resource jd;

    @Autowired
    private JdRegionService jdRegionService;
    @Autowired
    private JdCinemaService jdCinemaService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("")
    public String home() throws IOException {
        List<String> jsonContents = Files.readLines(json.getFile(), Charset.forName("UTF-8"));
        List<String> jdContents = Files.readLines(jd.getFile(), Charset.forName("UTF-8"));
        StringBuffer json = new StringBuffer();
        for (String contend : jsonContents) {
            json.append(contend);
        }
        List<Map<String, String>> regions = new ArrayList<>();
        List<Map<String, Object>> maps = objectMapper.readValue(json.toString(), List.class);
        for (Map<String, Object> map : maps) {
            List<Map<String, Object>> subs = (List<Map<String, Object>>) map.get("sub");
            for (Map<String, Object> sub : subs) {
                List<Map<String, Object>> subSubs = (List<Map<String, Object>>) sub.get("sub");
                if (subSubs != null) {
                    for (Map<String, Object> subSub : subSubs) {
                        Map<String, String> region = new HashMap<>();
                        String city = sub.get("name").toString().replace("区", "").replace("县", "").replace("州", "").replace("旗", "");
                        if (!"市辖".equals(city)) {
                            city = city.replace("市", "");
                        }
                        String name = subSub.get("name").toString().replace("区", "").replace("县", "").replace("州", "").replace("旗", "");
                        if (!"市辖".equals(name)) {
                            name = name.replace("市", "");
                        }
                        region.put("parentName", city);
                        region.put("code", (String) subSub.get("code"));
                        region.put("name", name);
                        regions.add(region);
                    }
                }
            }
        }


        StringBuffer jd = new StringBuffer();
        for (String contend : jdContents) {
            jd.append(contend);
        }
        List<Map<String, Object>> jdMaps = objectMapper.readValue(jd.toString(), List.class);
        for (Map<String, Object> jdMap : jdMaps) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) jdMap.get("item");
            for (Map<String, Object> item : items) {
                List<Map<String, Object>> jdCities = (List<Map<String, Object>>) item.get("list");
                for (Map<String, Object> city : jdCities) {
                    int cityId = (int) city.get("id");
                    String cityName = city.get("name").toString().replace("区", "").replace("县", "").replace("州", "").replace("旗", "");
                    if (!"市辖".equals(cityName)) {
                        cityName = cityName.replace("市", "");
                    }
                    String finalCityName = cityName;
                    Map<String, String> jdRegion = parseHtml(cityId);

                    if (!jdRegion.isEmpty()) {
                        Runnable runnable = () -> {
                            for (Map<String, String> region : regions) {
                                String parentName = region.get("parentName");
                                String code = region.get("code");
                                String name = region.get("name");
                                if (finalCityName.equals(parentName)) {
                                    if (jdRegion.containsKey(name)) {
                                        JdRegion regionJd = new JdRegion();
                                        regionJd.setDistrictCode(code);
                                        regionJd.setJdCityCode(cityId + "");
                                        regionJd.setJdRegionCode(jdRegion.get(name));
                                        jdRegionService.save(regionJd);
                                    }
                                }

                            }
                            log.info("end...");
                        };
                        Thread thread = new Thread(runnable);
                        thread.start();
                    }
                }

            }
        }
        return "portal";
    }

    @RequestMapping("cinemas")
    public @ResponseBody String cinemas() throws IOException {
        List<JdRegion> jdRegions = jdRegionService.findAll();
        for (JdRegion jdRegion : jdRegions) {
            String cityCode = jdRegion.getJdCityCode();
            String regionCode = jdRegion.getJdRegionCode();
            Runnable runnable = () -> {
                try {
                    List<JdCinema> jdCinemas = parsJson(cityCode, regionCode);
                    jdCinemaService.save(jdCinemas);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.info("end...");
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        return "ok";
    }

    private Map<String, String> parseHtml(int code) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://movie.jd.com/" + code, String.class);
        Document document = Jsoup.parse(responseEntity.getBody());
        Elements elements = document.getElementsByClass("h-regionUl");
        Map<String, String> result = new HashMap<>();
        for (Element element : elements) {
            for (Element child : element.children()) {
                String text = child.text().replace("区", "").replace("县", "").replace("州", "").replace("旗", "");
                if (!"市辖".equals(text)) {
                    text.replace("市", "");
                }
                result.put(text, child.attr("vid"));
            }
        }
        return result;
    }

    private List<JdCinema> parsJson(String city, String region) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        log.info("抓取 {}, {}", city, region);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://movie.jd.com/json/seat/seat_getIndexCinemas.action?cityId=" + city + "&regionId=" + region, String.class);
        String cinemaDataStr = responseEntity.getBody();
        List<JdCinema> jdCinemas = new ArrayList<>();
        try {
            Map<String,Object> cinemaDataMap = objectMapper.readValue(cinemaDataStr.replace("�\\", "碶"), Map.class);
            List<Map<String,Object>> cinemaDatas = (List<Map<String, Object>>) cinemaDataMap.get("cinemaData");
            for (Map<String,Object> cinemaData : cinemaDatas) {
                String address = (String) cinemaData.get("padd");
                String name = (String) cinemaData.get("pname");
                int id = (int) cinemaData.get("pid");
                JdCinema jdCinema = new JdCinema();
                jdCinema.setCityCode(city);
                jdCinema.setRegionCode(region);
                jdCinema.setCode(id + "");
                jdCinema.setName(name);
                jdCinema.setAddress(address);
                jdCinemas.add(jdCinema);
            }
        }catch (Exception e) {
            log.error("解析数据出错, city: {}, region: {}", city, region);
        }
        return jdCinemas;
    }
}
