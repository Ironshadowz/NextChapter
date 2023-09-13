package MiniProject.NextChapter_back.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setting 
{
    private String name;
    private Integer frontsize;
    private Boolean weatherEffect;
    private String imageUrl;
    private String weather;
}
