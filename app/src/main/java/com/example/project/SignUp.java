package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText Cname =(EditText) findViewById(R.id.cname);
        EditText uname =(EditText) findViewById(R.id.Uname);
        EditText job =  (EditText) findViewById(R.id.job);
        EditText email =(EditText) findViewById(R.id.email);
        EditText Password =(EditText) findViewById(R.id.Password);
        EditText Cpassword =(EditText) findViewById(R.id.Cpassword);
        RadioButton male =(RadioButton) findViewById(R.id.male);
        RadioButton female =(RadioButton) findViewById(R.id.female);
       // CalendarView calendar= (CalendarView)  findViewById(R.id.calendar);
        Button Reg = (Button) findViewById(R.id.Reg);
        DataBase db =new DataBase(this);

        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String gender;
                if(male.isChecked()){
                    gender = "Male";
                }
                else {
                    gender = "Female";
                }

                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss 'GMT'Z yyyy");
               String birthdate = dateFormat.format(c.getTime());
                DatePicker picker=(DatePicker)findViewById(R.id.datePicker1);

                String Birthday = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

                Toast.makeText(getApplicationContext(),Birthday,Toast.LENGTH_LONG).show();



                if(db.userbefore(uname.getText().toString())==false) {






                //jjjjjjj
                if(Password.getText().toString().equals(Cpassword.getText().toString())){

                    db.Register(Cname.getText().toString(),uname.getText().toString(),Password.getText().toString() ,
                            gender, job.getText().toString(), email.getText().toString() ,Birthday) ;

                Cname.setText("");
                uname.setText("");
                Password.setText("");
                Cpassword.setText("");
                job.setText("");
                email.setText("");
                    Time now = new Time();
                    picker.updateDate(now.year, now.month, now.monthDay);

                    Intent i=new Intent(SignUp.this,Login.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(),"You are Registered , Please Login",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Password Is Wrong",Toast.LENGTH_LONG).show();
                }
                }

    else
                Toast.makeText(getApplicationContext(),"This User name Used before",Toast.LENGTH_LONG).show();


            }

        });

    }

}