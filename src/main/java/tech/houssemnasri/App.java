package tech.houssemnasri;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import tech.houssemnasri.api.INodeView;
import tech.houssemnasri.property.ComplexIntegerProperty;
import tech.houssemnasri.property.ComplexObjectProperty;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
