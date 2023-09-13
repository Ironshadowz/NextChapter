package MiniProject.NextChapter_back.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUser 
{
    private String username;
    private String email;
    private String password1;
    private String password2;
    private String country;
    private String city;
}
