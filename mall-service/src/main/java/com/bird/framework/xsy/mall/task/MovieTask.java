package com.bird.framework.xsy.mall.task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class MovieTask {

    public void fetchMovie(String cinema) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://movie.jd.com/" + cinema, String.class);
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
    }
}
