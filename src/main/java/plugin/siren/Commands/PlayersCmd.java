package plugin.siren.Commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PlayersCmd extends AbstractCommand {

    public PlayersCmd() {
        super("players","server.commands.sirenscommands.players");

        this.addAliases("list");

        this.setPermissionGroups("Adventure");
    }

    @Nonnull
    @Override
    protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
        return CompletableFuture.runAsync(() -> {
            String playerListStr = "";

            Collection<PlayerRef> playersCollection = Universe.get().getPlayers();
            List<PlayerRef> players = playersCollection.stream().toList();

            for (int i = 0; i < players.size(); i++) {
                if (i != 0) {
                    playerListStr += ", ";
                }
                PlayerRef playerRef = players.get(i);

                playerListStr += playerRef.getUsername();
            }

            String playerCount = "There ";

            if (players.size() == 1) {
                playerCount += "is";
            } else {
                playerCount += "are";
            }

            playerCount += " " + Integer.toString(players.size()) + " ";

            if (players.size() == 1) {
                playerCount += "player";
            } else {
                playerCount += "players";
            }

            playerCount += " connected to the server.";

            context.sendMessage(Message.raw(playerCount).color(Color.YELLOW));
            context.sendMessage(Message.raw(playerListStr).color(Color.YELLOW));
        });
    }
}