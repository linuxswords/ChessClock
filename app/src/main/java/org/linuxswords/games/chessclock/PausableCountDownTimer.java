package org.linuxswords.games.chessclock;

import android.os.CountDownTimer;

public abstract class PausableCountDownTimer
{
    private CountDownTimer countDownTimer;
    private long remainingTime = 0L;
    private long millisUntilFinished = 0L;

    public PausableCountDownTimer(long millisUntilFinished)
    {
        this.millisUntilFinished = millisUntilFinished;
        this.remainingTime = millisUntilFinished;
    }

    private boolean isPaused = true;

    public abstract void onTimerTick(Long millisUntilFinished);

    public abstract void onTimerFinish();

    public synchronized void start()
    {
        if (isPaused) {
            this.countDownTimer = new CountDownTimer(remainingTime, 1_000L)
            {
                @Override
                public void onTick(long millisUntilFinished)
                {
                    remainingTime = millisUntilFinished ;
                    onTimerTick(millisUntilFinished);
                }

                @Override
                public void onFinish()
                {
                    onTimerFinish();
                    restart();
                }
            };
            this.countDownTimer.start();
            isPaused = false;
        }
    }

    public void pause()
    {
        if (!isPaused) {
            countDownTimer.cancel();
        }
        isPaused = true;
    }

    public void restart()
    {
        countDownTimer.cancel();
        remainingTime = millisUntilFinished;
        isPaused = true;
    }
}
