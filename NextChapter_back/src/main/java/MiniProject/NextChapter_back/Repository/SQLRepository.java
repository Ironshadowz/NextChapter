package MiniProject.NextChapter_back.Repository;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import MiniProject.NextChapter_back.Model.AddChapter;
import MiniProject.NextChapter_back.Model.Setting;
import MiniProject.NextChapter_back.Model.User;
import MiniProject.NextChapter_back.Model.Watchlist;


@Repository
public class SQLRepository 
{
    @Autowired
    private JdbcTemplate template;

    private final String CREATE_USER = "insert into User values (null, ?, ?, ?, ?, ?, ?)";
    private final String INSERT_CHAPTER = "insert into Watchlist (link, name, updateDate, title) values ( ?, ?, ?, ?)";
    private final String GET_HASH = "select hash from User where name = ?";
    private final String GET_URLS = "select * from Watchlist where name = ?";
    private final String DELETE_CHAPTER = "delete from Watchlist where name = ? and title = ?";
    private final String UPDATE_CHAPTER = "update Watchlist set link = ? where title = ? and name = ?";
    private final String GET_SETTING = "select * from Setting where name = ?";
    
    public int createUser(User user)
    {
        KeyHolder generateKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() 
        {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException
            {
                PreparedStatement ps = con.prepareStatement(CREATE_USER, new String [] {"userId"});
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setDate(3, user.getDateJoined());
                ps.setString(4, user.getHash());
                ps.setString(5, user.getCountry());
                ps.setString(6, user.getCity());
                return null;
            }
        };
        template.update(psc, generateKey);
        Integer result = generateKey.getKey().intValue();
        return result;
    }
    public String getUserHash(String email)
    {
        String result = template.queryForObject(GET_HASH,String.class, email);
        System.out.println("SQLRepo "+result);
        return result;
    }
    public Boolean insertChapter(AddChapter payload, String username)
    {        
        System.out.println("Insert Chapter");
        Date date = Date.valueOf(LocalDate.now());
        System.out.println(date+" "+username);
        System.out.println(payload.getUrl()+' '+payload.getTitle());
        int num = template.update(INSERT_CHAPTER, payload.getUrl(), username, date, payload.getTitle());
        System.out.println("Chapter inserted");
        return num>0 ? true:false;
    }
    public List<Watchlist> getListByUsername(String username)
    {
        System.out.println("SQL geturlbyusername "+username);
        return template.query(GET_URLS, BeanPropertyRowMapper.newInstance(Watchlist.class), username);      
    }
    public Boolean deleteChapter(String name, String title)
    {
        int x = template.update(DELETE_CHAPTER, name, title);
        return x>0 ? true : false;
    }
    public Boolean updateChapter(String link, String name, String title)
    {
        int result = template.update(UPDATE_CHAPTER, link, title, name);
        return result>0 ? true: false;
    }
    public Setting getUserSetting(String username)
    {
        List<Setting> setList = template.query(GET_SETTING, BeanPropertyRowMapper.newInstance(Setting.class), username);
        return setList.get(0);
    }
}
