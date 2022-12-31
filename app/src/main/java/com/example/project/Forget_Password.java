package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Forget_Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        EditText uname =(EditText) findViewById(R.id.user);
        EditText password =  (EditText) findViewById(R.id.txtpassword);
        TextView Cpassword = (TextView) findViewById(R.id.txtcpassword);
        Button upd_pass=(Button) findViewById(R.id.upd_pass);
        DataBase db =new DataBase(this);


        upd_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(password.getText().toString().equals(Cpassword.getText().toString())){
                if( db.Update_Password(uname.getText().toString(),password.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Update Password Successful",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(Forget_Password.this,Login.class);
                    startActivity(i);
                }
                else
                    Toast.makeText(getApplicationContext(),"User Name is not Right,try again",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(),"User Name or Password is not Right,try again",Toast.LENGTH_LONG).show();

            }
        });

    }
}