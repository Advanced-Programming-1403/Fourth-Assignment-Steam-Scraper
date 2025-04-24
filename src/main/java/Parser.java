import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;

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

        File input = new File("D:/AP/Fourth-Assignment-Steam-Scraper/src/Resources/Video_Games.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements gameElements = doc.select("div.game");

        for (Element gameElement : gameElements) {
            String name = gameElement.select("h3.game-name").text();

            String ratingText = gameElement.select("span.game-rating").text().replace("/5", "").trim();
            double rating = Double.parseDouble(ratingText);

            String priceText = gameElement.select("span.game-price").text().replace("€", "").trim();
            int price = Integer.parseInt(priceText);

            Game game = new Game(name, rating, price);
            games.add(game);
        }
    }
    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            parser.setUp();
            List<Game> sortedByName = parser.sortByName();
            System.out.println("Sorted by Name: " + sortedByName);

            List<Game> sortedByRating = parser.sortByRating();
            System.out.println("Sorted by Rating: " + sortedByRating);

            List<Game> sortedByPrice = parser.sortByPrice();
            System.out.println("Sorted by Price: " + sortedByPrice);

        } catch (IOException e) {
            System.err.println("Error parsing HTML file: " + e.getMessage());
        }
    }
}
