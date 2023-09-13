package MiniProject.NextChapter_back.Service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceShangHaiFantasy 
{

    public String shangHaiNovelMainPage(String url) throws IOException
    {
        System.out.println("Get Shanghai Main page");
        Document document = Jsoup.connect(url)
        //.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
         .get();
        // Fetch the website's HTML content
        System.out.println("Got HTML here");
        Elements section = document.select("body div section");
        Elements anchorElements = section.select("a");

        // Check if there is a second anchor element
        if (anchorElements.size() >= 2) {
         // Access the second anchor element
         Element secondAnchorElement = anchorElements.get(1);

         // Access attributes or perform actions on the second anchor element
         String href = secondAnchorElement.attr("href");
         System.out.println("Second Anchor Href: " + href);
         return href;
        } else 
        {
            return null;
        }
    }
    public String getNextShangHaiChapter(String url) throws IOException
    {
        System.out.println("ShangHai Service getNextChapter");
        Document document = Jsoup.connect(url)
               //.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .get();
            // Fetch the website's HTML content
            System.out.println("Got HTML here");
            Elements sectionElements = document.select("body div section");
            System.out.println("Checking sectionElement");
            Elements aElements = sectionElements.select("a");
            if (aElements.size() > 2) 
            {
                System.out.println(aElements.get(2).text());
                System.out.println(aElements.get(0).text());
                if(aElements.get(2).text().equals(aElements.get(0).text()))
                {
                    System.out.println("No new chapter");
                    return null;
                } else
                {
                    Element thirdAElement = aElements.get(2); // Index 2 for the 3rd <a>.
                    String linkText = thirdAElement.text();
                    String linkHref = thirdAElement.attr("href");
                    System.out.println("Link Text: " + linkText);
                    System.out.println("Link Href: " + linkHref);
                    return linkHref;
                }
            } else 
            {
                System.out.println("No new chapter");
                return null;
            }
    }
    public String getShangHaiNovelTitleFromMainPage(String url)
    {
        try
        {
            System.out.println("ShangHaiService getNovelTitleFromMainPage");
            Document document = Jsoup.connect(url)
               //.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .get();
            // Fetch the website's HTML content
            System.out.println("Got HTML here");
            Elements divElement = document.select("body div div");
            System.out.println("Checking divElement");
            Element nestedDivElement = divElement.first();
            // Get the text content of the nested <div>.
            String title = nestedDivElement.text();
            // Print the text content.
            System.out.println("Title: " + title);
            return title;
        } catch (Exception e) 
        {
            System.out.println("Error");
            e.printStackTrace();
            return null;
        }
    }
    public String shangHaiGetImgUrlFromMainPage(String mainPageUrl) throws IOException
    {
        Document document = Jsoup.connect(mainPageUrl)
               //.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .get();
            // Fetch the website's HTML content
            System.out.println("Got HTML here");
            Elements div = document.select("body div");
            Element img = div.select("img").first();

            // Access the src attribute of the img element
            String src = img.attr("src");
            System.out.println("Image Src: " + src);
            return src;
    }
}
