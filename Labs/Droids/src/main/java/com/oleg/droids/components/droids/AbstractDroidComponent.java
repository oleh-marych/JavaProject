package com.oleg.droids.components.droids;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IDComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.time.LocalTimer;
import com.oleg.droids.DroidsApp;
import com.oleg.droids.components.MovableComponent;
import com.oleg.droids.components.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.geti;


public abstract class AbstractDroidComponent extends Component {
    public HealthDoubleComponent hp;
    public BoundingBoxComponent bbox;
    public ViewComponent view;
    public IDComponent idComponent;
    public MovableComponent movableComponent;
    protected int speed, bulletRange, bulletSpeed;
    protected boolean killed = false;

    protected String team;
    /**
     * Maximal HP of droid
     */
    protected double maxHP, bulletDamage;
    /**
     * URL to the image of droid
     */
    protected String textureURL;
    /**
     * Timer to check if entity can shoot
     */
    protected LocalTimer weaponTimer = newLocalTimer();
    /**
     * Delay between shoots
     */
    protected Duration shootDelay;

    /**
     * Shoot method
     * @param shootPoint - target point of bullet
     */
    public abstract void shoot(Point2D shootPoint);

    /**
     * correct bullets spawn position relatively to entity image
     * @param factor - multiplayer for correcting
     * @param direction - direction of bullet
     * @return correct point of bullet's spawn
     */
    protected Point2D normalizeBulletPosition(double factor, Point2D direction) {
        return entity.getCenter()
                .subtract(new Point2D(17, 0))
                .add(direction.normalize().multiply(factor));
    }

    protected void spawnBullet(SpawnData data) {
        data.put("shooter", entity);
        FXGL.getGameWorld().spawn("bullet", data);
    }

    @Override
    public void onAdded() {
        entity.setProperty("DroidType", this);
    }

    protected SpawnData setBulletData(SpawnData data, Point2D direction, int speedBullet,
                                      double rangeBullet, double damageBullet, double bulletRadius) {
        data.put("speedBullet", speedBullet)
                .put("directionBullet", direction)
                .put("rangeBullet", rangeBullet)
                .put("damageBullet", damageBullet)
                .put("bulletRadius",  bulletRadius);
        return data;
    }

    /**
     * kills this entity
     */
    public void kill(Entity killer) {
        if (killed) return;
        killed = true;

        entity.removeComponent(CollidableComponent.class);

        FXGL.animationBuilder()
                .onFinished(() -> {
                    entity.getViewComponent().clearChildren();
                    if (Objects.equals(team, "Blue")) {
                        FXGL.inc("kst blue", -1);
                    }
                    else {
                        FXGL.inc("kst red", -1);
                    }
                })
                .duration(Duration.millis(300))
                .fadeOut(entity)
                .buildAndPlay();

        entity.getComponentOptional(PlayerComponent.class).ifPresent((e) -> ((DroidsApp) FXGL.getApp()).deathPlayer(killer));

        FXGL.despawnWithScale(entity, Duration.millis(300));
    }

    public int getSpeed() {
        return speed;
    }

    public double getMaxHP() {
        return maxHP;
    }

    public String getTexture() {
        return textureURL;
    }

    public Duration getShootDelay() {
        return shootDelay;
    }

    public double getBulletDamage() {
        return bulletDamage;
    }

    public int getBulletRange() {
        return bulletRange;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
        textureURL = textureURL.split("\\.")[0] + "_" + team + "." + textureURL.split("\\.")[1];
    }
}
