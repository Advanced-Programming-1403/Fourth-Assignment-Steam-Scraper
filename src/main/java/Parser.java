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
        sortedByRating.sort(Comparator.comparing(Game::getRating).reversed());
        return sortedByRating;
    }

    public List<Game> sortByPrice(){
        List<Game> sortedByPrice = new ArrayList<>(games);
        sortedByPrice.sort(Comparator.comparing(Game::getPrice).reversed());
        return sortedByPrice;
    }

    public void setUp() throws IOException {

        File input = new File("src/Resources/Video_Games.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements gameElements = doc.select(".game");

        List<Element> limitedGameElements = gameElements.subList(0, 100);


        for (Element gameElement : limitedGameElements) {

            String name = gameElement.select(".game-name").text();

            String ratingText = gameElement.select(".game-rating").text();
            double rating = Double.parseDouble(ratingText.split("/")[0]);

            String priceText = gameElement.select(".game-price").text();
            int price = Integer.parseInt(priceText.replace(" €", "").trim());

            games.add(new Game(name, rating, price));
        }
    }

    public static void main(String[] args) {
        int i = 0;
        for(Game game : games){
            System.out.print(++i + "-");
            System.out.println(game);
        }
        try {
            Parser parser = new Parser();
            parser.setUp();

            System.out.println("Successfully loaded " + games.size() + " games");


            System.out.println("\nFirst 5 games:");
            games.stream().limit(5).forEach(System.out::println);


            System.out.println("\nTop 5 by rating:");
            parser.sortByRating().stream().limit(5).forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Initialization failed: " + e.getMessage());
            e.printStackTrace();
        }












    }
}