package zerobase.weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import zerobase.weather.service.WeatherService;

@Controller
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/save/weather")
    void saveWeather() {
        weatherService.saveWeather();
    }
}
