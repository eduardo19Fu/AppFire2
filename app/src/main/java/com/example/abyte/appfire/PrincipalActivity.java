package com.example.abyte.appfire;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.abyte.appfire.Modelo.Producto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.abyte.appfire.*;


public class PrincipalActivity  extends AppCompatActivity {
    private List<Producto> listProducto = new ArrayList<Producto>();
    ArrayAdapter<Producto> arrayAdapterProducto;
    EditText nombreP, stockP;
    ListView listV_producto;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Producto productoSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        nombreP = (EditText) findViewById(R.id.txt_nombre);
        stockP = (EditText)findViewById(R.id.txt_stock);
        listV_producto= (ListView) findViewById(R.id.lv_datosProductos);
        inicializarFirebase();
        listarDatos();
        listV_producto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productoSelected = (Producto) parent.getItemAtPosition(position);
                nombreP.setText(productoSelected.getNombre());
                stockP.setText(productoSelected.getStock());

            }
        });
    }
    private void listarDatos() {
        databaseReference.child("producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listProducto.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    Producto p = objSnaptshot.getValue(Producto.class);
                    listProducto.add(p);
                    arrayAdapterProducto = new ArrayAdapter<Producto>(PrincipalActivity.this, android.R.layout.simple_list_item_1, listProducto);
                    listV_producto.setAdapter(arrayAdapterProducto);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String nombre = nombreP.getText().toString();
        String stock = stockP.getText().toString();
        switch (item.getItemId()){
            case R.id.icon_add:{
                if (nombre.equals("")||stock.equals(""))
                {
                    validacion();
                }else {
                    Producto p = new Producto();
                    p.setId(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setStock(stock);
                    databaseReference.child("producto").child(p.getId()).setValue(p);
                    Toast.makeText(this,"Agregar",Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }
                break;
            }
            case R.id.icon_save:{
                if (nombre.equals("") || stock.equals("")){
                    validacion();
                }else{
                    Producto p = new Producto();
                    p.setId(productoSelected.getId());
                    p.setNombre(nombreP.getText().toString().trim());
                    p.setStock(stockP.getText().toString().trim());
                    databaseReference.child("producto").child(p.getId()).setValue(p);
                    Toast.makeText(this,"Actualizado",Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }

                break;

            }
            case R.id.icon_delete:{
                if(nombre.equals("") || stock.equals("")){
                    validacion();
                }else {
                    Producto p = new Producto();
                    p.setId(productoSelected.getId());
                    databaseReference.child("producto").child(p.getId()).removeValue();
                    Toast.makeText(this,"Eliminado", Toast.LENGTH_LONG).show();
                    limpiarCajas();

                }

                break;
            }


            default:
                break;
        }
        return true;
    }
    private void limpiarCajas() {
        nombreP.setText("");
        stockP.setText("");
    }

    private void validacion()
    {
        String nombre = nombreP.getText().toString();
        String stock = stockP.getText().toString();

        if (nombre.equals("")){
            nombreP.setError("Required");
        }
        else if (stock.equals("")){
            stockP.setError("Required");
        }

    }
}



