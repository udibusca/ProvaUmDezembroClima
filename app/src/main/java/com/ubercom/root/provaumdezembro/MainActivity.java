package com.ubercom.root.provaumdezembro;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText imputLatitude = (EditText) findViewById(R.id.imputLatit);
        final EditText imputLongitude = (EditText) findViewById(R.id.imputLongi);
        final ImageView imagemTtempo = (ImageView) findViewById(R.id.imageClima);

        final Button botao = (Button) findViewById(R.id.buttonConsultaClima);

        botao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Uberlândia, Latitude: -18.9113, Longitude: -48.2622

                double latitudeG = Double.parseDouble(imputLatitude.getText().toString());
                double longitudeG = Double.parseDouble(imputLongitude.getText().toString());

                retrofit2.Call<DadosClima> requestDadosClima = RetrofitService.getServico().consulta(latitudeG,longitudeG);

                requestDadosClima.enqueue(new Callback<DadosClima>() {
                    @Override
                    public void onResponse(retrofit2.Call<DadosClima> call, Response<DadosClima> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Nenhum Post Encontrado!",Toast.LENGTH_SHORT).show();
                            Log.i("TAG","Erro : " + response.code());
                        }else{
                            DadosClima dados =  response.body();
                            Log.i("RETORNO"," : " + dados.getCurrently().getIcon().toString());
                            String foto = dados.getCurrently().getIcon().toString();

                            ImageView imagemTempo = (ImageView) findViewById(R.id.imageClima);

                            if (foto.equalsIgnoreCase("partly-cloudy-day")) {
                                imagemTempo.setImageResource(R.drawable.partly_cloudy_day);
                            } else if(foto.equalsIgnoreCase("clear-day")){
                                imagemTempo.setImageResource(R.drawable.clear_day);
                            } else if(foto.equalsIgnoreCase("clear-night")){
                                imagemTempo.setImageResource(R.drawable.clear_night);
                            } else if(foto.equalsIgnoreCase("rain")){
                                imagemTempo.setImageResource(R.drawable.rain);
                            } else if(foto.equalsIgnoreCase("snow")){
                                imagemTempo.setImageResource(R.drawable.snow);
                            } else if(foto.equalsIgnoreCase("wind")){
                                imagemTempo.setImageResource(R.drawable.wind);
                            } else if(foto.equalsIgnoreCase("fog")){
                                imagemTempo.setImageResource(R.drawable.fog);
                            } else if(foto.equalsIgnoreCase("cloudy")){
                                imagemTempo.setImageResource(R.drawable.cloudy);
                            } else if(foto.equalsIgnoreCase("partly-cloudy-night")){
                                imagemTempo.setImageResource(R.drawable.partly_cloudy_night);
                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<DadosClima> call, Throwable t) {
                        // Log de erro do request
                        Log.i("TAG","Erro : " +t.getMessage());
                    }
                });
            }
        });

    }
}