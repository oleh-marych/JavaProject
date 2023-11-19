package com.oleg.droids.collision;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.oleg.droids.components.MovableComponent;
import javafx.geometry.Point2D;

import static com.oleg.droids.EntityType.PLAYER;

public class PlayerPlayerHandler extends CollisionHandler {
    public PlayerPlayerHandler() {
        super(PLAYER, PLAYER);
    }

    private Point2D dir1, dir2;

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        Vec2 vecFromAtoB = new Vec2(b.getCenter().subtract(a.getCenter()));
        if (vecFromAtoB.x == 0 && vecFromAtoB.y == 0) {
            vecFromAtoB.x = vecFromAtoB.y = 1;
        }
        dir1 = vecFromAtoB.negate().toPoint2D();
        dir2 = vecFromAtoB.toPoint2D();

        a.getComponent(MovableComponent.class).collisionStart(dir1);
        b.getComponent(MovableComponent.class).collisionStart(dir2);
    }

    @Override
    protected void onCollisionEnd(Entity a, Entity b) {
        a.getComponent(MovableComponent.class).collisionEnd();
        b.getComponent(MovableComponent.class).collisionEnd();
    }
}
