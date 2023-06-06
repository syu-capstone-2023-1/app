package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.util.RetrofitService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddActivity extends AppCompatActivity{

EditText text1, text2, text3;

DatePicker dPicker;
int year = 0, month = 0, day = 0;

DBActivityHelper mDbOpenHelper;


    private static final String key = "07392cf2ac0c40f98bd3";
    private static final Logger logger = Logger.getLogger("MainActivity");
    private String barcodeNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mDbOpenHelper = new DBActivityHelper(this);
        mDbOpenHelper.open();

        text1 =(EditText)findViewById(R.id.editText);
        text2 =(EditText)findViewById(R.id.editText2);
        text3 =(EditText)findViewById(R.id.editText3);

        dPicker = (DatePicker)findViewById(R.id.datePicker);

        dPicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int yy, int mm, int dd) {
                year = yy;
                month = mm;
                day = dd;
            }
        });













        BottomNavigationView bottomNavigationView = findViewById(R.id.mainNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.backM:
                    {
                        finish();
                        return true;
                    }
                    case R.id.barcodeM:
                    {
                        IntentIntegrator integrator = new IntentIntegrator(AddActivity.this);
                        integrator.setBeepEnabled(false);
                        integrator.initiateScan();
                        return true;
                    }
                    case R.id.okM:
                    {

                        finish();
                        return true;
                    }
                }

                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == IntentIntegrator.REQUEST_CODE){
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (result != null) {
                    if (result.getContents() != null) {
                        Toast.makeText(this, "Scanned: " + result.getContents() + "\nFormat:" + result.getFormatName(), Toast.LENGTH_LONG).show();
                        barcodeNumber = result.getContents();
                        call();
                    }
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void call() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.foodsafetykorea. go.kr/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        System.out.println("11111111111111111111111");

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<String> barcodeInfo = retrofitService.getBarcodeInfo(key, "sample", "json", 1, 5, barcodeNumber);

        System.out.println("22222222222222222222222");

        barcodeInfo.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "정상 응답 : " + response.body(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "비정상 응답 : " + response.body(), Toast.LENGTH_LONG).show();
                    logger.warning(response.errorBody().toString());
                }

                TextView view = (TextView) findViewById(R.id.textview);
                view.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "요청 실패 : " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                logger.warning(t.getLocalizedMessage());
                logger.warning(t.getMessage());

                TextView view = (TextView) findViewById(R.id.textview);
                view.setText(t.getLocalizedMessage());
            }
        });
    }
}