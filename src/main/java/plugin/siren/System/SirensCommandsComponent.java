package plugin.siren.System;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.SirensCommands;

import javax.annotation.Nullable;

public class SirensCommandsComponent implements Component<EntityStore> {

    private boolean updateChecker;

    public static ComponentType<EntityStore, SirensCommandsComponent> getComponentType(){
        return SirensCommands.get().getSirensCommandsComponentType();
    }

    public SirensCommandsComponent(){
        this.updateChecker = false;
    }

    public SirensCommandsComponent(SirensCommandsComponent other){
        this.updateChecker = other.updateChecker;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new SirensCommandsComponent(this);
    }

    public boolean getUpdateCheckerCheck(){
        return this.updateChecker;
    }

    public void setCheckOnUpdateChecker(boolean checked){
        this.updateChecker = checked;
    }
}
