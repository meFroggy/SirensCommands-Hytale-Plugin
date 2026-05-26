package plugin.siren.Commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import plugin.siren.SirensCommands;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class SirensCmdListCmd extends AbstractCommand {

    public SirensCmdListCmd() {
        super("sirenscommands","server.commands.sirenscommands.list");

        this.addAliases("sirenscmds");
        this.addAliases("sirencommands");
        this.addAliases("sirencmds");
        this.addAliases("scmds");
        this.addAliases("scmd");

        this.setPermissionGroups("Adventure");
    }

    @Nonnull
    @Override
    protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
        return CompletableFuture.runAsync(() -> {
            context.sendMessage(Message.raw("Siren Commands").color(Color.CYAN).bold(true));
            context.sendMessage(Message.raw("Version: " + SirensCommands.getVersion()).color(Color.cyan).italic(true));
            context.sendMessage(Message.raw("==-==-==-==-==-==-==-==").color(Color.CYAN));
            context.sendMessage(Message.raw("/sirencommands, /scmds : See this menu").color(Color.CYAN));
            context.sendMessage(Message.raw("/list, /players : See who is connected to the server").color(Color.CYAN));
            context.sendMessage(Message.raw("/gmc, /gma : Sets the player's gamemode").color(Color.CYAN));
            context.sendMessage(Message.raw("/top : Sends player to the surface").color(Color.CYAN));
            context.sendMessage(Message.raw("/sucide, /die : Kills the player").color(Color.CYAN));
            context.sendMessage(Message.raw("/back : Teleports the player to last location").color(Color.CYAN));
        });
    }
}