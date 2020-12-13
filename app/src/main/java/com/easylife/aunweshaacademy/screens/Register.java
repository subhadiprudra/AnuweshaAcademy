package com.easylife.aunweshaacademy.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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

public class Register extends AppCompatActivity {

    EditText ed_username,ed_email,ed_password,ed_phone,ed_inst;
    String str_name,str_email,str_password,str_phone,str_institute;
    String url = "https://aunweshaa.000webhostapp.com/register.php";
    BasicFunction basicFunction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        basicFunction= new BasicFunction(this);
        ed_email = findViewById(R.id.ed_email);
        ed_username = findViewById(R.id.ed_username);
        ed_password = findViewById(R.id.ed_password);
        ed_phone=findViewById(R.id.ed_phone);
        ed_inst = findViewById(R.id.ed_inst);

    }

    public void moveToLogin(View view) {
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    ProgressDialog progressDialog;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void register(View view) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        if(ed_username.getText().toString().equals("")){
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
        }
        else if(ed_email.getText().toString().equals("")){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        }
        else if(ed_password.getText().toString().equals("")){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }else if(ed_username.getText().toString().equals("user not found")){
            Toast.makeText(this, "This can't be a name", Toast.LENGTH_SHORT).show();
        }
        else if(ed_inst.getText().toString().equals("")){
            Toast.makeText(this, "Enter institute name", Toast.LENGTH_SHORT).show();
        }
        else if(ed_phone.getText().toString().equals("")){
            Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show();
        }
        else{

            progressDialog.show();
            str_name = ed_username.getText().toString().trim();
            str_email = ed_email.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();
            str_institute=ed_inst.getText().toString().trim();
            str_phone=ed_phone.getText().toString().trim();

            str_password= basicFunction.encode(str_password);
            Log.i("password",str_password);



            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    progressDialog.dismiss();

                    ed_username.setText("");
                    ed_email.setText("");
                    ed_password.setText("");
                    ed_phone.setText("");
                    ed_inst.setText("");

                    Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                    if(response.equals("registered successfully")){
                        basicFunction.write("email",str_email);
                        basicFunction.write("password",str_password);
                        basicFunction.write("name",str_name);
                        getCoursesAndGotoMenu();
                    }
                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Log.e("Register",error.getMessage());
                    Toast.makeText(Register.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();

                    params.put("name",str_name);
                    params.put("email",str_email);
                    params.put("password",str_password);
                    params.put("phone",str_phone);
                    params.put("institute",str_institute);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
            requestQueue.add(request);


        }

    }


    public void getCoursesAndGotoMenu() {
        String url = "https://aunweshaa.000webhostapp.com/courses.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Intent intent = new Intent(Register.this,Menu.class);
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

        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(request);


    }

}