package com.trenical.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600); // larghezza e altezza iniziali

        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.setTitle("TreniCal - Utente");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
