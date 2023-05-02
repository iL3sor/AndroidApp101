package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button register, login;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        register = (Button) findViewById(R.id.btnRegister);
        login = (Button) findViewById(R.id.btnLogin);

        db = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(user.equals("") || pass.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter all the username and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean check = db.checkUsername(user);
                    if(check){
                        Toast.makeText(MainActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                    }else{
                        Boolean check2 = db.insertData(user, pass);
                        if(check2){
                            Toast.makeText(MainActivity.this, "Success! Now you can log in", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Fail! Register again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(user.equals("") || pass.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter all the username and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean check = db.checkLogin(user, pass);
                    if(check){
                        Toast.makeText(MainActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", user);
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}