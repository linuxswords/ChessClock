package org.linuxswords.games.chessclock;

import android.widget.TextView;
import java.util.concurrent.TimeUnit;

public class PlayerClock
{
    private final PausableCountDownTimer countDownTimer;
    private TextView view;
    private final long startTimeInMillis;

    public PlayerClock(long initialSeconds, TextView view)
    {
        this.startTimeInMillis = initialSeconds * 1_000L;
        this.view = view;
        countDownTimer = new PausableCountDownTimer(initialSeconds * 1_000L)
        {

            @Override
            public void onTimerTick(Long millisUntilFinished)
            {
                String timeText = convertMillisIntoDisplayableTime(millisUntilFinished);
                view.setText(timeText);
            }

            @Override
            public void onTimerFinish()
            {
                view.setText("ur dead");

            }
        };
    }

    private static String convertMillisIntoDisplayableTime(Long millisUntilFinished)
    {
        return String.format("%dm %ds",
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
        );
    }

    public PlayerClock showStartTime()
    {
        this.view.setText(convertMillisIntoDisplayableTime(this.startTimeInMillis));
        return this;
    }

    public PlayerClock start()
    {
        System.out.println("start called");
        this.countDownTimer.start();
        return this;
    }

    public PlayerClock restart()
    {
        System.out.println("restart called");
        this.countDownTimer.restart();
        return this;
    }

    public PlayerClock pause()
    {
        System.out.println("pause called");
        this.countDownTimer.pause();
        return this;
    }
}
