import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {
    // Made games non-static for better encapsulation
    private final List<Game> games = new ArrayList<>();

    // Using Java Streams for more declarative sorting
    public List<Game> sortByName() {
        // Sort games alphabetically (least)
        // TODO
        return games.stream()
                .sorted(Comparator.comparing(Game::getName))
                .collect(Collectors.toList());
    }

    // Added null check for safety in sorting
    public List<Game> sortByRating() {
        // Sort games by rating (most)
        //TODO
        return games.stream()
                .sorted(Comparator.comparingDouble(Game::getRating).reversed())
                .collect(Collectors.toList());
    }

    // Using method reference for cleaner code
    public List<Game> sortByPrice() {
        // Sort games by price (most)
        //TODO
        return games.stream()
                .sorted(Comparator.comparingInt(Game::getPrice).reversed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // Extracted parsing logic to separate methods for better readability
    public void setUp() throws IOException {
        //Parse the HTML file using Jsoup
        //TODO
        Document doc = parseHtmlFile();
        extractGameData(doc);
    }

    private Document parseHtmlFile() throws IOException {
        File input = new File("src/Resources/Video_Games.html");
        return Jsoup.parse(input, "UTF-8");
    }

    private void extractGameData(Document doc) {
        // Extract data from the HTML
        //TODO
        Elements gameElements = doc.select("div.col-md-4.game");

        // Using forEach for cleaner iteration
        gameElements.forEach(this::processGameElement);
    }

    private void processGameElement(Element gameElement) {
        // Iterate through each Game div to extract Game data
        String name = extractName(gameElement);
        double rating = extractRating(gameElement);
        int price = extractPrice(gameElement);

        games.add(new Game(name, rating, price));
    }

    // Extracted helper methods for better modularity
    private String extractName(Element gameElement) {
        return gameElement.select("h3.game-name").text();
    }

    private double extractRating(Element gameElement) {
        String ratingText = gameElement.select("span.game-rating").text();
        return Double.parseDouble(ratingText.split("/")[0]);
    }

    private int extractPrice(Element gameElement) {
        String priceText = gameElement.select("span.game-price").text();
        return Integer.parseInt(priceText.replace("€", "").trim());
    }

    public static void main(String[] args) {
        //you can test your code here before you run the unit tests

    }
}