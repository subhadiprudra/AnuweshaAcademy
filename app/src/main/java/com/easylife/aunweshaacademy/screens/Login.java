package com.easylife.aunweshaacademy.screens;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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

public class Login extends AppCompatActivity {

    EditText ed_email,ed_password;

    String str_email,str_password;
    String url = "https://aunweshaa.000webhostapp.com/login.php";
    BasicFunction basicFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        basicFunction= new BasicFunction(this);
    }

    ProgressDialog progressDialog;

    public void Login(View view) {

        if(ed_email.getText().toString().equals("")){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        }
        else if(ed_password.getText().toString().equals("")){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else{

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();

            str_email = ed_email.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();

            str_password= basicFunction.encode(str_password);
            Log.i("password",str_password);

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    if(!response.equalsIgnoreCase("user not found")){

                        basicFunction.write("email",str_email);
                        basicFunction.write("password",str_password);
                        basicFunction.write("name",response);

                        getCoursesAndGotoMenu();
                    }else {
                        Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                    }

                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("email",str_email);
                    params.put("password",str_password);
                    Log.i("password",str_password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            requestQueue.add(request);

        }
    }

    public void moveToRegistration(View view) {
        startActivity(new Intent(getApplicationContext(),Register.class));
        finish();
    }




    public void getCoursesAndGotoMenu() {
        String url = "https://aunweshaa.000webhostapp.com/courses.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Intent intent = new Intent(Login.this,Menu.class);
                basicFunction.write("data",response);
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

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(request);


    }




}