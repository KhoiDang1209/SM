package org.example.sm;

import image.BackGroundScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        AnchorPane root=fxmlLoader.load();
        BackGroundScene backGround=new BackGroundScene();
        StackPane group=new StackPane(backGround.getBackgroundView(),root);
        Scene scene = new Scene(group, backGround.getBackgroundWidth(), backGround.getBackgroundHeight());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}