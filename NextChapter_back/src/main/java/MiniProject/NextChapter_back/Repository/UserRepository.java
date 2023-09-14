package MiniProject.NextChapter_back.Repository;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import MiniProject.NextChapter_back.Model.Setting;
import MiniProject.NextChapter_back.Model.User;

@Repository
public class UserRepository 
{
    @Autowired
    private JdbcTemplate template;

    private final String DEFAULT_SETTING = "insert into Setting (name, frontsize, weatherEffect) values (?, ?, ?)";
    private final String GET_USER_BY_NAME = "select * from User where name = ?";
    private final String SQL_INSERT_NEW_USER = "INSERT INTO User (name, email, dateJoined, hash, country, city) VALUES (?,?,?,?,?,?)";
    private final String UPDATE_SETTING = "update Setting set frontsize = ?, weatherEffect = ?, imageUrl = ? where name = ?";

    public User getUserByName(String name)
    {
        System.out.println(name);        
        List<User> user = template.query(GET_USER_BY_NAME, BeanPropertyRowMapper.newInstance(User.class), name);
        if(user.isEmpty())
        {
            return new User();
        }
        return user.get(0);
    }
    public Integer insertNewUser(User user) 
    {
         Date date = Date.valueOf(LocalDate.now());
        return template.update
        (
            SQL_INSERT_NEW_USER, 
            user.getName(),
            user.getEmail(),
            date,
            user.getHash(),
            user.getCountry(),
            user.getCity()
        );
    }
    public void defaultSetting(String username)
    {
        template.update(DEFAULT_SETTING, username, 24, false);
    }
    public Boolean updateSetting(Setting setting)
    {
        System.out.println("user repo updatesetting "+setting.getName());
        int x = template.update(UPDATE_SETTING, setting.getFrontsize(), setting.getWeatherEffect(), setting.getImageUrl(), setting.getName());
        return x>0 ? true : false;
    }
}
