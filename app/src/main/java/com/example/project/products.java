package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class products extends AppCompatActivity {
    String s;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);



        ListView pro_list = (ListView) findViewById(R.id.Product_list);
        TextView name = (TextView) findViewById(R.id.catname);
        ArrayAdapter<String> prod = new ArrayAdapter<String >(getApplicationContext(), android.R.layout.simple_list_item_1);

        pro_list.setAdapter(prod);

        DataBase db = new DataBase(getApplicationContext());

        String name_cat = getIntent().getExtras().getString("Catname");

        int id_cat = getIntent().getExtras().getInt("CatID");

        name.setText(name_cat);

        Cursor c =  db.getAllProducts((id_cat));

        while (!c.isAfterLast()) {
            prod.add(c.getString(1));
            prod.add( "Price :  "+c.getString(2));
            prod.add("__________________________________");
            c.moveToNext();

        }
        registerForContextMenu(pro_list);


        pro_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                s = pro_list.getItemAtPosition(position).toString();

                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_manu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.Home:
                i=new Intent(this,Home.class);

                startActivity(i);
                return true;

            case R.id.cart:
                i=new Intent(getApplicationContext(),ShoppingCart.class);
                startActivity(i);
                return true;

            case R.id.mSearch:
                 i=new Intent(this,Search.class);

                startActivity(i);
                return true;
            case R.id.logout:
                i=new Intent(products.this,Login.class);
                startActivity(i);
                return true;

        }

        return false;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.pro_menu,menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public static ArrayList <String> id_pro = new ArrayList<String>();
    public static ArrayList <Integer> quantity_pro = new ArrayList<Integer>();

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.scart:
                if(! id_pro.contains(s))
                id_pro.add(s);
                return true;
        }
        return false;
    }
}

