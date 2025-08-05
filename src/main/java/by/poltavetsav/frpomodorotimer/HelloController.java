package by.poltavetsav.frpomodorotimer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import by.poltavetsav.frpomodorotimer.util.*;
import javafx.scene.control.Slider;

public class HelloController {
    @FXML
    private Label timerText;
    @FXML
    private Button startTimerButton;
    @FXML
    private Slider volumeSlider;

    private static final int WORK_DURATION = 1500;
    private static final int RELAX_DURATION = 300;
    private static boolean isRelaxing = false;

    @FXML
    protected void onStartTimerButtonClick() {
        int duration = isRelaxing ? RELAX_DURATION : WORK_DURATION;
        SingletonTimer.getInstance().start(duration, this::updateTimerUI, this::workTimerFinished);
        isRelaxing = !isRelaxing;
        startTimerButton.setVisible(false);
    }

    private void updateTimerUI() {
        // Конвертируем миллисекунды в минуты:секунды
        long remaining = SingletonTimer.getInstance().getRemainingTime();
        long minutes = remaining / 60;
        long seconds = remaining % 60;
        timerText.setText(String.format("Time left: %02d:%02d", minutes, seconds));
    }

    private void workTimerFinished() {
        timerText.setText("00:00 - Time's up!");
        SoundPlayer.PlaySound("/sounds/EndAlarm.mp3", volumeSlider.getValue() / 100);
        startTimerButton.setText("Relax");
    }

    @FXML
    private void stopTimer() {
        SingletonTimer.getInstance().stop();
        timerText.setText("Таймер остановлен");
    }
}