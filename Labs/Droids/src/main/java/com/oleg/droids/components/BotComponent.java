package com.oleg.droids.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.time.LocalTimer;
import com.oleg.droids.components.droids.AbstractDroidComponent;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

import java.util.Objects;

import static com.oleg.droids.EntityType.PLAYER;

public class BotComponent extends Component {
    private Point2D moveDirection,
                    viewDirection = new Point2D(0, 0);
    private AbstractDroidComponent droidComponent;
    public MovableComponent movableComponent;
    public StateComponent state;
    private double bulletRange;

    @Override
    public void onAdded() {
        droidComponent = entity.getObject("DroidType");
        bulletRange = droidComponent.getBulletRange();
        range = new Rectangle2D(entity.getCenter().getX() - bulletRange / 2, entity.getCenter().getY() - bulletRange / 2, bulletRange, bulletRange);
        state.changeState(ATTACK_ENEMY);
    }

    private Rectangle2D range;
    public Entity target;

    @Override
    public void onUpdate(double tpf) {
        var closestEntity = findClosestEntity();
        if (closestEntity == null) {
            state.changeState(FIND_ENEMY);
        }
        else {
            target = closestEntity;
            state.changeState(ATTACK_ENEMY);
        }
    }

    private EntityState ATTACK_ENEMY = new EntityState() {
        private final LocalTimer moveTimer = FXGL.newLocalTimer();
        private final Duration moveDelay = Duration.millis(300);

        private final double PI4 = FXGLMath.HALF_PI/2;

        @Override
        protected void onUpdate(double tpf) {
            if (target == null) return;

            shootBot();
            moveBot();
        }

        private void shootBot() {
            viewDirection = target.getCenter().subtract(entity.getCenter());
            entity.rotateToVector(viewDirection);
            shoot(target.getCenter());
        }

        private void moveBot() {
            double dist = entity.distance(target);
            if (moveDirection == null) moveDirection = viewDirection;
            if (dist > bulletRange * 0.7) {
                moveDirection = viewDirection;
            }
            else if (dist <= bulletRange * 0.7 && dist > bulletRange * 0.3) {
                if (moveTimer.elapsed(moveDelay)) {
                    moveDirection = FXGLMath.rotate(moveDirection, viewDirection, FXGLMath.toDegrees(FXGL.random(-FXGLMath.PI, FXGLMath.PI)));
                    moveTimer.capture();
                }
            }
            if (dist < bulletRange * 0.3) {
                if (moveTimer.elapsed(moveDelay)) {
                    moveDirection = moveDirection.multiply(-1);
                    moveDirection = FXGLMath.rotate(moveDirection, moveDirection, FXGLMath.toDegrees(FXGL.random(-PI4, PI4)));
                    moveTimer.capture();
                }
            }
            movableComponent.move(moveDirection);
        }
    };

    private EntityState FIND_ENEMY = new EntityState() {
        private final LocalTimer moveTimer = FXGL.newLocalTimer();
        private final Duration moveDelay = Duration.millis(1000);
        @Override
        public void onEntering() {
            moveDirection = FXGLMath.rotate(FXGLMath.randomPoint(range),
                    viewDirection, FXGLMath.toDegrees(FXGL.random(-FXGLMath.PI, FXGLMath.PI)));
            viewDirection = moveDirection;
            entity.rotateToVector(viewDirection);
            movableComponent.move(moveDirection);
        }

        @Override
        protected void onUpdate(double tpf) {
            if (moveTimer.elapsed(moveDelay)) {
                moveDirection = FXGLMath.rotate(moveDirection, viewDirection, FXGLMath.toDegrees(FXGL.random(-FXGLMath.HALF_PI, FXGLMath.HALF_PI)));
                moveTimer.capture();
                movableComponent.move(moveDirection);
            }
            viewDirection = FXGLMath.rotate(viewDirection, Point2D.ZERO, FXGLMath.toDegrees( 0.06));
            entity.rotateToVector(viewDirection);
        }
    };

    private Entity findClosestEntity() {
        var newTarget = FXGL.getGameWorld().getClosestEntity(entity,
                (e1) -> e1.getType() == PLAYER &&
                        !Objects.equals(((AbstractDroidComponent) entity.getObject("DroidType")).getTeam(), ((AbstractDroidComponent) e1.getObject("DroidType")).getTeam())
                        && (int) (entity.distance(e1)) < bulletRange
        );

        return newTarget.orElse(null);
    }

    public void shoot(Point2D shootDirection) {
        droidComponent.shoot(shootDirection);
    }
}
