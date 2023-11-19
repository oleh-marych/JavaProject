package com.oleg.droids;

import com.almasb.fxgl.dsl.components.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.IDComponent;
import com.almasb.fxgl.entity.state.StateComponent;
import com.oleg.droids.components.*;
import com.oleg.droids.components.droids.AbstractDroidComponent;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.oleg.droids.EntityType.*;
import static com.oleg.droids.configs.GameConfig.OUTSIDE_DISTANCE;

public class DroidsAppFactory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        AbstractDroidComponent droidTypeComponent = data.<AbstractDroidComponent>get("droidType");
        var hp = new HealthDoubleComponent(droidTypeComponent.getMaxHP());

        var e = entityBuilder(data)
                .type(PLAYER)
                .collidable()
                .zIndex(1000)
                .with(new IDComponent(data.get("droidName"), data.get("droidName").hashCode()))
                .with(hp)
                .with(new ProjectileComponent(new Point2D(0, 0), droidTypeComponent.getSpeed()))
                .with(new MovableComponent())
                .viewWithBBox(droidTypeComponent.getTexture())
                .rotationOrigin(new Point2D(25, 25))
                .with(new NickNameComponent(data.<String>get("droidName")))
                .with(new HPViewComponent())
                .with(droidTypeComponent)
                .with(new KeepInBoundsComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight())));

        if (data.get("isPlayer")) {
            e.with(new PlayerComponent());
        }
        else {
            e.with(new StateComponent());
            e.with(new BotComponent());
        }

        return e.build();
    }
    @Spawns("bullet")
    public Entity newBullet(SpawnData data) {
        int speed = data.<Integer>get("speedBullet");
        var e = entityBuilder(data)
                .type(EntityType.BULLET)
                .viewWithBBox(new Circle(data.<Double>get("bulletRadius"),  Color.BLANCHEDALMOND))
                .collidable()
                .with(new ProjectileComponent(data.get("directionBullet"), (double) speed))
                .with(new ExpireCleanComponent(Duration.seconds(data.get("rangeBullet"))).animateOpacity())
                .with(new OffscreenCleanComponent())
                .build();

        e.setProperty("shooter", data.get("shooter"));
        e.setProperty("damage", data.get("damageBullet"));
        return e;
    }

    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .at(-OUTSIDE_DISTANCE, -OUTSIDE_DISTANCE)
                .with(new BackgroundComponent())
                .zIndex(-500)
                .build();
    }
}
