package MiniProject.NextChapter_back.Service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class LinkLuminaryNovelService 
{
    public String getNextLuminaryNovels(String url) throws IOException
    {
        try
        {
            Document document = Jsoup.connect(url)
            //.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                    .get();
            System.out.println("Got HTML here");
            Elements divElements = document.select("body div div div div div div div div div div div div div div div.nav-next");
            System.out.println("Checking divElement");
            if(divElements.equals(null))
            {
                System.out.println("No new chapter");
                return null;
            } else
            {
                Element divElement = divElements.first();
                Elements aElements = divElement.select("a");
                System.out.println("Text in <a> "+aElements.get(0).text());
            
                Element thirdAElement = aElements.get(0); 
                String linkText = thirdAElement.text();
                String linkHref = thirdAElement.attr("href");
                System.out.println("Link Text: " + linkText);
                System.out.println("Link Href: " + linkHref);
                return linkHref;
            }
        } catch (Exception e) 
        {
            System.out.println("No new chapter");
            return null;
        }
    }
    public String getLuminaryTitleFromMainPage(String url) throws IOException
    {
        Document document = Jsoup.connect(url)
               //.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .get();
            // Fetch the website's HTML content
            System.out.println("Got HTML here");
            Elements olElement = document.select("body div div div div div div div div div div div div div div div ol");
            System.out.println("Checking divElement");
            Elements liElements = olElement.select("li:eq(1)");
            // Check if any matching elements were found.
            if (!liElements.isEmpty()) 
            {
                // Access the first matching <li> element.
                Element secondLiElement = liElements.first();
                // Get the text content of the <li> element.
                String liText = secondLiElement.text();
                // Print the text content.
                System.out.println("Title: " + liText);
                return liText;
            } else 
            {
                System.out.println("No title found.");
                return null;
            }
    }

}
