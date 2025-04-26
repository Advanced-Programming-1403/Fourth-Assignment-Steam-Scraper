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

        // Sort games alphabetically by name
        sortedByName.sort(Comparator.comparing(Game::getName));

        return sortedByName;
    }

    public List<Game> sortByRating(){
        List<Game> sortedByRating = new ArrayList<>(games);

        // Sort games by rating (most)
        sortedByRating.sort(Comparator.comparing(Game::getRating).reversed());

        return sortedByRating;
    }

    public List<Game> sortByPrice(){
        List<Game> sortedByPrice = new ArrayList<>(games);

        // Sort games by price (most)
        sortedByPrice.sort(Comparator.comparing(Game::getPrice).reversed());

        return sortedByPrice;
    }

    public void setUp() throws IOException {

        //Parse the HTML file using Jsoup
        File input = new File("C:\\Users\\Notebook\\Ap-course\\Fourth-Assignment-Steam-Scraper\\src\\Resources\\Video_Games.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        // Extract data from the HTML
        Elements gameElements = doc.select("div.col-md-4.game");

        // Iterate through each Game div to extract Game data
        for (Element gameElement : gameElements) {
            String title = gameElement.selectFirst("h3.game-name").text();

            String priceText = gameElement.selectFirst("span.game-price").text(); // "91 €"
            int price = Integer.parseInt(priceText.replaceAll("[^\\d]", "")); // 91

            String ratingText = gameElement.selectFirst("span.game-rating").text(); // "4.8/5"
            double rating = Double.parseDouble(ratingText.split("/")[0]); // 4.8

            // Create a Game object using the extracted data
            Game game = new Game(title, rating, price);

            // Add the game to the static list
            games.add(game);
        }
    }

    public static void main(String[] args) {
        //you can test your code here before you run the unit tests
    }
}