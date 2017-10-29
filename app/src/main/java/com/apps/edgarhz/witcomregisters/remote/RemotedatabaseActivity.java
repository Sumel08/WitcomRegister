package com.apps.edgarhz.witcomregisters.remote;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.apps.edgarhz.witcomregisters.R;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.EntityUtils;

public class RemotedatabaseActivity extends AppCompatActivity {

    public static final String HOST_C = "http://192.168.43.214/witcom/GetDataC.php";
    public static final String HOST_W = "http://192.168.43.214/witcom/GetDataW.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remotedatabase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button button = (Button)findViewById(R.id.boton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tr = new Thread() {
                    @Override
                    public void run() {
                        final String Resultado = leer();
                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        cargaListado(obtDatosJSON(Resultado));
                                    }
                                });
                    }
                };
                tr.start();
            }
        });


    }

    public void cargaListado(ArrayList<String> datos){
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);
        ListView listado = (ListView) findViewById(R.id.listView1);
        listado.setAdapter(adaptador);
    }

    public String leer(){
        RadioButton rbC = (RadioButton)findViewById(R.id.rbC);
        RadioButton rbW = (RadioButton)findViewById(R.id.rbw);
        String host = null;

        if(rbC.isChecked()) {
            host = HOST_C;
        }

        else if(rbW.isChecked()) {
            host = HOST_W;
        }
        else
            return null;

        HttpClient cliente =new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();
        HttpGet httpget = new HttpGet(host);
        String resultado=null;
        try {
            HttpResponse response = cliente.execute(httpget,contexto);
            HttpEntity entity = response.getEntity();
            resultado = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ArrayList<String> obtDatosJSON(String response){
        ArrayList<String> listado= new ArrayList<String>();
        try {
            JSONArray json= new JSONArray(response);
            String texto="";
            for (int i=0; i<json.length();i++){
                texto = "BOLETA:     "+json.getJSONObject(i).getString("boleta") +"\n"+
                        "NOMBRE:    "+json.getJSONObject(i).getString("nombre_alumno") +"\n"+
                        "EVENTO:     "+json.getJSONObject(i).getString("nombre_evento");
                listado.add(texto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listado;
    }


}
