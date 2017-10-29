package com.apps.edgarhz.witcomregisters.local;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.apps.edgarhz.witcomregisters.R;
import com.apps.edgarhz.witcomregisters.provider.ClientesProvider;

import org.json.JSONArray;

import java.util.ArrayList;

public class LocaldatabaseActivity extends AppCompatActivity {

    private AppCompatActivity appCompatActivity = this;

    private Button btnConsultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localdatabase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnConsultar = (Button)findViewById(R.id.boton);
        btnConsultar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Columnas de la tabla a recuperar
                String[] projection = new String[]{
                        ClientesProvider.Clientes._ID,
                        ClientesProvider.Clientes.COL_NOMBRE,
                        ClientesProvider.Clientes.COL_BOLETA,
                        ClientesProvider.Clientes.COL_TIPO,
                        ClientesProvider.Clientes.COL_EVENTO};

                Uri clientesUri = ClientesProvider.CONTENT_URI;

                ContentResolver cr = getContentResolver();

                //Hacemos la consulta
                Cursor cur = cr.query(clientesUri,
                        projection, //Columnas a devolver
                        null,       //Condición de la query
                        null,       //Argumentos variables de la query
                        null);      //Orden de los resultados

                if (cur.moveToFirst()) {

                    int colNombre = cur.getColumnIndex(ClientesProvider.Clientes.COL_NOMBRE);
                    int colBoleta = cur.getColumnIndex(ClientesProvider.Clientes.COL_BOLETA);
                    int colTipo = cur.getColumnIndex(ClientesProvider.Clientes.COL_TIPO);
                    int colEvento = cur.getColumnIndex(ClientesProvider.Clientes.COL_EVENTO);

                    ArrayList<String> listado = new ArrayList<String>();
                    String texto = "";

                    do {
                        texto = "BOLETA:     " + cur.getString(colBoleta) + "\n" +
                                "NOMBRE:    " + cur.getString(colNombre) + "\n" +
                                "TIPO:           " + cur.getString(colTipo) + "\n" +
                                "EVENTO:     " + cur.getString(colEvento);

                        listado.add(texto);
                    } while (cur.moveToNext());
                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(appCompatActivity, android.R.layout.simple_list_item_1, listado);
                    ListView listadolv = (ListView) findViewById(R.id.listView1);
                    listadolv.setAdapter(adaptador);
                }
            }
        });

    }




}
