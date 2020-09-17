package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
TextView ph, temperature;
Button predictbutton;

ApiInterface apiInterface;
List<String> vizdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ph = findViewById(R.id.pdata);
        temperature = findViewById(R.id.tempdata);

        predictbutton = findViewById(R.id.pbutton);

        vizdata=new ArrayList<>();

        predictbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((ph.getText().toString().length()!=0) && (temperature.getText().toString().length()!=0))
                {
                        hitAPICall(ph,temperature);
                }
            }
        });
    }

    private void hitAPICall(TextView ph, TextView temperature) {
        vizdata.add(ph.toString());
        vizdata.add(temperature.toString());

        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        Call<Pojo> call = apiInterface.getAPIResponse(vizdata);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(@NonNull Call<Pojo> call, @NonNull Response<Pojo> response) {
                Pojo pojo=response.body();
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("soil Prediction");
                alertDialog.setMessage("Predicted Result is "+pojo.getPrediction());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }

            @Override
            public void onFailure(@NonNull Call<Pojo> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"Oops try again!...",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
