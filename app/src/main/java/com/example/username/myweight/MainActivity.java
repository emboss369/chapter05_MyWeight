package com.example.username.myweight;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private BigDecimal mWeight = new BigDecimal("50.0");
    private Calendar mCal = Calendar.getInstance();

    public static class MainDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("保存処理");
            builder.setMessage("保存します");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 保存処理
                }
            });
            return builder.create();
        }
    }

    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    ((MainActivity)getActivity())
                            .setCalendar(year, monthOfYear, dayOfMonth);
                }
            }, year, month, day);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showData();
        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
    }

    public void onSaveButtonTapped(View view) {
        DialogFragment newFragment = new MainDialogFragment();
        newFragment.show(getFragmentManager(), "MainFragmentDialog");
    }

    public void onDateButtonTapped(View view) {
        DialogFragment picker = new DatePickerFragment();
        picker.show(getFragmentManager(), "datePicker");
    }

    public void setCalendar(int year ,int month, int day) {
        mCal.set(year, month, day);
        showData();
    }
    private void showData() {
        Button weight = (Button) findViewById(R.id.weight);
        weight.setText(String.valueOf(mWeight));

        String today = mCal.get(Calendar.YEAR)
                + "/"
                + (mCal.get(Calendar.MONTH) + 1)
                + "/"
                + mCal.get(Calendar.DATE);
        Button date = (Button) findViewById(R.id.date);
        date.setText(today);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        float h = (float) pref.getInt("HEIGHT", 160);
        float w = mWeight.floatValue();
        float bmi = w/((h/100f)*(h/100f));
        TextView bmiText = (TextView) findViewById(R.id.bmiText);
        bmiText.setText(String.valueOf(bmi));

        float tekisei = ((h/100f)*(h/100f)) * 22.0f;
        TextView tekiseiText = (TextView) findViewById(R.id.tekiseiText) ;
        tekiseiText.setText(String.valueOf(tekisei));

    }

    public void onChangeWeightButtonTapped(View view) {
        switch (view.getId()) {
            case R.id.plus_weight:
                mWeight = mWeight.add(new BigDecimal("0.1"));
                break;
            case R.id.minus_weight:
                mWeight = mWeight.add(new BigDecimal("-0.1"));
                break;
        }
        showData();
    }

    public void onChangeDateButtonTapped(View view) {
        switch (view.getId()) {
            case R.id.plus_date:
                mCal.add(Calendar.DATE, 1);
                break;
            case R.id.minus_date:
                mCal.add(Calendar.DATE, -1);
                break;
        }
        showData();
    }


}
