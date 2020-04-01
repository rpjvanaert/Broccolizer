package broccolizer;

import broccolizer.Listeners.textChannelListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class App {

    public static void main(String[] args) {


        DiscordApi api = new DiscordApiBuilder().setToken(Information.getToken()).login().join();

        api.addListener(new textChannelListener());


        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }
}