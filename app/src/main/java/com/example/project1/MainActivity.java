package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView sPage;
    EditText edEmail, edPassword;
    Button buttonLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sPage = findViewById(R.id.sPage);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        progressBar=findViewById(R.id.progressBar);

        sPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, SignUp.class);
                startActivity(myIntent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edEmail.getText().toString().trim();
                final String password = edPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Email and password are required", Toast.LENGTH_SHORT).show();
                } else {
                    // URL of your login.php script on the server
                    String loginUrl = "https://3333manaman.000webhostapp.com/apps/login.php"; // Replace with your actual server URL

                    progressBar.setVisibility(View.VISIBLE);
                    // Create a RequestQueue
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                    // Define a StringRequest
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);
                                    if (response.equals("Login successful")) {
                                        // Login successful, navigate to the next activity
                                        Intent intent = new Intent(MainActivity.this, Demo.class);
                                        startActivity(intent);
                                    } else {
                                        // Login failed, show an error message
                                        Toast.makeText(MainActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Handle error
                                    Toast.makeText(MainActivity.this, "Error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            // Parameters to send to the server
                            Map<String, String> params = new HashMap<>();
                            params.put("e", email);
                            params.put("p", password);
                            return params;
                        }
                    };

                    // Add the request to the RequestQueue
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}
