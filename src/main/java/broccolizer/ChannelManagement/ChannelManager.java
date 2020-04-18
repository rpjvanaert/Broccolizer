package broccolizer.ChannelManagement;

import broccolizer.ChannelManagement.Listeners.*;
import broccolizer.GameLogic.Roles;
import broccolizer.GameLogic.DiscordController;
import broccolizer.Information;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.user.User;

import java.util.concurrent.ExecutionException;

public class ChannelManager {

    public static void deleteChannel(TextChannel channel){
        channel.asServerChannel().get().delete();
    }

    public static void setupChannels(){
        DiscordController.getInstance().setWolvesChannel(setupChannel(Roles.WOLF));
        DiscordController.getInstance().getWolvesChannel().addTextChannelAttachableListener(new WolfChannelListener());

        DiscordController.getInstance().setWitchChannel(setupChannel(Roles.WITCH));
        DiscordController.getInstance().getWitchChannel().addTextChannelAttachableListener(new WitchChannelListener());

        DiscordController.getInstance().setOracleChannel(setupChannel(Roles.ORACLE));
        DiscordController.getInstance().getOracleChannel().addTextChannelAttachableListener(new OracleChannelListener());

        DiscordController.getInstance().setCupidChannel(setupChannel(Roles.CUPID));
        DiscordController.getInstance().getCupidChannel().addTextChannelAttachableListener(new CupidChannelListener());
    }

    private static TextChannel setupChannel(Roles rolePermitted){
        ServerTextChannelBuilder serverTextChannelBuilder = new ServerTextChannelBuilder(DiscordController.getInstance().getServer())
                .addPermissionOverwrite(DiscordController.getInstance().getServer()
                        .getRoleById(Information.getEveryoneRoleID()).get(), getPermissionDenied());

        serverTextChannelBuilder = setRoleDependingPermission(serverTextChannelBuilder, rolePermitted);
        TextChannel returnChannel = null;
        try {
            returnChannel = serverTextChannelBuilder.setName(rolePermitted.getFancyName()).create().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return returnChannel;
    }

    /**
     * setPermissionRoleOnly
     * adds Permission Overwrite to ServerTextChannelBuilder and returns it.
     * Only allows users to chat (read and message) if the User has the given role assigned to it.
     * @param serverTextChannelBuilder
     * @param roles
     * @return serverTextChannelBuilder
     */
    private static ServerTextChannelBuilder setRoleDependingPermission(ServerTextChannelBuilder serverTextChannelBuilder, Roles roles){
        for (User eachUser : DiscordController.getInstance().getUsers().keySet()){
            if (DiscordController.getInstance().getUsers().get(eachUser) == roles){
                serverTextChannelBuilder.addPermissionOverwrite(eachUser, getPermissionAllowedChat());
            } else if (DiscordController.getInstance().getUsers().get(eachUser) == Roles.LITTLE_GIRL){
                serverTextChannelBuilder.addPermissionOverwrite(eachUser, getPermissionAllowedRead());
            }
        }
        return serverTextChannelBuilder;
    }

    /**
     * getPermissionDenied
     * builds Permission with all denied set.
     * @return Permissions
     */
    private static Permissions getPermissionDenied(){
        return new PermissionsBuilder()
                .setAllDenied()
                .build();
    }

    /**
     * getPermissionAllowedRead
     * builds Permission with all denied but read allowed.
     * @return Permissions
     */
    private static Permissions getPermissionAllowedRead(){
        return new PermissionsBuilder()
                .setAllDenied()
                .setAllowed(PermissionType.READ_MESSAGES)
                .setAllowed(PermissionType.READ_MESSAGE_HISTORY)
                .build();
    }

    /**
     * getPermissionAllowedChat
     * builds Permission with all denied except read and send messages.
     * @return Permissions
     */
    private static Permissions getPermissionAllowedChat(){
        return new PermissionsBuilder()
                .setAllDenied()
                .setAllowed(PermissionType.READ_MESSAGES)
                .setAllowed(PermissionType.SEND_MESSAGES)
                .setAllowed(PermissionType.READ_MESSAGE_HISTORY)
                .build();
    }
}
