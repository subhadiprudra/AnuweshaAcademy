package com.easylife.aunweshaacademy.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easylife.aunweshaacademy.BasicFunction;
import com.easylife.aunweshaacademy.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button register,login ;

    String str_email,str_password;
    String url = "https://aunweshaa.000webhostapp.com/login.php";
    BasicFunction basicFunction;

    LinearLayout progress,buttons;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        basicFunction = new BasicFunction(this);
        register= findViewById(R.id.btn_register_ma);
        login= findViewById(R.id.btn_login_ma);
        progress = findViewById(R.id.progress_circular);
        buttons = findViewById(R.id.buttons);


        str_email = basicFunction.read("email");
        str_password = basicFunction.read("password");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });


        if(!str_email.equals("null") && !str_password.equals("null")){
            login();
        }else {
            buttons.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        }

    }



    public void login() {

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    if(!response.equalsIgnoreCase("user not found")){
                        getCoursesAndGotoMenu();
                    }else {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        buttons.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                    }

                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    buttons.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("email",str_email);
                    params.put("password",str_password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);


    }



    public void getCoursesAndGotoMenu() {
        String url = "https://aunweshaa.000webhostapp.com/courses.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                basicFunction.write("data",response);
                Intent intent = new Intent(MainActivity.this,Menu.class);
                startActivity(intent);
                Log.i("data",response);

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",str_email);
                params.put("password",str_password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);


    }

}



