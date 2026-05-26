package plugin.siren.Utils;

import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import plugin.siren.SirensCommands;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SirensCmdUpdateChecker {
    private static java.util.List<String> latestVersions;

    public static java.util.List<String> getVersionStrings(){
        try{
            URL url = new URL("https://api.mermaids.dev/versions/sirens-commands/release/");

            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            java.util.List<String> list = new ArrayList<>();

            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    if(line.contains("<h1>{ version: ") && line.contains(" }</h1>")){
                        line = line.substring(line.indexOf("<h1>{ version: ") + 15);
                        line = line.substring(0, line.indexOf(" }</"));
                        list.add(line);
                    }

                    if(line.contains("<h3>{ ignore-version: ") && line.contains(" }</h3>")){
                        line = line.substring(line.indexOf("<h3>{ ignore-version: ") + 22);
                        line = line.substring(0, line.indexOf(" }</"));
                        list.add(line);
                    }
                }

                return list;
            }
        } catch (Exception e) {
            SirensCommands.LOGGER.atInfo().log("Exception with SirensCFUpdateChecker : " + e.toString());

            java.util.List<String> list = new ArrayList<>();
            list.add(SirensCommands.getVersion());

            return list;
        }
    }

    public static void sendUpdateMessage(){
        sendUpdateMessage(null, false, SirensCmdUpdateChecker.Type.Default);
    }

    public static String sendUpdateMessage(SirensCmdUpdateChecker.Type type){
        if(SirensCmdUpdateChecker.Type.StartUp.getValue() == type.getValue()) {
            java.util.List<String> recentVersions = getVersionStrings();
            latestVersions = recentVersions;

            boolean outDated = true;
            String latestVersion = recentVersions.getFirst();
            for(int i = 0; i < recentVersions.size(); i++){
                if(recentVersions.get(i).equalsIgnoreCase(SirensCommands.getVersion())){
                    outDated = false;
                }
            }

            if(outDated) {
                SirensCommands.LOGGER.atInfo().log("= =- -=- -=- -=- -=- -=- -=- -=- -=- -=- -= =");
                SirensCommands.LOGGER.atInfo().log("The Siren's Commands Mod version is outdated, Siren's Commands has released v" + latestVersion + ".");
            }
        }else if(SirensCmdUpdateChecker.Type.InfoCmd.getValue() == type.getValue()){
            java.util.List<String> recentVersions = getVersionStrings();
            latestVersions = recentVersions;

            if(recentVersions.isEmpty()){
                return SirensCommands.getVersion();
            }else{
                return recentVersions.getFirst();
            }
        }else{
            sendUpdateMessage(false);
        }

        return null;
    }

    public static void sendUpdateMessage(boolean requestAPI){
        sendUpdateMessage(null, false, SirensCmdUpdateChecker.Type.Default, requestAPI);
    }

    public static void sendUpdateMessage(PlayerRef playerRef){
        sendUpdateMessage(playerRef, true, SirensCmdUpdateChecker.Type.Default);
    }

    public static void sendUpdateMessage(PlayerRef playerRef, SirensCmdUpdateChecker.Type type){
        sendUpdateMessage(playerRef, true, type);
    }

    public static void sendUpdateMessage(@Nullable PlayerRef playerRef, boolean sendToPlayer, SirensCmdUpdateChecker.Type type){
        sendUpdateMessage(playerRef, sendToPlayer, type, false);
    }

    public static void sendUpdateMessage(@Nullable PlayerRef playerRef, boolean sendToPlayer, SirensCmdUpdateChecker.Type type, boolean requestAPI){
        List<String> recentVersions = new ArrayList<>();
        if(requestAPI){
            recentVersions = getVersionStrings();
            latestVersions = recentVersions;
        }else{
            recentVersions = latestVersions;
        }

        boolean outDated = true;
        String latestVersion = recentVersions.getFirst();
        for(int i = 0; i < recentVersions.size(); i++){
            if(recentVersions.get(i).equalsIgnoreCase(SirensCommands.getVersion())){
                outDated = false;
            }
        }

        if(outDated){
            String translationId = "server.updateChecker.sirenscommands.release.message";
            Message versionMessage = Message.translation(translationId).param("version", latestVersion);

            if(SirensCmdUpdateChecker.Type.PlayerReadyEvent.getValue() != type.getValue()) {
                SirensCommands.LOGGER.atInfo().log(versionMessage.getAnsiMessage());
            }
        }
    }

    public enum Type {
        StartUp(0),
        InfoCmd(1),
        PlayerReadyEvent(2),
        Default(3),
        MermaidsUI(4);

        private final int value;
        private Type(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }
    }
}
