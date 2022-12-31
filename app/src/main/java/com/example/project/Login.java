package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    static int id;
    CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);
        EditText uname =(EditText)findViewById(R.id.uname);
        EditText password =  (EditText)findViewById(R.id.password);
        TextView fpassword = (TextView)findViewById(R.id.Fpassword);
        Button login=(Button) findViewById(R.id.login);
        Button sign=(Button) findViewById(R.id.signup);
        DataBase db =new DataBase(this);

        CheckBox saveLoginCheckBox = (CheckBox) findViewById(R.id.remeber);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            uname.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String x = uname.getText().toString();
                String y= password.getText().toString();
            Boolean check =db.Check_login(x,y);

            if(check){

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(uname.getWindowToken(), 0);

                    if (saveLoginCheckBox.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", x);
                        loginPrefsEditor.putString("password", y);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                }

          //      id = db.get_CustID(x);
                    Intent i=new Intent(Login.this,Home.class);
                    startActivity(i);
                }

            else{
               uname.setText("");
               password.setText("");
               Toast.makeText(getApplicationContext(),"User Name or Password is not Right,try again",Toast.LENGTH_LONG).show();
                }
            }
        });



        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Login.this,Forget_Password.class);
                startActivity(i);
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this,SignUp.class);
                startActivity(i);
            }
        });

    }
}