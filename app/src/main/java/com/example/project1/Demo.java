package com.example.project1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Demo extends AppCompatActivity {

    private String phpUrl = "https://3333manaman.000webhostapp.com/apps/demo.php";
    private ListView exmTypeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        exmTypeListView = findViewById(R.id.exmTypeListView);

        // Call the AsyncTask to fetch and display "exm_type" data
        new FetchExmTypeTask().execute();
    }

    private class FetchExmTypeTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> exmTypes = new ArrayList<>();

            try {
                URL url = new URL(phpUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // Read data from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Parse the JSON response
                JSONArray jsonArray = new JSONArray(result.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    exmTypes.add(jsonArray.getString(i));
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return exmTypes;
        }

        @Override
        protected void onPostExecute(ArrayList<String> exmTypes) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    Demo.this,
                    android.R.layout.simple_list_item_1,
                    exmTypes
            );

            exmTypeListView.setAdapter(adapter);

            // Handle item clicks to navigate to the next activity (Demo1)
            exmTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedExmType = exmTypes.get(position);
                    Intent intent = new Intent(Demo.this, Demo1.class);

                    // Pass the selected "exm_type" to the next activity (Demo1)
                    intent.putExtra("selectedExmType", selectedExmType);

                    // Start the next activity
                    startActivity(intent);
                }
            });
        }
    }
}