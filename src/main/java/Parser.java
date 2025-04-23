import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {
    static List<Game> games = new ArrayList<>();
    String path = "C:\\Users\\mobin\\Desktop\\javaa\\work_shop\\Scrapper\\src\\main\\resources\\Games.html";

    public List<Game> sortByName() {
        List<Game> sortedByName = new ArrayList<>(games);
        sortedByName.sort(Comparator.comparing(Game::getName));
        return sortedByName;
    }

    public List<Game> sortByRating() {
        List<Game> sortedByRating = new ArrayList<>(games);
        sortedByRating.sort(Comparator.comparingDouble(Game::getRating).reversed());
        return sortedByRating;
    }

    public List<Game> sortByPrice() {
        List<Game> sortedByPrice = new ArrayList<>(games);
        sortedByPrice.sort(Comparator.comparingInt(Game::getPrice).reversed());
        return sortedByPrice;
    }


    public void setUp() throws IOException {

        Document doc = Jsoup.parse(new File(path), "UTF-8");
        Elements gameElements = doc.select(".game");

        for (Element game : gameElements) {
            String name = game.select(".game-name").text();
            double rating = Double.parseDouble(game.select(".game-rating").text().split("/")[0]);
            int price = Integer.parseInt(game.select(".game-price").text().replace(" €", ""));
            games.add(new Game(name, rating, price));
        }

        // for having exactly 100 games
        if (games.size() > 100) {
            games = games.subList(0, 100);
        }
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            parser.setUp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
