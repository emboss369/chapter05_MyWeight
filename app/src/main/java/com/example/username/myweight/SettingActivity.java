package com.example.username.myweight;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    RadioGroup mRadio;
    SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mRadio = (RadioGroup)findViewById(R.id.sexuality);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int sexuality = pref.getInt("SEXUALITY",R.id.man);
        mRadio.check(sexuality);
        mSeekBar = (SeekBar)findViewById(R.id.seekBar);
        int height = pref.getInt("HEIGHT",160);
        mSeekBar.setProgress(height);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    showData();
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        showData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("SEXUALITY", mRadio.getCheckedRadioButtonId());
        editor.commit();
    }
    private void showData() {
        TextView heightText = (TextView) findViewById(R.id.height);
        heightText.setText(String.valueOf(mSeekBar.getProgress()));
    }

}
