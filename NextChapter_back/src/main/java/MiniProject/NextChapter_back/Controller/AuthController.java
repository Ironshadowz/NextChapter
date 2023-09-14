package MiniProject.NextChapter_back.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MiniProject.NextChapter_back.Config.UserAuthProvider;
import MiniProject.NextChapter_back.Model.User;
import MiniProject.NextChapter_back.Model.UserDto;
import MiniProject.NextChapter_back.Records.LoginDto;
import MiniProject.NextChapter_back.Records.SignUpDto;
import MiniProject.NextChapter_back.Service.UserService;


@RestController
public class AuthController 
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthProvider userAuthProvider;
    
    @PostMapping("/authenticate")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto) 
    {
        System.out.println("Controller authen");
        UserDto user = userService.login(loginDto);
        if(user.getUsername()==null)
        {
            System.out.println("User exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        System.out.println("After getting user");
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }
    @PostMapping("/registeruser")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) 
    {
        User user = userService.register(signUpDto);
        if(user.getName()=="")
        {
            System.out.println("AuthController User exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        UserDto createdUser = new UserDto();
        createdUser.setToken(userAuthProvider.createToken(new UserDto(user.getId(), user.getName(), "")));
        System.out.println(createdUser);
        return ResponseEntity.ok(createdUser);
    }
}