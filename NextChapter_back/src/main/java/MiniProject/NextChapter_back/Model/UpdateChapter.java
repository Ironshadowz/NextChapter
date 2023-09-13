package MiniProject.NextChapter_back.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateChapter 
{
    private String url;
    private String title;
    private String name;
}
