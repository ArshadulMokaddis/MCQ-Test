package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Bangla2010 extends AppCompatActivity {
    private ScrollView scrollViewQuestions;
    private LinearLayout linearLayoutQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangla2010);

        scrollViewQuestions = findViewById(R.id.scrollViewQuestions);
        linearLayoutQuestions = findViewById(R.id.linearLayoutQuestions);

        // Specify the exam type, subject, and year for filtering
        String exmType = "JSC";
        String exmSubject = "Bangla";
        String year = "2010";

        // URL of your modified PHP script with query parameters
        String serverUrl = "https://3333manaman.000webhostapp.com/apps/mcq.php" +
                "?exm_type=" + exmType +
                "&exm_subject=" + exmSubject +
                "&year=" + year;

        // Create a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Define a JsonArrayRequest for the HTTP GET request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, serverUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Loop through the JSON array and create views for questions and options
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject questionObject = response.getJSONObject(i);
                                String questionText = questionObject.getString("question_text");

                                TextView textViewQuestion = new TextView(Bangla2010.this);
                                textViewQuestion.setText("Question " + (i + 1) + ": " + questionText);
                                textViewQuestion.setTextSize(18);
                                textViewQuestion.setLayoutParams(new LayoutParams(
                                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                                linearLayoutQuestions.addView(textViewQuestion);

                                // Create and add RadioGroup for options
                                RadioGroup radioGroupOptions = new RadioGroup(Bangla2010.this);
                                radioGroupOptions.setLayoutParams(new LayoutParams(
                                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

                                String[] optionKeys = {"option_a", "option_b", "option_c", "option_d"};
                                for (String optionKey : optionKeys) {
                                    String optionText = questionObject.getString(optionKey);
                                    if (!optionText.isEmpty()) {
                                        RadioButton radioButton = new RadioButton(Bangla2010.this);
                                        radioButton.setText(optionText);
                                        radioGroupOptions.addView(radioButton);
                                    }
                                }
                                linearLayoutQuestions.addView(radioGroupOptions);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        error.printStackTrace();
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }
}
