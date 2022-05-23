package org.linuxswords.games.chessclock.time;

public enum TimeSettings
{
    TEST(2, 0),
    THREE_PLUS_ZERO(3, 0),
    THREE_PLUS_FIVE(3, 5),
    FIVE_PLUS_ZERO(5, 0),
    FIVE_PLUS_FIVE(5, 5),
    TEN_PLUS_ZERO(10, 0),
    TEN_PLUS_FIVE(10, 5),
    FIFTEEN_PLUS_ZERO(15, 0),
    FIFTEEN_PLUS_FIVE(15, 5)
    ;

    public final int minutes;
    public final int increment;
    TimeSettings(int minutes, int increment)
    {
        this.minutes = minutes;
        this.increment = increment;
    }

    public int getIncrement()
    {
        return increment;
    }

    public long minutesAsMilliSeconds()
    {
        return this.minutes * 60L * 1_000L;
    }
}
