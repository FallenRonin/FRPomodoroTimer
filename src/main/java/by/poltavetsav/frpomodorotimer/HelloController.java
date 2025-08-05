package by.poltavetsav.frpomodorotimer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import by.poltavetsav.frpomodorotimer.util.*;

public class HelloController {
    @FXML
    private Label timerText;
    @FXML
    private Button startTimerButton;
    @FXML
    private Button relaxButton;

    private static final int WORK_DURATION = 1500;
    private static final int RELAX_DURATION = 300;

    @FXML
    protected void onStartTimerButtonClick() {
        // 1500 секунд = 25 минут (для Pomodoro)
        SingletonTimer.getInstance().start(WORK_DURATION, this::updateTimerUI, this::workTimerFinished);
    }

    @FXML
    protected void onRelaxButtonClick() {
        SingletonTimer.getInstance().start(RELAX_DURATION, this::updateTimerUI, this::relaxTimerFinished);
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
        playAlarm();
        startTimerButton.setVisible(false);
        relaxButton.setVisible(true);
    }

    private void relaxTimerFinished() {
        timerText.setText("00:00 - Time's up!");
        playAlarm();
        relaxButton.setVisible(false);
        startTimerButton.setVisible(true);
    }

    private void playAlarm() {
        SoundPlayer.PlaySound("/sounds/EndAlarm.mp3");
    }

    @FXML
    private void stopTimer() {
        SingletonTimer.getInstance().stop();
        timerText.setText("Таймер остановлен");
    }
}