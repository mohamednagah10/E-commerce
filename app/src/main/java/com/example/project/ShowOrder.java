package com.example.project;

import static com.example.project.CustomListAdapter.*;
import static com.example.project.ShoppingCart.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);

        TextView TotalPrice=(TextView)findViewById(R.id.TotalPrice);
        Button confirm = (Button)findViewById(R.id.confirm);
        int TPrice = 0;
        int TProduct = 0;

        ListView reset = (ListView) findViewById(R.id.reset);
        DataBase db = new DataBase(getApplicationContext());
        ArrayAdapter<String> orderDetails = new ArrayAdapter<String >(getApplicationContext(), android.R.layout.simple_list_item_1);

        Cursor c = db.show_order();

        while (!c.isAfterLast()) {
            orderDetails.add("Name of Product : "+ c.getString(0));
            orderDetails.add("Price : "+ c.getString(1) +"  "+ "  quantity : "+ c.getString(2));
            TPrice +=c.getInt(1)*c.getInt(2);
            TProduct = c.getInt(1)*c.getInt(2);
            orderDetails.add("Total Price Of Product is  : "+ TProduct );
            orderDetails.add("====================================== ");
            //  orderDetails.add("quantity : "+ c.getString(2));
       /*   int TotalPrice = Integer.parseInt(c.getString(1)) * Integer.parseInt(c.getString(2));
            orderDetails.add(String.valueOf(TotalPrice));
*/
            c.moveToNext();

        }
        TotalPrice.setText("Total Price is " + String.valueOf(TPrice));
        reset.setAdapter(orderDetails);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.ConfirmOrder();
                Toast.makeText(getApplicationContext(),"Done Confirmed order"
                        ,Toast.LENGTH_LONG).show();

                orderDetails.clear();
                Intent i = new Intent(ShowOrder.this,Home.class);
                startActivity(i);
            }
        });

    }
}
