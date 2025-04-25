package main.java;

import main.java.Game;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameParser {
    static List<Game> games = new ArrayList<>();

    public List<Game> sortByName(){
        List<Game> sortedByName = new ArrayList<>(games);
        // if it's ascending sorting, we use the natural criterion;
        //Otherwise, we perform it in reverse (descending) order.
        if (ascending) {
            sortedByName.sort(Comparator.comparing(Game::getName));
            System.out.println("Game names were sorted alphabetically ascending ✅");
        } else {
            sortedByName.sort(Comparator.comparing(Game::getName).reversed());
            System.out.println("Game names were sorted in descending order ✅");
        }

        return sortedByName;
    }

    public List<Game> sortByRating(){
        List<Game> sortedByRating = new ArrayList<>(games);
        Comparator<Game> ratingComparator = Comparator.comparing(Game::getRating);
        // use sorting in descending order
        if (descending) {
            ratingComparator = ratingComparator.reversed();
            System.out.println("Games were sorted in descending order ✅");
        } else {
            System.out.println("Game scores were sorted in ascending order ✅");
        }

        //If the scores are equal, sort the games alphabetically by name
        ratingComparator = ratingComparator.thenComparing(Game::getName);
        sortedByRating.sort(ratingComparator);
        return sortedByRating;
    }

    public List<Game> sortByPrice(){
        List<Game> sortedByPrice = new ArrayList<>(games);
        Comparator<Game> priceComparator = Comparator.comparing(Game::getPrice);

        if (descending) {
            priceComparator = priceComparator.reversed();
            System.out.println("💰 بازی‌ها بر اساس قیمت (بیشترین به کمترین) مرتب شدند.");
        } else {
            System.out.println("💸 بازی‌ها بر اساس قیمت (کمترین به بیشترین) مرتب شدند.");
        }

        // اگر قیمت برابر بود، ابتدا بر اساس امتیاز، سپس بر اساس نام مرتب شود
        priceComparator = priceComparator
                .thenComparing(Comparator.comparing(Game::getRating).reversed())
                .thenComparing(Game::getName);

        sortedByPrice.sort(priceComparator);
        return sortedByPrice;
    }

    public void setUp() throws IOException {

        File input = new File("src/Resources/Video_Games.html");// Reading a HTML file using jSoup and converting it to a doc object
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements gameElements = doc.select("div.game");

        for (Element gameElement : gameElements) {
            try {
                String name = gameElement.select("span.game-name").text().trim();
                String ratingStr = gameElement.select("span.game-rating").text().trim();
                String priceStr = gameElement.select("span.game-price").text().trim();

                if (name.isEmpty() || ratingStr.isEmpty() || priceStr.isEmpty()) {
                    System.out.println("⚠️One of the fields is empty");
                    continue;
                }

                double rating = Double.parseDouble(ratingStr);
                int price = Integer.parseInt(priceStr.replaceAll("[^\\d]", ""));

                games.add(new Game(name, rating, price));

            } catch (NumberFormatException e) {
                System.out.println("⚠️Numberical conversion error : " + e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠️Unexpected error " + e.getMessage());
            }

        System.out.println("✅ Number of games entered :" + games.size());
    }
    }

    public static void main(String[] args) {

    }
