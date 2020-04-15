package broccolizer.Generators;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class DogGenerator {

    public static File getDogIMG(){
        File file = null;


        try {
            InputStream is = new URL("https://dog.ceo/api/breeds/image/random").openStream();

            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            file = new File(jsonObject.getJsonString("message").getString());

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        try {
//            JsonReader jsonReader = Json.createReader(new FileReader(new File("https://dog.ceo/api/breeds/image/random")));
//            JsonObject jsonObject = jsonReader.readObject();
//            String string = String.valueOf(jsonObject.getJsonString("message"));
//            System.out.println(string);
//            file = new File(string);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        return file;
    }

    public static URL getDogURL(){
        URL url = null;

        try {
            url = new URL("https://dog.ceo/api/breeds/image/random");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}
