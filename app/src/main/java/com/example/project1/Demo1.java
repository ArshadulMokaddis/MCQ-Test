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

public class Demo1 extends AppCompatActivity {

    private String phpUrl = "https://3333manaman.000webhostapp.com/apps/demo1.php";
    private ListView exmSubjectListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);

        exmSubjectListView = findViewById(R.id.exmSubjectListView);

        // Get the selected "exm_type" from the previous activity
        Intent intent = getIntent();
        String selectedExmType = intent.getStringExtra("selectedExmType");

        // Call the AsyncTask to fetch and display "exm_subject" data for the selected "exm_type"
        new FetchExmSubjectTask().execute(selectedExmType);
    }

    private class FetchExmSubjectTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            String selectedExmType = params[0];
            ArrayList<String> exmSubjects = new ArrayList<>();

            try {
                URL url = new URL(phpUrl + "?exm_type=" + selectedExmType);
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
                    exmSubjects.add(jsonArray.getString(i));
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return exmSubjects;
        }

        @Override
        protected void onPostExecute(ArrayList<String> exmSubjects) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    Demo1.this,
                    android.R.layout.simple_list_item_1,
                    exmSubjects
            );

            exmSubjectListView.setAdapter(adapter);

            exmSubjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedExmSubject = exmSubjects.get(position);
                    Intent intent = new Intent(Demo1.this, Demo2.class);

                    intent.putExtra("selectedExmSubject", selectedExmSubject);
                    startActivity(intent);
                }
            });
        }
    }
}