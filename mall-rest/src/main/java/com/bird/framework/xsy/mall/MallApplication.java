package com.bird.framework.xsy.mall;

import com.bird.framework.xsy.mall.config.ServiceConfig;
import com.bird.framework.xsy.mall.service.ansj.DicLibraryService;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import org.ansj.library.DicLibrary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@EnableTransactionManagement
@SpringBootApplication
@Import(ServiceConfig.class)
public class MallApplication {

    public static void main(String[] args) throws IOException {
//        System.out.println(HanLP.segment("微 信"));
//        DicLibraryService.analysis("近平特朗普");
//        RestTemplate restTemplate = new RestTemplate();
//        ObjectMapper objectMapper = new ObjectMapper();
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(MallApplication.class, args);
//        CityTimeService cityTimeService = configurableApplicationContext.getBean(CityTimeService.class);
//        TimeCinemaService timeCinemaService = configurableApplicationContext.getBean(TimeCinemaService.class);
//        List<CityTime> cityTimes = cityTimeService.all("0");
//        for (CityTime cityTime : cityTimes) {
//            ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://service.theater.mtime.com/Cinema.api?Ajax_CallBack=true&Ajax_CallBackType=Mtime.Cinema.Services&Ajax_CallBackMethod=GetOnlineTheatersInCity&Ajax_CallBackArgument0=" + cityTime.getTimeCode(), String.class);
//            String value = responseEntity.getBody().replace("var getGetOnlineTheatersInCityResult = ", "");
//            Map<String, Object> content = objectMapper.readValue(value, Map.class);
//
//            Map<String, Object> aaa = (Map<String, Object>) content.get("value");
//            List<Map<String, Object>> cinemas = (List<Map<String, Object>>) aaa.get("cinemas");
//            for (Map<String, Object> map : cinemas) {
//                String code = map.get("cinemaId").toString();
//                String name = map.get("name").toString();
//                String districtId = map.get("districtId").toString();
//                CityTime cityTime1 = cityTimeService.selectByTimeCode(districtId);
//                TimeCinema timeCinema = new TimeCinema();
//                if (cityTime1 != null) {
//                    timeCinema.setDistrictCode(cityTime1.getTimeCode());
//                }
//                timeCinema.setCityCode(cityTime.getTimeCode());
//                timeCinema.setCode(code);
//                timeCinema.setName(name);
//                timeCinemaService.save(timeCinema);
//            }
//        }

//        RoleService roleService = configurableApplicationContext.getBean(RoleService.class);
//        Role role = new Role();
//        role.setName("BUYER");
//        role.setStatus("1");
//        role.setVersion(1);
//        roleService.save(role);
//        Member user = new Member();
//        user.setFirstName("磊");
//        user.setLastName("韩");
//        user.setMobile("18615267773");
//        user.setNick("jesse");
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        user.setPassword(encoder.encode("123456"));
//        user.setStatus("1");
//        user.setVersion(1);
//        user.getRoles().add(role);
//        userService.save(user);
    }
}
