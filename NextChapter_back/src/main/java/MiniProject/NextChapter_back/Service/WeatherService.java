package MiniProject.NextChapter_back.Service;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WeatherService 
{
    @Value("${apiKey}")
    private String apiKey;

    String apiURL = "https://api.openweathermap.org/data/2.5/weather";

    public String searchWeather(String city)
    {
        // building the url to prepare to use below
         String url = UriComponentsBuilder
                .fromUriString(apiURL)
                .queryParam("appid", apiKey)
                .queryParam("q", city)
                .queryParam("units", "metric")
                .toUriString();
        System.out.println(url);
        // why need <Void> ?? 
        // using the resttemplate to call the api
        System.out.println(city);
        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(req, String.class);

        // converting from RE object -> JsonObject
        JsonReader reader = Json.createReader(new StringReader(response.getBody()));
        JsonObject result = reader.readObject();
        JsonArray weather = result.getJsonArray("weather");
        JsonObject main = result.getJsonObject("main");
        JsonObject wind = result.getJsonObject("wind");

        // building the jsonobject of properties that we want selectively 
        JsonObject weatherObj = Json.createObjectBuilder()
                                .add("main", weather.getJsonObject(0).getString("main"))
                                .add("temperature", main.getJsonNumber("temp"))
                                .add("humidity", main.getJsonNumber("humidity"))
                                .add("wind_speed", wind.getJsonNumber("speed"))
                                .add("wind_degree", wind.getJsonNumber("deg"))
                                .build();

        System.out.println(weatherObj.getString("main"));
        return weatherObj.getString("main");
    }

   
}