package org.example.sm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        AnchorPane root=fxmlLoader.load();
        Image background = new Image("file:///D:/OOP%20Project/SM/src/main/java/image/background.jpg");
        ImageView backgroundView=new ImageView(background);
        StackPane group=new StackPane(backgroundView,root);
//        root.getChildren().add(backgroundView);
        Scene scene = new Scene(group, background.getWidth(), background.getHeight());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}