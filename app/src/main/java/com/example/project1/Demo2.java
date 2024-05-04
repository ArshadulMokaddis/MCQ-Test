package com.example.project1;

// Import necessary packages

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Demo2 extends AppCompatActivity {
    private String phpUrl = "https://3333manaman.000webhostapp.com/apps/demo2.php";
    private ListView yearListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);

        yearListView = findViewById(R.id.yearListView);

        // Get the selected "exm_type" and "exm_subject" from the previous activity
        Intent intent = getIntent();
        String selectedExmType = intent.getStringExtra("selectedExmType");
        String selectedExmSubject = intent.getStringExtra("selectedExmSubject");

        // Call the AsyncTask to fetch and display "year" data for the selected "exm_type" and "exm_subject"
        new FetchYearTask().execute(selectedExmType, selectedExmSubject);
    }

    private class FetchYearTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            String selectedExmType = params[0];
            String selectedExmSubject = params[1];
            ArrayList<String> years = new ArrayList<>();

            try {
                URL url = new URL(phpUrl + "?exm_type=" + selectedExmType + "&exm_subject=" + selectedExmSubject);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONArray jsonArray = new JSONArray(result.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    years.add(jsonArray.getString(i));
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return years;
        }

        @Override
        protected void onPostExecute(ArrayList<String> years) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    Demo2.this,
                    android.R.layout.simple_list_item_1,
                    years
            );

            yearListView.setAdapter(adapter);

            yearListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedYear = years.get(position);
                    // You can navigate to the next activity or perform other actions here.
                }
            });
        }
    }
}
