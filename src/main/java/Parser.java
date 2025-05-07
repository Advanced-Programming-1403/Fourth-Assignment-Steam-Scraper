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
        // Sort games alphabetically (least)
        List<Game> sortedByName = new ArrayList<>(games);
        for (int i = 0; i < sortedByName.size() - 1; i++)
        {
            for (int j = 0; j < sortedByName.size() - i - 1; j++)
            {
                if (sortedByName.get(j).getName().compareTo(sortedByName.get(j + 1).getName()) > 0) {
                    // Swap the elements
                    Game temp = sortedByName.get(j);
                    sortedByName.set(j, sortedByName.get(j + 1));
                    sortedByName.set(j + 1, temp);
                }
            }
        }
        return sortedByName;
    }

    public List<Game> sortByRating(){
        // Sort games by rating (most)
        List<Game> sortedByRating = new ArrayList<>(games);
        for (int i = 0; i < sortedByRating.size() - 1; i++)
        {
            for (int j = 0; j < sortedByRating.size() - i - 1; j++)
            {
                if (sortedByRating.get(j).getRating() < sortedByRating.get(j + 1).getRating())
                {
                    // Swap the elements
                    Game temp = sortedByRating.get(j);
                    sortedByRating.set(j, sortedByRating.get(j + 1));
                    sortedByRating.set(j + 1, temp);
                }
            }
        }
        return sortedByRating;
    }

    public List<Game> sortByPrice(){
        List<Game> sortedByPrice = new ArrayList<>(games);
        // Sort games by price (most)
        for (int i = 0; i < sortedByPrice.size() - 1; i++)
        {
            for (int j = 0; j < sortedByPrice.size() - i - 1; j++)
            {
                if (sortedByPrice.get(j).getPrice() < sortedByPrice.get(j + 1).getPrice())
                {
                    // Swap the elements
                    Game temp = sortedByPrice.get(j);
                    sortedByPrice.set(j, sortedByPrice.get(j + 1));
                    sortedByPrice.set(j + 1, temp);
                }
            }
        }
        return sortedByPrice;
    }

    public void setUp() throws IOException {

        //Parse the HTML file using Jsoup
        File input = new File("src/Resources/Video_Games.html");
        Document doc = Jsoup.parse(input, "UTF-8"); //UTF-8 for read all char

        // Extract data from the HTML
        Elements gameElements = doc.select("div.game");

        // Iterate through each Game div to extract Game data
        for (Element gameElement : gameElements) {
            String name = gameElement.select(".game-name").text();

            String ratingText = gameElement.select(".game-rating").text();
            ratingText = ratingText.replace("/5", "").trim();
            double rating = Double.parseDouble(ratingText);

            String priceText = gameElement.select(".game-price").text();
            priceText = priceText.replace("€", "").trim();
            int price = Integer.parseInt(priceText);
            Game game = new Game(name, rating, price);
            games.add(game);
        }
    }

    public static void main(String[] args) {
        //you can test your code here before you run the unit tests
    }
}
