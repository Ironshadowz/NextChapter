package MiniProject.NextChapter_back.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import MiniProject.NextChapter_back.Model.AddChapter;
import MiniProject.NextChapter_back.Model.Setting;
import MiniProject.NextChapter_back.Model.UpdateChapter;
import MiniProject.NextChapter_back.Model.User;
import MiniProject.NextChapter_back.Service.WeatherService;
import MiniProject.NextChapter_back.Service.MainService;
import MiniProject.NextChapter_back.Service.UserService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping
public class MainController 
{
    @Autowired
    private MainService mainServ;
    @Autowired
    private UserService userServ;
    @Autowired
    private WeatherService weatherServ;

    @PostMapping(path="/addLink/{username}")
    public ResponseEntity<?> addLink(@RequestBody String pack, @PathVariable("username") String username) throws IOException
    {
        Boolean result = mainServ.addChapterFromUser(pack, username);
        return ResponseEntity.ok(result);
        // else
        // return ResponseEntity.status(HttpStatus.CONFLICT).body("Item already exists");
    }
    @GetMapping(path="/home/{username}")
    public ResponseEntity<List<AddChapter>> getNewChapters(@PathVariable("username") String username) throws IOException
    {
        List<AddChapter> newChapters = mainServ.getNextChapter(username);
        return ResponseEntity.ok(newChapters);
    }
    @GetMapping(path="/watchlist/{username}")
    public ResponseEntity<List<AddChapter>> getWatchList(@PathVariable("username") String username) throws IOException
    {
        System.out.println("getWatchList in controller "+username);
        List<AddChapter> allChapters = mainServ.getAllChapters(username);
        return ResponseEntity.ok(allChapters);
    }
    @PutMapping(path="/updatechapter")
    public ResponseEntity<Boolean> updateChapter(@RequestBody UpdateChapter update)
    {
        String url = update.getUrl();
        String title = update.getTitle();
        String name = update.getName();
        System.out.println("Controller updateChapter "+title);
        Boolean result = mainServ.updateChapter(name, title, url);
        return ResponseEntity.ok(result);
    }
    @PutMapping(path="/delete/{username}")
    public ResponseEntity<Boolean> deleteChapter(@RequestBody String title, @PathVariable("username") String username)
    {
        Boolean result = mainServ.deleteChapter(username, title);
        if(result==true)
        return ResponseEntity.ok(result);
        else
        return ResponseEntity.internalServerError().build();
    }
    @GetMapping(path="/setting/{username}")
    public ResponseEntity<Setting> getSetting(@PathVariable("username") String name)
    {
        Setting currentSetting = userServ.getUserSetting(name);
        return ResponseEntity.ok(currentSetting);
    }
    @PutMapping(path="/setting/{username}")
    public ResponseEntity<Boolean> updateSetting(@PathVariable("username") String username, 
                            @RequestPart String frontSize, @RequestPart String weatherEffect, @RequestPart MultipartFile myfile)
    {
        Setting setting = new Setting();
        setting.setFrontsize(Integer.parseInt(frontSize));
        setting.setWeatherEffect(Boolean.parseBoolean(weatherEffect));
        setting.setName(username);
        System.out.println("maincontroller updatesetting setting: "+setting);
        Boolean updated = userServ.updateUserSetting(setting, myfile);
        return ResponseEntity.ok(updated);
    }
    @GetMapping(path="/weather")
    public ResponseEntity<String> getWeather(@RequestParam(required=false) String name)
    {
        System.out.println("Controller getWeather username: "+name);
        User currentUser = userServ.getUser(name);
        System.out.println("currentUser "+currentUser);
        String weather = weatherServ.searchWeather(currentUser.getCity());
        return ResponseEntity.ok(weather);
    }
    // @PostMapping(path="/registeruser")
    // public ResponseEntity<Boolean> registerUser(@RequestBody NewUser newUser)
    // {
    //     System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> hello");
    //     User user = uServ.convertuser(newUser);
    //     Integer userId = mainServ.registerUser(user);
    //     if(userId!=null)
    //     return ResponseEntity.ok(true);
    //     else
    //     return ResponseEntity.badRequest().body(false);
    // }   
    // @PostMapping(path="/authenticate")
    // public ResponseEntity<Boolean> authenUser(@RequestBody String input)
    // {
    //     System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> hello");
    //     Boolean result = mainServ.authenuser(input);
    //     if(result==true)
    //     return ResponseEntity.ok(result);
    //     else
    //     return ResponseEntity.status(404).body(result);
    // }
}
