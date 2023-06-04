package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.util.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String key = "07392cf2ac0c40f98bd3";
    private static final Logger logger = Logger.getLogger("MainActivity");
    private String barcodeNumber;
    private Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = (Button)findViewById(R.id.button_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setBeepEnabled(false);
                integrator.initiateScan();
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