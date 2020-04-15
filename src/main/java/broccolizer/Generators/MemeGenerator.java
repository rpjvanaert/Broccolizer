package broccolizer.Generators;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MemeGenerator {

    public static ArrayList<String> verbs;
    public static ArrayList<String> word1;
    public static ArrayList<String> word2;

    public static Random rng;

    public static MemeGenerator instance;

    public static MemeGenerator getInstance(){
        if (instance == null){
            instance = new MemeGenerator();
        }
        return instance;
    }

    public MemeGenerator(){
        rng = new Random();

        verbs = new ArrayList<>(Arrays.asList(
                "shits on", "runs past", "walks past", "dabs on", "sits on",
                "crawls past", "slams", "headbutts", "smashes",
                "crushes", "defeats", "ignores", "makes",
                "cosplays", "fakes", "eats", "feels",
                "complicates", "knows", "got", "bends",
                "breaks", "loses", "hates", "cries because of",
                "forgets", "does", "loves", "speaks",
                "moves", "needs", "comes to", "leaves behind",
                "plays with", "fetishes", "hisses at", "moves past",
                "drinks", "burns", "is", "was", "is", "suffers because of", "uses"
        ));

        word1 = new ArrayList<>(Arrays.asList(
                "Mario", "Wario", "Waluigi", "Luigi",
                "Elsa", "Anna", "Olaf", "Camille", "Your crush",
                "Trump", "Bill Gates", "Kim Yong-Un",
                "Rosalien Theresia Engelbrecht", "A bunny", "A dog",
                "A cat", "A rock", "My dumb ass", "Ralph",
                "Sonic", "Annabeth Chase", "Percy Jackson", "Optimus Prime",
                "A bunny", "A bird", "Lightning McQueen", "Justice Magicians",
                "Harry Potter"
        ));

        word2 = new ArrayList<>(Arrays.asList(
                "nachos", "Keanu Reeves", "Trump", "nothing",
                "Olaf", "Peach", "Daisy", "Bowser", "Bowsette",
                "a bee", "enchiladas", "broccoli", "computers",
                "baby toys", "pictures", "work", "a phone", "The Force",
                "a butterfly", "chemicals", "love", "feelings", "no feelings",
                "coffee", "milk", "chemicals", "ankles", "blood", "cool", "shit",
                "swear words", "bullfrogs", "ankle biters", "death", "life", "suffering",
                "the meaning", "guns", "lines", "sausages", "vegetables", "bed", "chair", "table",
                "in your mind", "Justice magicians", "Harry Potter", "girls", "guys", "your mom"
        ));
    }

    public static String getRandomTitle(){
        return word1.get(rng.nextInt(word1.size())) + " " + verbs.get(rng.nextInt(verbs.size())) + " " + word2.get(rng.nextInt(word2.size())) + "!";
    }

    public static File getRandomMeme() {
        File meme;
        File memeFolder = new File("resource/memesIncluded");
        meme = memeFolder.listFiles()[rng.nextInt(memeFolder.listFiles().length)];
        return meme;
    }
}
