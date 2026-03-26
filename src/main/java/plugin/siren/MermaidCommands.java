package plugin.siren;

import com.hypixel.hytale.event.EventRegistration;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import plugin.siren.Commands.*;
import plugin.siren.Events.PlayerReadyEventSC;
import plugin.siren.Utils.HStats;
import plugin.siren.Utils.UpdateCheckerSC;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class MermaidCommands extends JavaPlugin {
    private final static String VERSION = "1.0.3";
    private static MermaidCommands instance;
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public MermaidCommands(@Nonnull JavaPluginInit init){
        super(init);
        instance = this;

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

        this.getCommandRegistry().registerCommand(new MermaidCommandsList("sirenscommands", "View all of Siren's commands"));
        this.getCommandRegistry().registerCommand(new MermaidCommandsList("sirenscmds", "View all of Siren's commands"));
        this.getCommandRegistry().registerCommand(new MermaidCommandsList("sirencommands", "View all of Siren's commands"));
        this.getCommandRegistry().registerCommand(new MermaidCommandsList("sirencmds", "View all of Siren's commands"));
        this.getCommandRegistry().registerCommand(new MermaidCommandsList("scmds", "View all of Siren's commands"));
        this.getCommandRegistry().registerCommand(new MermaidCommandsList("scmd", "View all of Siren's commands"));

        this.getCommandRegistry().registerCommand(new ListCmd("list", "See a list of all players connected to the server"));
        this.getCommandRegistry().registerCommand(new ListCmd("players", "See a list of all players connected to the server"));

        //this.getCommandRegistry().registerCommand(new TPAll(instance));
        LOGGER.atInfo().log("Successfully registered all commands.");

        LOGGER.atInfo().log("Version " + VERSION + " of Siren's Commands has successfully loaded.");

        UpdateCheckerSC.sendUpdateMessage(UpdateCheckerSC.Type.StartUp);

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

    public static MermaidCommands getInstance(){
        return instance;
    }

    public static String getVersion(){
        return VERSION;
    }
}
