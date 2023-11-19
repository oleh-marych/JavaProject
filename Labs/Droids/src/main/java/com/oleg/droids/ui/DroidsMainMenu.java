package com.oleg.droids.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.oleg.droids.DroidsApp;
import com.oleg.droids.components.droids.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.*;

public class DroidsMainMenu extends FXGLMenu {
    private List<AbstractDroidComponent> teamRed, teamBlue;
    private AbstractDroidComponent newDroid;
    private String currentTeam = "Blue";

    public DroidsMainMenu(List<AbstractDroidComponent> teamRed, List<AbstractDroidComponent> teamBlue) {
        super(MenuType.GAME_MENU);

        this.teamBlue = teamBlue;
        this.teamRed = teamRed;

        var bg = texture("background.png", getAppWidth() + 50, getAppHeight() + 250);
        bg.setTranslateY(-85);
        bg.setTranslateX(-10);

        var titleView = getUIFactoryService().newText(getSettings().getTitle(), 48);
        centerTextBind(titleView, getAppWidth() / 2.0, 100);

        VBox group = new VBox(
                new MenuButton("1 vs 1", this::subMenuForPvP),
                new MenuButton("Team vs Team", this::subMenuForTvT),
                new MenuButton("Exit", this::fireExit)
        );

        group.setAlignment(Pos.CENTER);
        group.setSpacing(15);

        group.setTranslateX(100);
        group.setTranslateY(450);

        getContentRoot().getChildren().addAll(bg, titleView, group);
    }

    private void subMenuForTvT() {
        ((DroidsApp) getApp()).regim = "Team vs Team";
        var team = getUIFactoryService().newText("Team Blue");
        var title = getUIFactoryService().newText("Choose type of droid for Player:");
        var warn = getUIFactoryService().newText("");

        currentTeam = "Blue";
        VBox subMenu = new VBox(
                team,
                title,
                new MenuButton("Gunner Droid", () -> {
                    newDroid = new GunnerComponent();
                    newDroid.setTeam(currentTeam);

                    createDroid();
                    title.setText("Choose type of droid for Bot:   ");
                }),
                new MenuButton("Tank Droid", () -> {
                    newDroid = new TankComponent();
                    newDroid.setTeam(currentTeam);

                    createDroid();
                    title.setText("Choose type of droid for Bot:   ");
                }),
                new MenuButton("Mortar Droid", () -> {
                    newDroid = new MortarComponent();
                    newDroid.setTeam(currentTeam);

                    createDroid();
                    title.setText("Choose type of droid for Bot:   ");
                }),
                new MenuButton("Create Red Team", () -> {
                    currentTeam = "Red";
                    team.setText("Red Team");
                }),
                new MenuButton("Create Blue Team", () -> {
                    currentTeam = "Blue";
                    team.setText("Blue Team");
                }),
                new MenuButton("Start Game", () -> {
                    if (teamRed.size() > 0 && teamBlue.size() > 0) {
                        getContentRoot().getChildren().removeLast();
                        fireNewGame();
                    }
                    else {
                        warn.setText("Each team must have at least 1 player");
                    }
                }),
                warn
        );

        subMenu.setAlignment(Pos.CENTER);
        subMenu.setSpacing(15);
        subMenu.setTranslateY(300);
        subMenu.setTranslateX(500);

        if (getContentRoot().getChildren().size() == 4)
            getContentRoot().getChildren().set(3, subMenu);
        else
            getContentRoot().getChildren().add(subMenu);
    }

    private void subMenuForPvP() {
        ((DroidsApp) getApp()).regim = "PvP";
        var title = getUIFactoryService().newText("Choose type of droid for Player:");

        currentTeam = "Blue";
        VBox subMenu = new VBox(
                title,
                new MenuButton("Gunner Droid", () -> {
                    newDroid = new GunnerComponent();
                    newDroid.setTeam(currentTeam);

                    createDroid();
                    title.setText("Choose type of droid for Bot:   ");
                    currentTeam = "Red";

                    if (teamRed.size() == 1 && teamBlue.size() == 1) {
                        getContentRoot().getChildren().removeLast();
                        fireNewGame();
                    }
                }),
                new MenuButton("Tank Droid", () -> {
                    newDroid = new TankComponent();
                    newDroid.setTeam(currentTeam);

                    createDroid();
                    title.setText("Choose type of droid for Bot:   ");
                    currentTeam = "Red";

                    if (teamRed.size() == 1 && teamBlue.size() == 1) {
                        getContentRoot().getChildren().removeLast();
                        fireNewGame();
                    }
                }),
                new MenuButton("Mortar Droid", () -> {
                    newDroid = new MortarComponent();
                    newDroid.setTeam(currentTeam);

                    createDroid();
                    title.setText("Choose type of droid for Bot:   ");
                    currentTeam = "Red";

                    if (teamRed.size() == 1 && teamBlue.size() == 1) {
                        getContentRoot().getChildren().removeLast();
                        fireNewGame();
                    }
                })
        );

        subMenu.setAlignment(Pos.CENTER);
        subMenu.setSpacing(15);
        subMenu.setTranslateY(300);
        subMenu.setTranslateX(500);

        if (getContentRoot().getChildren().size() == 4)
            getContentRoot().getChildren().set(3, subMenu);
        else
            getContentRoot().getChildren().add(subMenu);
    }

    private void createDroid() {
        if (newDroid == null) return;

        if (Objects.equals(newDroid.getTeam(), "Blue")) {
            teamBlue.add(newDroid);
        }
        else {
            teamRed.add(newDroid);
        }

        newDroid = null;
    }

    private static class MenuButton extends StackPane {
        private String name;
        private Runnable action;
        private Text text;

        public MenuButton(String name, Runnable action) {
            this.name = name;
            this.action = action;

            text = getUIFactoryService().newText(name, Color.WHITE, 24.0);

            text.fillProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(Color.BLUE)
                            .otherwise(Color.WHITE)
            );

            getChildren().addAll(text);

            setOnMouseClicked((e) -> action.run());
        }
    }
}
