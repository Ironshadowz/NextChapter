package MiniProject.NextChapter_back.Service;

import java.io.IOException;
import java.net.URL;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService 
{
    @Autowired
    private LinkServiceShangHaiFantasy shangHai;

    @Autowired
    private LinkLuminaryNovelService luminary;

    public String getDomain(String url)
    {
        String domain = "";
        try 
        {
            // Parse the URL using java.net.URL.
            URL urlObject = new URL(url);
            // Extract the domain (host) from the URL.
            domain = urlObject.getHost();
            // Remove "www." if it exists.
            domain = domain.startsWith("www.") ? domain.substring(4) : domain;
            // Print the extracted domain.
            System.out.println("LinkService Domain: " + domain);
            
            return domain;
        } catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        }
    }
    public String getTitle(String domain, String url) throws IOException
    {
        String title="";
        switch(domain)
        {
            case "shanghaifantasy.com": 
                title=shangHai.getShangHaiNovelTitleFromMainPage(url);
                break;
            case "luminarynovels.com":
                title=luminary.getLuminaryTitleFromMainPage(url);
                break;
        }
        return title;
    }
    public String getNextUrl(String domain, String currentChapterUrl) throws IOException
    {
        String nextChapter="";
        switch(domain)
        {
            case "shanghaifantasy.com": 
                nextChapter=shangHai.getNextShangHaiChapter(currentChapterUrl);
                break;
            case "luminarynovels.com":
                nextChapter=luminary.getNextLuminaryNovels(currentChapterUrl);
                break;
        }
        return nextChapter;
    }
    public String getImage(String domain, String currentChapterUrl) throws IOException
    {
        String imageUrl="";
        String mainPage="";
        switch(domain)
        {
            case "shanghaifantasy.com": 
                mainPage = shangHai.shangHaiNovelMainPage(currentChapterUrl); 
                imageUrl = shangHai.shangHaiGetImgUrlFromMainPage(mainPage);
                break;
            // case "luminarynovels.com":
                
            //     break;
        }
        return imageUrl;
    }
}
