package com.example.weatherApp.controller;

import com.example.weatherApp.model.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.List;

@Controller
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city, Model model) {
        logger.info("API Key: {}", apiKey);  // Log the API key
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city, apiKey);
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.get("cod") != null && response.get("cod").toString().equals("200")) {
            Weather weather = new Weather();
            weather.setCityName(response.get("name").toString());
            weather.setTemperature(((Map<String, Double>) response.get("main")).get("temp"));
            weather.setHumidity(((Map<String, Integer>) response.get("main")).get("humidity"));
            weather.setWindSpeed(((Map<String, Double>) response.get("wind")).get("speed"));
            weather.setDescription(((Map<String, Object>) ((List<Object>) response.get("weather")).get(0)).get("description").toString());

            model.addAttribute("weather", weather);
            return "weather";
        } else {
            throw new RuntimeException("Invalid API response");
        }
    }
}
