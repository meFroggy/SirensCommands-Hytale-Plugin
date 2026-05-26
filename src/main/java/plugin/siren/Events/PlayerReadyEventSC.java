package plugin.siren.Events;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.SirensCommands;
import plugin.siren.System.SirensCommandsComponent;
import plugin.siren.Utils.SirensCmdUpdateChecker;

public class PlayerReadyEventSC {
    public static void onPlayerReadyEvent(PlayerReadyEvent event){
        World world = event.getPlayer().getWorld();
        if (world == null){
            SirensCommands.LOGGER.atSevere().log("Most recent player failed to get get world inside PlayerReadyEvent : PlayerReadyEventCF");
        } else {
            world.execute(() -> {
                Ref<EntityStore> ref = event.getPlayerRef();
                Store<EntityStore> store = ref.getStore();

                SirensCommandsComponent sirensComponent = new SirensCommandsComponent();
                store.putComponent(ref, SirensCommandsComponent.getComponentType(), sirensComponent);

                SirensCommandsComponent sirensCFComponent = store.ensureAndGetComponent(ref, SirensCommandsComponent.getComponentType());
                if (sirensCFComponent == null) {
                    SirensCmdUpdateChecker.sendUpdateMessage(null, false, SirensCmdUpdateChecker.Type.Default, false);
                } else {
                    if (!sirensCFComponent.getUpdateCheckerCheck()) {
                        SirensCmdUpdateChecker.sendUpdateMessage(null, false, SirensCmdUpdateChecker.Type.Default, false);

                        sirensCFComponent.setCheckOnUpdateChecker(true);
                    }
                }
            });
        }
    }
}
