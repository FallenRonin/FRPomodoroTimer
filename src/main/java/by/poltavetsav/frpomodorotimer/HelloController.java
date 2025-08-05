package by.poltavetsav.frpomodorotimer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import by.poltavetsav.frpomodorotimer.util.*;

public class HelloController {
    @FXML
    private Label timerText;

    @FXML
    protected void onStartTimerButtonClick() {
        // 1500 секунд = 25 минут (для Pomodoro)
        SingletonTimer.getInstance().start(10, this::updateTimerUI, this::timerFinished);
    }

    private void updateTimerUI() {
        // Конвертируем миллисекунды в минуты:секунды
        long remaining = SingletonTimer.getInstance().getRemainingTime();
        long minutes = remaining / 60;
        long seconds = remaining % 60;
        timerText.setText(String.format("Time left: %02d:%02d", minutes, seconds));
    }

    private void timerFinished() {
        timerText.setText("00:00 - Time's up!");
    }

    @FXML
    private void stopTimer() {
        SingletonTimer.getInstance().stop();
        timerText.setText("Таймер остановлен");
    }
}