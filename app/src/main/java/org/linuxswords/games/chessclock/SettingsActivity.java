package org.linuxswords.games.chessclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import org.linuxswords.games.chessclock.time.TimeSettings;
import org.linuxswords.games.chessclock.time.TimeSettingsManager;

public class SettingsActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        findViewById(R.id.settingsCancelButton).setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        findViewById(R.id.timeSettingThreeZero).setOnClickListener(v -> this.setTimeAndCloseView(TimeSettings.FIFTEEN_PLUS_ZERO));
        findViewById(R.id.timeSettingThreePlusFive).setOnClickListener(v -> this.setTimeAndCloseView(TimeSettings.FIFTEEN_PLUS_FIVE));

        findViewById(R.id.timeSettingFiveZero).setOnClickListener(v -> this.setTimeAndCloseView(TimeSettings.FIVE_PLUS_ZERO));
        findViewById(R.id.timeSettingFivePlusFive).setOnClickListener(v -> this.setTimeAndCloseView(TimeSettings.FIVE_PLUS_FIVE));

        findViewById(R.id.timeSettingTenZero).setOnClickListener(v -> this.setTimeAndCloseView(TimeSettings.TEN_PLUS_ZERO));
        findViewById(R.id.timeSettingTenPlusFive).setOnClickListener(v -> this.setTimeAndCloseView(TimeSettings.TEN_PLUS_FIVE));


        findViewById(R.id.timeSettingFifteenZero).setOnClickListener(v -> this.setTimeAndCloseView(TimeSettings.FIFTEEN_PLUS_ZERO));
        findViewById(R.id.timeSettingFifteenPlusFive).setOnClickListener(v -> this.setTimeAndCloseView(TimeSettings.FIFTEEN_PLUS_FIVE));
    }

    private void setTimeAndCloseView(TimeSettings timeSetting)
    {
        TimeSettingsManager timeSettingsManager = TimeSettingsManager.instance();
        timeSettingsManager.setCurrent(timeSetting);
        startActivity(new Intent(this, MainActivity.class));
    }
}
