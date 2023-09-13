package MiniProject.NextChapter_back.Service;

import java.io.StringReader;

import org.springframework.stereotype.Service;

import MiniProject.NextChapter_back.Model.NewUser;
import MiniProject.NextChapter_back.Model.User;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class Utility 
{
    public JsonObject toJson(String data)
    {
        JsonReader jsonReader = Json.createReader(new StringReader(data));
        JsonObject o = jsonReader.readObject();
        return o;
    }
    public User convertuser(NewUser data)
    {
        User user = new User();
        user.setCity(data.getCity());
        user.setCountry(data.getCountry());
        user.setEmail(data.getEmail());
        user.setHash(data.getPassword1());
        user.setName(data.getUsername());
        return user;
    }
}
