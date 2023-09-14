package MiniProject.NextChapter_back.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MiniProject.NextChapter_back.Model.AddChapter;
import MiniProject.NextChapter_back.Model.Watchlist;
import MiniProject.NextChapter_back.Repository.SQLRepository;


@Service
public class MainService 
{
    @Autowired
    private SQLRepository sqlRepo;
    @Autowired
    private LinkService linkServ;

    public List<AddChapter> getNextChapter(String username) throws IOException
    {
        System.out.println("Service getNextChapter "+username);
        List<Watchlist> urls = sqlRepo.getListByUsername(username);
        System.out.println("get urls");
        List<AddChapter> chapters = new ArrayList<>();
        for(Watchlist url : urls)
        {   
            if(!url.equals(null))
            {
                System.out.println(url);
                try
                {

                    String domain = linkServ.getDomain(url.getLink());
                    String nextChapter = linkServ.getNextUrl(domain, url.getLink());
                    if(nextChapter.equals(""))
                    {   
                        System.out.println("Chapter empty");
                        continue;
                    }
                    String title = linkServ.getTitle(domain, url.getLink());
                    AddChapter newChapter = new AddChapter();
                    String imageUrl = linkServ.getImage(domain, url.getLink());
                    System.out.println("Service getNextChapter imageURL: "+imageUrl);
                    System.out.println("Service getNextChapter nextchapter: "+nextChapter);
                    newChapter.setImage(imageUrl);
                    newChapter.setTitle(title);
                    newChapter.setUrl(nextChapter);
                    chapters.add(newChapter);
                } catch(Exception ex)
                {

                }
            } else
            System.out.println("url is null");
        }
        return chapters;
    }
    public List<AddChapter> getAllChapters(String username)
    {   
        System.out.println("Service getAllChapters");
        List<Watchlist> urls = sqlRepo.getListByUsername(username);
        List<AddChapter> chapters = new ArrayList<>();
         for(Watchlist url : urls)
        {   
            try
            {
                System.out.println("MainServ getAllChapter "+url);
                String domain = linkServ.getDomain(url.getLink());
                String title = linkServ.getTitle(domain, url.getLink());
                AddChapter newChapter = new AddChapter();
                String imageUrl = linkServ.getImage(domain, url.getLink());
                newChapter.setUrl(url.getLink());
                newChapter.setImage(imageUrl);
                newChapter.setTitle(title);
                chapters.add(newChapter);
            } catch(Exception ex)
            {

            }
        }
        return chapters;
    }
    public Boolean addChapterFromUser(String url, String username) throws IOException
    {
        System.out.println("Service addChapterFromUser");
        String domain = "";
        String title = "";
        Boolean titleExist=false;
        System.out.println(url);
        AddChapter d = new AddChapter();
        System.out.println("MainService addchapterfromuser: "+url);
        d.setUrl(url);// Replace with the URL of the website you want to inspect
        domain = linkServ.getDomain(url);
        title=linkServ.getTitle(domain, url);
        System.out.println("addchapterfromuser :"+title+" "+username);
        d.setTitle(title);
        List<Watchlist> currentChapters = sqlRepo.getListByUsername(username);
        for(Watchlist list : currentChapters)
        {
            System.out.println("Main Service addchapterfromuser title: "+list.getTitle());
            System.out.println(title);
            if(title.equals(list.getTitle()))
            {
                titleExist=true;
                System.out.println("Main Service addChapterFromUser title exists "+titleExist);
            }
        }
        try
        {   if(titleExist==false)
            {
                Boolean result = sqlRepo.insertChapter(d, username);
                return result;
            }
            return false;
        } catch(Exception ex)
        {
            return false;
        }
    }
    public Boolean updateChapter(String name, String title, String url)
    {
        Boolean result = sqlRepo.updateChapter(url, name, title);
        return result;
    }
    public Boolean deleteChapter(String name, String title)
    {
        System.out.println("Service deleteChapter");
        Boolean result = sqlRepo.deleteChapter(name, title);
        return result;
    }
}
