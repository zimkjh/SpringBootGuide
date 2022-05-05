package zerobase.weather.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zerobase.weather.domain.Weather;
import zerobase.weather.repository.WeatherRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {
    @Value("${openweathermap.key}")
    private String apiKey;
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Scheduled(cron = "0 0 1 * * *")
//    @Scheduled(cron = "0 0/2 * * * *")
    public void saveWeather() {
        System.out.println("saved weather");
        // open weather map 에서 날씨 데이터 가져오기
        String weatherData = getWeatherString("seoul");
        // 날씨 json 파싱하기
        Map<String, Object> parsedWeather = parseWeather(weatherData);
        // 우리 db에 넣기
        Weather nowWeather = new Weather();
        nowWeather.setMain(parsedWeather.get("main").toString());
        nowWeather.setIcon(parsedWeather.get("icon").toString());
        nowWeather.setTemp((Double) parsedWeather.get("temp"));
        weatherRepository.save(nowWeather);
    }

    private String getWeatherString(String city) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        // 이 위에 주의해서 쳐야함.. & 까먹거나 할 수 있음 프린트해서 확인해보기
//        System.out.println(apiUrl);
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
//            System.out.println(responseCode);
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (Exception e) {
            return "failed to get response";
        }
    }

    private Map<String, Object> parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> resultMap = new HashMap<>();
        JSONObject mainData = (JSONObject) jsonObject.get("main");
        resultMap.put("temp", mainData.get("temp"));
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));
        return resultMap;
    }
}
