package org.linuxswords.games.chessclock.time;

import android.graphics.Color;
import android.widget.TextView;

public class PlayerClock
{
    public static final int BACKGROUND_COLOR = Color.parseColor("#403E3E");
    private static final int WARN_COLOR = Color.RED;
    private static final long WARN_THRESH_HOLD_IN_MILLIS = 1L * 60L * 1_000L;
    private final PausableCountDownTimer countDownTimer;
    private final TextView view;
    private final long startTimeInMillis;

    public PlayerClock(long initialMilliseconds, TextView view)
    {
        this.startTimeInMillis = initialMilliseconds;
        this.view = view;
        countDownTimer = new PausableCountDownTimer(initialMilliseconds)
        {

            @Override
            public void onTimerTick(Long millisUntilFinished)
            {
                String timeText = TimeFormatter.convertMillisIntoDisplayableTime(millisUntilFinished);
                if(millisUntilFinished<WARN_THRESH_HOLD_IN_MILLIS) {
                    view.setTextColor(WARN_COLOR);
                }

                view.setText(timeText);
            }

            @Override
            public void onTimerFinish()
            {
                view.setTextColor(WARN_COLOR);
                view.setText("lost");
            }
        };
    }


    public PlayerClock showStartTime()
    {
        this.view.setText(TimeFormatter.convertMillisIntoDisplayableTime(this.startTimeInMillis));
        return this;
    }

    public PlayerClock start()
    {
        this.countDownTimer.start();

        this.view.setBackgroundColor(Color.WHITE);
        this.view.setTextColor(getDynamicText_Color(this.countDownTimer.getRemainingTime(), BACKGROUND_COLOR));
        return this;
    }

    public PlayerClock restart()
    {
        this.countDownTimer.restart();
        return this;
    }

    public PlayerClock pause()
    {
        this.countDownTimer.pause();
        this.view.setBackgroundColor(BACKGROUND_COLOR);
        this.view.setTextColor(getDynamicText_Color(this.countDownTimer.getRemainingTime(), Color.WHITE));
        return this;
    }

    private int getDynamicText_Color(long currentTimeInMillis, int fallBackColor)
    {
        int result =fallBackColor;
        if (currentTimeInMillis <= WARN_THRESH_HOLD_IN_MILLIS) {
            result = WARN_COLOR;
        }
        return result;
    }
}
