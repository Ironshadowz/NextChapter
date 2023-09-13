package MiniProject.NextChapter_back.Service;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import MiniProject.NextChapter_back.Exceptions.AppException;
import MiniProject.NextChapter_back.Model.Setting;
import MiniProject.NextChapter_back.Model.User;
import MiniProject.NextChapter_back.Model.UserDto;
import MiniProject.NextChapter_back.Records.LoginDto;
import MiniProject.NextChapter_back.Records.SignUpDto;
import MiniProject.NextChapter_back.Repository.S3Repository;
import MiniProject.NextChapter_back.Repository.SQLRepository;
import MiniProject.NextChapter_back.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService 
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SQLRepository SQLRepo;
    @Autowired
    private S3Repository s3Repo;
    @Autowired
    private WeatherService weatherServ;

    public UserDto login(LoginDto loginDto) 
    {
        User user = userRepository.getUserByName(loginDto.username());
                                    //.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(loginDto.password()), user.getHash())) 
        {
            return new UserDto(user.getId(), user.getName(), "");
        } else
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public User register(SignUpDto signUpDto) 
    {
        User optionalUser = userRepository.getUserByName(signUpDto.username());
        // You were implement the above repo's method
        if (optionalUser.getName()!=null) 
        {
            System.out.println("Userservice user exist");
            User user = new User();
            user.setName("");
            return user;
        }
        System.out.println("UserService register");
        User user = new User(null, signUpDto.username(), signUpDto.email(), null, signUpDto.password1().toString(), signUpDto.country(), signUpDto.city());
        user.setHash(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password1())));
        if (userRepository.insertNewUser(user) > 0) 
        {
            userRepository.defaultSetting(signUpDto.username());
            return user;
        }
        else 
        {
            throw new AppException("User " + user.getName() + " was not successfully created", HttpStatus.BAD_REQUEST);
        }
    }
    public User getUser(String username)
    {
        User optionalUser = userRepository.getUserByName(username);
        return optionalUser;
    }
    public Setting getUserSetting(String name)
    {
        Setting setting = SQLRepo.getUserSetting(name);
        if(setting.getWeatherEffect()==true)
        {
            User user = userRepository.getUserByName(name);
            String weather = weatherServ.searchWeather(user.getCity());
            setting.setWeather(weather);
        }
        return setting;
    }
    public Boolean updateUserSetting(Setting setting, MultipartFile image)
    {
        String uuid= s3Repo.saveFile(image);
        String url = "https://somethinguphere.sgp1.digitaloceanspaces.com/"+uuid;
        setting.setImageUrl(url);
        return userRepository.updateSetting(setting);
    }
    // public Integer registerUser(User data)
    // {
    //     System.out.println("Service registerUser");
        
    //     java.util.Date utilDate = new java.util.Date();
    //     java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
    //     data.setDateJoined(sqlDate);

    //     Integer userId = sqlRepo.createUser(data);
    //     sqlRepo.defaultSetting(data.getName());
    //     return userId;
    // }
    // public Boolean authenuser(String data)
    // {
    //     JsonObject o = jServ.toJson(data);
    //     String name = o.getString("username");
    //     String password = o.getString("password");
    //     System.out.println("Service authenuser");
    //     String userPass = sqlRepo.getUserHash(name);
    //     //Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);
    //     System.out.println("Service authenuser");
    //     //Boolean authenticate = encoder.matches(password, userPass);
    //     if(userPass.equals(password))
    //     return true;
    //     else
    //     return false;
    // }
}