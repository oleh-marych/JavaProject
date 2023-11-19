package com.oleg.droids.collision;

import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.oleg.droids.components.droids.AbstractDroidComponent;

import java.util.Objects;

import static com.oleg.droids.EntityType.*;

public class BulletPlayerHandler extends CollisionHandler {
    public BulletPlayerHandler() {
        super(BULLET, PLAYER);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity target) {
        var targetHP = target.getComponent(HealthDoubleComponent.class);
        Entity shooter = bullet.getObject("shooter");
        if (!(target == shooter) && !isTeamates(target, shooter)) {
            targetHP.damage(bullet.getDouble("damage"));
            if (targetHP.isZero()) {
                target.<AbstractDroidComponent>getObject("droidType").kill(shooter);
            }
            bullet.removeFromWorld();
        }
    }

    private boolean isTeamates(Entity e1, Entity e2){
        if (!e1.isActive() || !e2.isActive()) return false;
        return Objects.equals(e2.<AbstractDroidComponent>getObject("DroidType").getTeam(),e1.<AbstractDroidComponent>getObject("DroidType").getTeam());
    }
}
