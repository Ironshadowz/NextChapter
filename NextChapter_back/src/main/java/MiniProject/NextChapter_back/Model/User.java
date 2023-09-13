package MiniProject.NextChapter_back.Model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User 
{
    private String id;
    private String name;
    private String email;
    private Date dateJoined;
    private String hash;
    private String country;
    private String city;
}