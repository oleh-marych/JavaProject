package com.oleg.droids.components;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class MovableComponent extends Component {
    public static final Point2D UP = new Point2D(0, -1), RIGHT = new Point2D(1, 0),
            DOWN = new Point2D(0, 1), LEFT = new Point2D(-1, 0);

    private final Vec2 moveDirection = new Vec2(0, 0);

    public ProjectileComponent projectileComponent;
    private boolean isCollision = false;
    private double speed, currentSpeed;

    /**
     * coefficient, that impact how fast entity lose inertia
     * <ul>
     *     <li> = 1 - default</li>
     *     <li> < 1 - faster losing inertia</li>
     *     <li> > 1 - slower losing inertia</li>
     * </ul>
     *
     */
    private double slowdownCoefficient;

    public MovableComponent() {
        this.slowdownCoefficient = 1;
    }
    public MovableComponent(double slowdownCoefficient) {
        this.slowdownCoefficient = slowdownCoefficient;
    }

    @Override
    public void onAdded() {
        projectileComponent.allowRotation(false);
        speed = currentSpeed = projectileComponent.getSpeed();
    }

    @Override
    public void onUpdate(double tpf) {
        currentSpeed -= 1 * slowdownCoefficient;
        projectileComponent.setSpeed(currentSpeed);
    }

    public void collisionStart(Point2D direction) {
        isCollision = true;
        currentSpeed = speed;
        projectileComponent.setSpeed(currentSpeed);
        moveDirection.set(direction);
        projectileComponent.setDirection(moveDirection.toPoint2D());
    }

    public void collisionEnd() {
        isCollision = false;
        currentSpeed = speed;
        projectileComponent.setSpeed(currentSpeed);
    }

    public void move(Point2D direction) {
        if (isCollision) return;
        if (direction.getX() == 0)
            moveDirection.y = (float) direction.getY();
        else if (direction.getY() == 0)
            moveDirection.x = (float) direction.getX();
        else
            moveDirection.set(direction);

        currentSpeed = speed;

        projectileComponent.setDirection(moveDirection.toPoint2D());
    }

    public void stop(Point2D direction) {
        if (isCollision) return;
        if (direction.getX() == 0)
            moveDirection.y = 0;
        else
            moveDirection.x = 0;
        projectileComponent.setDirection(moveDirection.toPoint2D());
    }

    public void stop() {
        moveDirection.setZero();
        projectileComponent.setDirection(moveDirection.toPoint2D());
    }

    public void setSlowdownCoefficient(double slowdownCoefficient) {
        this.slowdownCoefficient = slowdownCoefficient;
    }
}
