import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {
    static List<Game> games = new ArrayList<>();

    // Sort by Name (A-Z)
    public List<Game> sortByName() {
        List<Game> sortedByName = new ArrayList<>(games);
        sortedByName.sort(Comparator.comparing(Game::getName));
        return sortedByName;
    }

    // Sort by Rating (Highest First)
    public List<Game> sortByRating() {
        List<Game> sortedByRating = new ArrayList<>(games);
        sortedByRating.sort(Comparator.comparingDouble(Game::getRating).reversed());
        return sortedByRating;
    }

    // Sort by Price (Highest First)
    public List<Game> sortByPrice() {
        List<Game> sortedByPrice = new ArrayList<>(games);
        sortedByPrice.sort(Comparator.comparingInt(Game::getPrice).reversed());
        return sortedByPrice;
    }

    // Parse the HTML file and populate game list
    public void setUp() throws IOException {
        File input = new File("src/Resources/Video_Games.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements gameElements = doc.select("div.game"); // Adjust this selector if needed

        for (Element gameElement : gameElements) {
            String name = gameElement.select("span.game-name").text();
            String ratingText = gameElement.select("span.game-rating").text();
            String priceText = gameElement.select("span.game-price").text();

            double rating = Double.parseDouble(ratingText);  // Assumes rating is in a valid format
            int price = Integer.parseInt(priceText.replaceAll("[^\\d]", "")); // Remove $ sign etc.

            Game game = new Game(name, rating, price);
            games.add(game);
        }
    }


    public static void main(String[] args) {
        Parser parser = new Parser();

        try {
            parser.setUp();

            System.out.println("Sorted by Name:");
            List<Game> sortedByName = parser.sortByName();
            sortedByName.forEach(game -> System.out.println(game.getName()));

            System.out.println("\nSorted by Rating (Highest First):");
            List<Game> sortedByRating = parser.sortByRating();
            sortedByRating.forEach(game -> System.out.println(game.getName() + " - " + game.getRating()));

            System.out.println("\nSorted by Price (Highest First):");
            List<Game> sortedByPrice = parser.sortByPrice();
            sortedByPrice.forEach(game -> System.out.println(game.getName() + " - $" + game.getPrice()));

        } catch (IOException e) {
            System.out.println("Error during parsing: " + e.getMessage());
        }
    }
}
