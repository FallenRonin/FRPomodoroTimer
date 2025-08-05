package by.poltavetsav.frpomodorotimer.util;

import javafx.application.Platform;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SingletonTimer {
    private static SingletonTimer instance;
    private ScheduledExecutorService executor;
    private AtomicLong remainingTime = new AtomicLong(0);
    private Runnable onTickCallback;
    private Runnable onFinishCallback;

    private SingletonTimer() {}

    public static synchronized SingletonTimer getInstance() {
        if (instance == null) {
            instance = new SingletonTimer();
        }
        return instance;
    }

    public void start(long seconds, Runnable onTick, Runnable onFinish) {
        stop(); // Останавливаем предыдущий таймер

        this.onTickCallback = onTick;
        this.onFinishCallback = onFinish;
        remainingTime.set(seconds);

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            long current = remainingTime.decrementAndGet();

            Platform.runLater(() -> {
                if (current > 0) {
                    if (onTickCallback != null) onTickCallback.run();
                } else {
                    if (onFinishCallback != null) onFinishCallback.run();
                    stop();
                }
            });
        }, 1, 1, TimeUnit.SECONDS); // Обновляем каждую секунду
    }

    public void stop() {
        if (executor != null) {
            executor.shutdownNow();
            executor = null;
        }
    }

    public long getRemainingTime() {
        return remainingTime.get();
    }
}