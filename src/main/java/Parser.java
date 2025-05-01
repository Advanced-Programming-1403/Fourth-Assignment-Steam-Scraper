import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Parser {
    static List<Game> games = new ArrayList<>();

    public List<Game> sortByName(){
        List<Game> sortedByName = new ArrayList<>(games);
        sortedByName.sort(Comparator.comparing(Game::getName));
        return  sortedByName;
    }

    public List<Game> sortByRating(){
        List<Game> sortedByRating = new ArrayList<>(games);
        sortedByRating.sort(Comparator.comparingDouble(Game::getRating).reversed());
        return sortedByRating;
    }

    public List<Game> sortByPrice(){
        List<Game> sortedByPrice = new ArrayList<>(games);
        sortedByPrice.sort(Comparator.comparingInt(Game::getPrice).reversed());
        return sortedByPrice;
    }

    public void setUp() throws IOException {
        File myf = new File("src/Resources/Video_Games.html");
        Document doc = Jsoup.parse(myf, "UTF-8");

        Elements gameEl = doc.select("div.game");

        for (Element element : gameEl) {
            String name = element.select("h3.game-name").text();
            String ratingText = element.select("span.game-rating").text().replace("/5", "").trim();
            String priceText = element.select("span.game-price").text().replace("€", "").trim();

            try {
                double rating = Double.parseDouble(ratingText);
                int price = Integer.parseInt(priceText);
                Game game = new Game(name, rating, price);
                games.add(game);
            } catch (NumberFormatException e) {
                System.out.println("Error in: " + name + "-----------------");
            }
        }
    }


    public static void main(String[] args) {

        
    }
}
