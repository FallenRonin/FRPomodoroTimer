package by.poltavetsav.frpomodorotimer;

import by.poltavetsav.frpomodorotimer.util.DefaultSoundPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Pomodoro timer. FallenRonin");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();
        FormController formController = fxmlLoader.getController();
        formController.setSoundPlayer(new DefaultSoundPlayer());
    }

    public static void main(String[] args) {
        launch();
    }
}