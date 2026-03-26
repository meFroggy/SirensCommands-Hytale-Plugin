package plugin.siren.Events;

import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.world.World;
import plugin.siren.MermaidCommands;
import plugin.siren.Utils.UpdateCheckerSC;

public class PlayerReadyEventSC {
    public static void onPlayerReadyEvent(PlayerReadyEvent event){
        World world = event.getPlayer().getWorld();
        world.execute(() -> {
            Player player = event.getPlayer();

            UpdateCheckerSC.sendUpdateMessage(player);
        });
    }
}
