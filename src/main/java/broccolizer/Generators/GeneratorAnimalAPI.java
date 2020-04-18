package broccolizer.Generators;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GeneratorAnimalAPI {

    public static String getDogTitle(){
        ArrayList<String> titles = new ArrayList<>(Arrays.asList(
                "A good boy.", "Doggo", "Best fren", "Pupper", "Sub-Woofer"
        ));

        Random rng = new Random();

        return titles.get(rng.nextInt(titles.size()));
    }

    public static URL getDogURL(){
        return getURL("https://dog.ceo/api/breeds/image/random");
    }

    public static URL getCatURL(){
        return getURL("https://api.thecatapi.com/v1/images/search");
    }

    private static URL getURL(String stringURL){
        URL url = null;
        String line;
        try {
            Scanner scanner = new Scanner(new URL(stringURL).openStream());
            line = scanner.nextLine();
            int begin = line.indexOf("https:");
            int end = line.indexOf(".jpg") + 4;
            url = new URL(line.substring(begin, end).replace("\\", ""));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            url = getURL(stringURL);
        } catch (IllegalArgumentException e){
            url = getURL(stringURL);
        }

        return url;
    }

    public static String getCatTitle() {
        ArrayList<String> titles = new ArrayList<>(Arrays.asList(
                "Meowster", "Pawesome", "Fuzz", "Meowses", "Lucifurr"
        ));

        Random rng = new Random();

        return titles.get(rng.nextInt(titles.size()));
    }
}
