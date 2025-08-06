package by.poltavetsav.frpomodorotimer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import by.poltavetsav.frpomodorotimer.util.*;
import javafx.scene.control.Slider;

import java.util.concurrent.TimeUnit;

public class FormController {

    @FXML
    private Label timerText;
    @FXML
    private Button startTimerButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Label timeoutsLabel;

    private SoundPlayer soundPlayer;
    private static final int WORK_DURATION = (int) TimeUnit.MINUTES.toSeconds(25);
    private static final int RELAX_DURATION = (int) TimeUnit.MINUTES.toSeconds(5);
    private static final int LARGE_RELAX_DURATION = (int) TimeUnit.MINUTES.toSeconds(15);
    private static final int TIMEOUTS_BEFORE_LARGE_BREAK = 4;
    private boolean isRelaxing = false;
    private int timeoutCounter = 0;

    public void setSoundPlayer(SoundPlayer soundPlayer) {
        this.soundPlayer = soundPlayer;
    }

    @FXML
    protected void onStartTimerButtonClick() {
        int duration = isRelaxing ? RELAX_DURATION : WORK_DURATION;
        if (timeoutCounter == TIMEOUTS_BEFORE_LARGE_BREAK) {
            timeoutCounter = 0;
            duration = LARGE_RELAX_DURATION;
        }
        SingletonTimer.getInstance().start(duration, this::updateTimerUI, this::onTimerFinished);
        startTimerButton.setVisible(false);
    }

    private void updateTimerUI() {
        // Time converting
        long remaining = SingletonTimer.getInstance().getRemainingTime();
        long minutes = remaining / 60;
        long seconds = remaining % 60;
        timerText.setText(String.format("Time left: %02d:%02d", minutes, seconds));
    }

    private void onTimerFinished() {
        timerText.setText("00:00 - Time's up!");
        soundPlayer.playSound("/sounds/EndAlarm.mp3", volumeSlider.getValue() / 100);
        startTimerButton.setText(isRelaxing ? "Work" : "Relax");
        startTimerButton.setVisible(true);
        timeoutCounter += isRelaxing ? 1 : 0;
        isRelaxing = !isRelaxing;
        timeoutsLabel.setText(String.format("Timeouts: %d", timeoutCounter));
    }

    @FXML
    private void stopTimer() {
        SingletonTimer.getInstance().stop();
        timerText.setText("Timer stopped");
    }
}