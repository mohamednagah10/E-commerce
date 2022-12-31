package com.example.project;

import static com.example.project.products.id_pro;

import static com.example.project.CustomListAdapter.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {
  public static   ArrayList<product> prod = new ArrayList<product>();
    public static  CustomListAdapter CLA;
    static public   int q;
    static  ListView cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        DataBase db = new DataBase(getApplicationContext());
          cart = (ListView) findViewById(R.id.lst);
        prod.clear();
         Button address = (Button)findViewById(R.id.add);
        for (int i = 0 ; i < id_pro.size() ; i++ ){
            product p = db.cart(id_pro.get(i));
            prod.add(p);
        }

        CLA= new CustomListAdapter(this,prod);
        cart.setAdapter(CLA);

        Button buy=(Button) findViewById(R.id.buy);

        buy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             //   db.Insert_Order( Map.address_Order );
                Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_LONG).show();
                Intent i=new Intent(ShoppingCart.this,ShowOrder.class);
                startActivity(i);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShoppingCart.this, Map.class);
                startActivity(i);
            }
        });


    }


}