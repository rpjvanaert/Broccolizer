package broccolizer;

import org.javacord.api.entity.channel.TextChannel;

public class TUI {

    public static void sendLobbyInstruction(TextChannel sentChannel){
        sentChannel.sendMessage(
                "Hi this is a game of the-werewolves-of-millers-hollow\n" +
                        "\"Weerwolven van Wakkerdam\"\n" +
                        "---------------------------\n" +
                        "Command\t\tFunction\n" +
                        "\n" +
                        "!info\t\t\tTo get this info panel\n" +
                        "!join\t\t\tYou join the game lobby\n" +
                        "!leave\t\t\tYou leave the game lobby\n" +
                        "!start\t\t\tTo start the game\n" +
                        "!players\t\t\tPlayers in lobby\n"
        );
    }
}
