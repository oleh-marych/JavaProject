package com.oleg.droids;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.oleg.droids.collision.BulletPlayerHandler;
import com.oleg.droids.collision.PlayerPlayerHandler;
import com.oleg.droids.components.MovableComponent;
import com.oleg.droids.components.PlayerComponent;
import com.oleg.droids.components.droids.AbstractDroidComponent;
import com.oleg.droids.ui.DroidsGameMenu;
import com.oleg.droids.ui.DroidsMainMenu;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.oleg.droids.configs.GameConfig.*;

public class DroidsApp extends GameApplication {
    private PlayerComponent playerComponent;
    private Entity player;
    private final List<AbstractDroidComponent> teamRed = new ArrayList<>();
    private final List<AbstractDroidComponent> teamBlue = new ArrayList<>();
    public String regim;
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Droids");
        settings.setIntroEnabled(IS_RELEASE);
        settings.setMainMenuEnabled(true);

        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new DroidsMainMenu(teamRed, teamBlue);
            }

            @Override
            public FXGLMenu newGameMenu() {
                return new DroidsGameMenu();
            }
        });
    }

    private static int i = 0;

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                if (player.isActive()) playerComponent.up();
            }
            @Override
            protected void onActionEnd() {
                if (player.isActive()) playerComponent.stop(MovableComponent.UP);
            }
        }, KeyCode.W);
        getInput().addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                if (player.isActive()) playerComponent.down();
            }
            @Override
            protected void onActionEnd() {
                if (player.isActive()) playerComponent.stop(MovableComponent.DOWN);
            }
        }, KeyCode.S);
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                if (player.isActive()) playerComponent.left();
            }
            @Override
            protected void onActionEnd() {
                if (player.isActive()) playerComponent.stop(MovableComponent.LEFT);
            }
        }, KeyCode.A);
        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                if (player.isActive()) playerComponent.right();
            }

            @Override
            protected void onActionEnd() {
                if (player.isActive())
                    playerComponent.stop(MovableComponent.RIGHT);
            }
        }, KeyCode.D);
        getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onAction() {
                if (player.isActive())
                    playerComponent.shoot();
            }
        }, MouseButton.PRIMARY);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("kst blue", teamBlue.size());
        vars.put("kst red", teamRed.size());
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new DroidsAppFactory());
        spawn("background");


        if (Objects.equals(regim, "PvP")) {
            createPlayer(teamBlue.getFirst(), "Player", new Point2D(getAppWidth(), getAppHeight()));
            createBot(teamRed.getFirst(), "Bot", new Point2D(0, 0));

            onIntChangeTo("kst blue", 0, () -> {
                gameOver("Bot");
            });
            onIntChangeTo("kst red", 0, () -> {
                gameOver("Player");
            });
        }

        if (Objects.equals(regim, "Team vs Team")) {
            createPlayer(teamBlue.getFirst(), "Player", new Point2D(0, getAppHeight()));
            for (int j = 1; j < teamBlue.size(); j++) {
                createBot(teamBlue.get(j), "Bot" + i++, FXGLMath.randomPoint(new Rectangle2D(0, (double) (getAppHeight() * 2) / 3, getAppWidth(), (double) getAppHeight() / 3)));
            }
            teamRed.forEach((e) -> createBot(e, "Bot" + i++, FXGLMath.randomPoint(new Rectangle2D(0, 0, getAppWidth(), (double) (getAppHeight()) / 3))));

            onIntChangeTo("kst blue", 0, () -> {
                gameOver("Team Red");
            });
            onIntChangeTo("kst red", 0, () -> {
                gameOver("Team Blue");
            });
        }


        int dist = OUTSIDE_DISTANCE;

        getGameScene().getViewport().setLazy(true);
        getGameScene().getViewport().setBounds(-dist, -dist, getAppWidth() + dist, getAppHeight() + dist);
        getGameScene().getViewport().bindToEntity(player, getAppWidth() / 2.0 - player.getWidth() / 2, getAppHeight() / 2.0 - player.getHeight() / 2);

    }

    @Override
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();

        physics.addCollisionHandler(new BulletPlayerHandler());

        physics.addCollisionHandler(new PlayerPlayerHandler());
    }

    public void createBot(AbstractDroidComponent droidType, String name, Point2D position) {
        SpawnData data = new SpawnData(position)
                .put("isPlayer", false)
                .put("droidType", droidType)
                .put("droidName", name);
        spawn("player", data);
    }
    public void createPlayer(AbstractDroidComponent droidType, String name, Point2D position) {
        if (playerComponent == null) {
            SpawnData data = new SpawnData(position)
                    .put("isPlayer", true)
                    .put("droidType", droidType)
                    .put("droidName", name);
            player = spawn("player", data);
            playerComponent = player.getComponent(PlayerComponent.class);
        }
    }

    public void deathPlayer(Entity killer) {
        getGameScene().getViewport().unbind();
        getGameScene().getViewport().bindToEntity(killer, getAppWidth() / 2.0 - killer.getWidth() / 2, getAppHeight() / 2.0 - killer.getHeight() / 2);
    }

    private void gameOver(String winner) {
        getDialogService().showMessageBox(winner + " win !", () -> {
            teamRed.clear();
            teamBlue.clear();

            getGameController().exit();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
