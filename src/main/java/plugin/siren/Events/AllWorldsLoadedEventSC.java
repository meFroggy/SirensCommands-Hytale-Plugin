package plugin.siren.Events;

import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.world.events.AllWorldsLoadedEvent;
import plugin.siren.Utils.SirensCmdUpdateChecker;

import java.util.concurrent.TimeUnit;

public class AllWorldsLoadedEventSC {
    public static void onAllWorldsLoaded(AllWorldsLoadedEvent event){
        //SirensCmdUpdateChecker
        Runnable updateCheckRunnable = new Runnable() {
            @Override
            public void run() {
                SirensCmdUpdateChecker.sendUpdateMessage(true);
            }
        };

        HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(updateCheckRunnable, 3, 60*60, TimeUnit.SECONDS);
    }
}
