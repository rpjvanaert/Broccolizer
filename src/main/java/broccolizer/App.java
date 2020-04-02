package broccolizer;

import broccolizer.GameLogic.GameManager;
import broccolizer.Listeners.textChannelListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;

import javax.sound.sampled.Line;

public class App {

    public static BotStates state;

    public static void main(String[] args) {

        state = BotStates.LOBBY;

        DiscordApi api = new DiscordApiBuilder().setToken(Information.getToken()).login().join();

        Server server = api.getServerById(Information.getServerID()).get();

        GameManager.getInstance().setServer(server);

//        TextChannel channel = new ServerTextChannelBuilder(server).setName("wolves").create().get();

        api.addListener(new textChannelListener());


        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }
}