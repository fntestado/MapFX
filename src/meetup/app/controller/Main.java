package meetup.app.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Stage primaryStage;
    public static BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        showMainView();
        showMapView();
    }

    @FXML
    public void showMapView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/map_view.fxml"));
        BorderPane showMap = loader.load();
        mainLayout.getChildren().setAll(showMap);
    }

    @FXML
    public void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/main_view.fxml"));
        mainLayout = loader.load();

        mainLayout.setPrefHeight(505);
        mainLayout.setPrefWidth(701);

        Scene scene = new Scene(mainLayout);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        showMapView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
