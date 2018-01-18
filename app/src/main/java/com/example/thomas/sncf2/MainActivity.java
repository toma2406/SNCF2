package com.example.thomas.sncf2;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView ListViewObjet ;
    private ObjetRepo objetRepo = new ObjetRepo(this);
    private DBHELPER dbhelper = new DBHELPER(this);

    private static String url ="https://ressources.data.sncf.com/api/records/1.0/search/?dataset=objets-trouves-restitution&sort=date&facet=gc_obo_gare_origine_r_name&facet=gc_obo_type_c&facet=gc_obo_nom_recordtype_sc_c";
    private String date;
    private String gare;
    private String type ;
    private String nature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListViewObjet=(ListView)findViewById(R.id.listViewObjet);
        afficherList();
    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id== R.id.action_rotate) {
            RecupererJson recupererJson = new RecupererJson();
            recupererJson.execute();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    class RecupererJson extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null ,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            deleteDatabase(dbhelper.getDatabaseName());
                            Toast.makeText(getApplicationContext(),
                                    "Récupération réussie !",
                                    Toast.LENGTH_LONG).show();

                            //parsing du JSON
                            try{
                                JSONArray records = response.getJSONArray("records");
                                for (int i = 0; i < records.length(); i++){
                                    JSONObject objet = records.getJSONObject(i);

                                    JSONObject objets = objet.getJSONObject("fields");
                                    date = objets.getString("date");
                                    gare = objets.getString("gc_obo_gare_origine_r_name");
                                    nature = objets.getString("gc_obo_nature_c");
                                    type = objets.getString("gc_obo_type_c");


                                    //implementation de la base de donnees
                                    ObjetTrouve objetTrouve = new ObjetTrouve();
                                    objetTrouve.date = date;
                                    objetTrouve.gare = gare;
                                    objetTrouve.nature= nature;
                                    objetTrouve.type = type;
                                    objetRepo.insert(objetTrouve);
                                }
                                afficherList();

                            }catch (JSONException e){
                                Toast.makeText(MainActivity.this, "An error ocurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "La récupération a échouée", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(jsonObjectRequest);
            return null;
        }
    }


    public void afficherList(){
        ArrayList<HashMap<String,String>> objetList = objetRepo.getObjetList();
        ObjetAdaptater adaptater = new ObjetAdaptater(this,objetList);
        ListViewObjet.setAdapter(adaptater);
    }

}
