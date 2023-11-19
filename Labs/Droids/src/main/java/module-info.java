module com.oleg.droids {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens assets.textures;

    opens com.oleg.droids to javafx.fxml;
    exports com.oleg.droids;
    opens com.oleg.droids.components to javafx.fxml;
    exports com.oleg.droids.components;
    opens com.oleg.droids.collision to javafx.fxml;
    exports com.oleg.droids.collision;
    opens com.oleg.droids.components.droids to javafx.fxml;
    exports com.oleg.droids.components.droids;
    opens com.oleg.droids.configs to javafx.fxml;
    exports com.oleg.droids.configs;
}