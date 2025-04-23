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
        return sortedByName;
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
        File input = new File("src/Resources/Video_Games.html"); // مسیر فایل رو بر اساس محل ذخیره‌سازی واقعی اصلاح کن
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements gameElements = doc.select("div.col-md-4.game");

        for (Element gameElement : gameElements) {
            String name = gameElement.selectFirst("h3.game-name").text();
            String ratingText = gameElement.selectFirst("span.game-rating").text();  // مثل 4.9/5
            String priceText = gameElement.selectFirst("span.game-price").text();    // مثل 92 €

            double rating = Double.parseDouble(ratingText.split("/")[0]);
            int price = Integer.parseInt(priceText.replaceAll("[^\\d]", ""));

            Game game = new Game(name, rating, price);
            games.add(game);
        }
    }


    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            parser.setUp();
            System.out.println("Sorted by Name:");
            parser.sortByName().forEach(System.out::println);

            System.out.println("\nSorted by Rating:");
            parser.sortByRating().forEach(System.out::println);

            System.out.println("\nSorted by Price:");
            parser.sortByPrice().forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error reading HTML file: " + e.getMessage());
        }
    }

}
