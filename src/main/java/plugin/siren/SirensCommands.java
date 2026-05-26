package plugin.siren;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.event.EventRegistration;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.events.AllWorldsLoadedEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Commands.*;
import plugin.siren.Events.AllWorldsLoadedEventSC;
import plugin.siren.Events.PlayerReadyEventSC;
import plugin.siren.System.SirensCommandsComponent;
import plugin.siren.Utils.HStats;
import plugin.siren.Utils.SirensCmdUpdateChecker;

import javax.annotation.Nonnull;

public class SirensCommands extends JavaPlugin {
    private final static String VERSION = "1.0.4";

    private static SirensCommands plugin;
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    private ComponentType<EntityStore, SirensCommandsComponent> sirensCommandsComponent;

    public SirensCommands(@Nonnull JavaPluginInit init){
        super(init);
        plugin = this;

        new HStats("56a8c866-7826-4eab-a0ac-116491f4fc60", VERSION);
    }

    @Override
    protected void setup(){
        LOGGER.atInfo().log("===---==---==---== SIREN'S COMMANDS ==---==---==---===");
        LOGGER.atInfo().log("Siren's Commands has began to load.");

        EventRegistration<String, PlayerReadyEvent> playerReadyEventRegistration = this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, PlayerReadyEventSC::onPlayerReadyEvent);
        if(playerReadyEventRegistration != null && playerReadyEventRegistration.isRegistered()) {
            LOGGER.atInfo().log("Registered Player Ready Event.");
        }else{
            LOGGER.atSevere().log("Failed to register Player Ready Event.");
        }

        EventRegistration<Void, AllWorldsLoadedEvent> allWorldsLoadedEventRegistration = this.getEventRegistry().registerGlobal(AllWorldsLoadedEvent.class, AllWorldsLoadedEventSC::onAllWorldsLoaded);
        if(allWorldsLoadedEventRegistration != null && allWorldsLoadedEventRegistration.isRegistered()) {
            LOGGER.atInfo().log("Registered All Worlds Loaded Event.");
        }else{
            LOGGER.atSevere().log("Failed to register All Worlds Loaded Event.");
        }

        this.sirensCommandsComponent = this.getEntityStoreRegistry().registerComponent(SirensCommandsComponent.class, SirensCommandsComponent::new);
        if(this.sirensCommandsComponent != null) {
            LOGGER.atInfo().log("Registered Siren's Commands Component.");
        }else{
            LOGGER.atInfo().log("Failed to register Siren's Commands Component.");
        }

        this.getCommandRegistry().registerCommand(new SirensCmdListCmd());
        this.getCommandRegistry().registerCommand(new PlayersCmd());

        LOGGER.atInfo().log("Successfully registered all commands.");

        LOGGER.atInfo().log("Version " + VERSION + " of Siren's Commands has successfully loaded.");

        SirensCmdUpdateChecker.sendUpdateMessage(SirensCmdUpdateChecker.Type.StartUp);

        LOGGER.atInfo().log("===---==---==---==---==---==---==---==---==---==---===");
    }

    @Override
    protected void shutdown(){
        LOGGER.atInfo().log("===---==---==---== SIREN'S COMMANDS ==---==---==---===");
        LOGGER.atInfo().log("Siren's Commands has began to shutdown.");
        LOGGER.atInfo().log("Saving any necessary data.");
        LOGGER.atInfo().log("Version " + VERSION + " of Siren's Commands has successfully shutdown.");
        LOGGER.atInfo().log("===---==---==---==---==---==---==---==---==---==---===");
    }

    public ComponentType<EntityStore, SirensCommandsComponent> getSirensCommandsComponentType(){
        return sirensCommandsComponent;
    }

    public static SirensCommands get(){
        return plugin;
    }

    public static String getVersion(){
        return VERSION;
    }
}
