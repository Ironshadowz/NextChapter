package MiniProject.NextChapter_back.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddChapter 
{
    private String image;
    private String url;
    private String title;
}