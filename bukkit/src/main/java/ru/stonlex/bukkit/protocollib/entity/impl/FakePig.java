package ru.stonlex.bukkit.protocollib.entity.impl;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntityLiving;
import ru.stonlex.bukkit.protocollib.entity.FakeEntityLiving;

public class FakePig extends FakeBaseEntityLiving
        implements FakeEntityLiving {

    private boolean hasSaddle;

    public FakePig(@NonNull Location location) {
        super(EntityType.PIG, location);
    }


    public void setSaddle(boolean hasSaddle) {
        if (this.hasSaddle == hasSaddle) {
            return;
        }

        this.hasSaddle = hasSaddle;
        broadcastDataWatcherObject(13, BOOLEAN_SERIALIZER, hasSaddle);
    }

    public boolean hasSaddle() {
        return hasSaddle;
    }
}
