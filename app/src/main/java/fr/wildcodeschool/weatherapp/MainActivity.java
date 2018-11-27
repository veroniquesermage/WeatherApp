package fr.wildcodeschool.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {

    private final static String API_Key="1350f58959c54d44be4a6de83d12e661";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crée une file d'attente pour les requêtes vers l'API
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final TextView text1 = (TextView) findViewById(R.id.text1);


        String url = "https://api.openweathermap.org/data/2.5/forecast?q=Toulouse&APPID=" + API_Key;

        // Création de la requête vers l'API, ajout des écouteurs pour les réponses et erreurs possibles
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        int day=0;
                        try {
                            JSONArray list = response.getJSONArray("list");

                            for (int index=0; index<list.length(); index+=8){
                                JSONObject listItem = (JSONObject) list.get(index);
                                JSONArray weatherArray = (JSONArray) listItem.getJSONArray("weather");
                                JSONObject weatherItem  = (JSONObject) weatherArray.get(0);
                                String description = weatherItem.getString("description");
                                day++;
                                text1.append("Day " + day  + " : " +description + "\n");
                                //Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Afficher l'erreur
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        // On ajoute la requête à la file d'attente
        requestQueue.add(jsonObjectRequest);
    }
}
