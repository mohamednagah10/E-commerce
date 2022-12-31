package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    DataBase db ;
    Button search ;
    EditText name ;
    ImageButton voicebtn ;
    ListView lst ;
    ArrayAdapter<String>ad ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = new DataBase(this);
         search = (Button) findViewById(R.id.btn_search);
         name = (EditText) findViewById(R.id.txt_name);
        voicebtn = (ImageButton) findViewById(R.id.voice);
        lst = (ListView) findViewById(R.id.lst);
        ad = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        lst.setAdapter(ad);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.clear();
                if(!name.getText().toString().isEmpty())
                {
                    Cursor c = db.get_product(name.getText().toString());
                    while (!c.isAfterLast()){
                        ad.add(c.getString(0));
                        c.moveToNext();
                    }
                }

            }
        });

        voicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent,Voicecode);
            }
        });


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x= ad.getItem(position);
            Cursor c= db.Search_product(x);


                Intent i = new Intent(Search.this,QRCode.class);
                i.putExtra("name",c.getString(1) );
                i.putExtra("price", c.getString(2));
                startActivity(i);

            }
        });
    }

    int Voicecode = 1;
   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Voicecode && resultCode == RESULT_OK)
        {

            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            name.setText(text.get(0));
            lst.setAdapter(ad);
            Cursor c = db.get_product(text.get(0));
            if(c !=null){
                while (!c.isAfterLast()){
                    ad.add(c.getString(0));
                    c.moveToNext();
                }
            }
            else
                Toast.makeText(getApplicationContext(), "Not Found This Product", Toast.LENGTH_LONG).show();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


}