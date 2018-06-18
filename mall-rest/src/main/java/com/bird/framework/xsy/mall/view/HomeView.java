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

        return "portal";
    }

    @RequestMapping("cinemas")
    public @ResponseBody String cinemas() throws IOException {
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
