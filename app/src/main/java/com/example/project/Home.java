package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageView Classic = (ImageView) findViewById(R.id.Classic);
        ImageView heel = (ImageView) findViewById(R.id.Heels);
        ImageView boot = (ImageView) findViewById(R.id.Boots);

        Classic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, products.class);
                i.putExtra("CatID", 1);
                i.putExtra("Catname", "Classic");
                startActivity(i);
            }
        });

                heel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(Home.this, products.class);
                        i.putExtra("CatID", 2);
                        i.putExtra("Catname", "Heels");
                        startActivity(i);


                    }
                });


        boot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Home.this,products.class);
                i.putExtra("CatID",3);
                i.putExtra("Catname", "Boots");
                startActivity(i);
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.se_sh,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.cart:
                Intent i=new Intent(this,ShoppingCart.class);

                startActivity(i);
                return true;

            case R.id.MSearch:
                Intent intent=new Intent(this,Search.class);
                startActivity(intent);
                return true;

            case R.id.log:
                Intent in=new Intent(this,Login.class);
                startActivity(in);
                return true;

        }

        return false;
    }



}