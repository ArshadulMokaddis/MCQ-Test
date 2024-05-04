package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SignUp extends AppCompatActivity {
    TextView lPage;
    ProgressBar progressBar;
    EditText edName,edEmail,edPassword;
    Button buttonInsert;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        edName=findViewById(R.id.edName);
        edEmail=findViewById(R.id.edEmail);
        edPassword=findViewById(R.id.edPassword);
        progressBar=findViewById(R.id.progressBar);
        buttonInsert=findViewById(R.id.buttonInsert);


        lPage=findViewById(R.id.lPage);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=edName.getText().toString();
                String email=edEmail.getText().toString();
                String password=edPassword.getText().toString();
                String url="https://3333manaman.000webhostapp.com/apps/data.php?n=" +name
                        +"&e=" +email +"&p="+password;

                progressBar.setVisibility(View.VISIBLE);
                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        new AlertDialog.Builder(SignUp.this)
                                .setTitle("Server Response")
                                .setMessage(response)
                                .show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue= Volley.newRequestQueue(SignUp.this);
                requestQueue.add(stringRequest);
            }
        });

        lPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent=new Intent(SignUp.this,MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
}