package MiniProject.NextChapter_back.Model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Watchlist 
{
    String listId;
    String link;
    String name;
    Date updateDate;
    String title;
}
