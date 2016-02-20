package br.com.servicofacil.servicofacil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.servicofacil.activity.ServicoLista;
import br.com.servicofacil.activity.UsuarioActivity;
import br.com.servicofacil.activity.UsuarioList;

/**
 * Created by P001241 on 19/10/2015.
 */
public class Splash extends Activity implements Runnable {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Handler handler = new Handler();
        handler.postDelayed(this, 3000);
    }

    public void run(){
        startActivity(new Intent(this, ServicoLista.class));
        finish();
    }

}
