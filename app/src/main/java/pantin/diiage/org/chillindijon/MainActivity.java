package pantin.diiage.org.chillindijon;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import pantin.diiage.org.chillindijon.Models.ChillInDijonDbHelper;
import pantin.diiage.org.chillindijon.Models.JSONParser;
import pantin.diiage.org.chillindijon.Models.POI;

public class MainActivity extends AppCompatActivity {

   /*private static class PoiViewHolder extends RecyclerView.ViewHolder {

        public PoiViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de la pas de données
        ChillInDijonDbHelper helper = new ChillInDijonDbHelper(this);

        // Obtient un connecteur à la base de données
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("idPoi","jnrgnrkngj");
        contentValues.put("star",4);
        long idFavorisNew = db.insert(ChillInDijonDbHelper.TABLE_FAVORIS, null, contentValues);

        Cursor cursor = db.query(ChillInDijonDbHelper.TABLE_FAVORIS,
                new String[]{"idFavoris","idPoi","stars","dateAjout"},
                "stars >= ?",
                new String[]{"4"},
                null,
                null,
                "stars DESC");

        // Parcours du curseur
        // Tant que le curseur peut avancer
        while(cursor.moveToNext())
        {
            // Obtient les données des colonnes via leurs indexes
            long idFavoris = cursor.getLong(0);
            long idPOI = cursor.getLong(1);
            int stars = cursor.getInt(2);
            long dateAjout = cursor.getLong(3);

            // TODO : créer une instance de favoris avec les données et l'ajouter dans un ArrayList
        }

        // Modification d'un ou plusieur tuple(s) avec update
        // Création d'un objet ContentValues avec les valeurs que l'on souhaite appliquer
        ContentValues updatecontentValues = new ContentValues();
        updatecontentValues.put("stars",5);

        // Appel de la méthode update() avec des paramètres similaires de query()
        db.update(
                // Nom de la table
                ChillInDijonDbHelper.TABLE_FAVORIS,
                updatecontentValues,
                "idFavoris = ?",
                // Les valeurs à injecter dans la clause where
                new String[]{String.valueOf(idFavorisNew)});

        db.delete(
                // Nom de la table
                ChillInDijonDbHelper.TABLE_FAVORIS,
                "idFavoris = ?",
                // Les valeurs à injecter dans la clause where
                new String[]{String.valueOf(idFavorisNew)});


        String baseUrlApi = getResources().getString(R.string.base_url_api);

        //Creation de l'urrl de la string recup dans les resources (string.xml)
        URL baseUrl = null;
        try {
            baseUrl = new URL(baseUrlApi);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        AsyncTask<URL,Integer,ArrayList<POI>> task = new AsyncTask<URL, Integer, ArrayList<POI>>() {
            ArrayList<POI> pois = null;

            @Override
            protected ArrayList<POI> doInBackground(URL... urls) {

                try {
                    InputStream inputStream = urls[0].openStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    //Initialisation d'un StringBuilder pour stocker le contenu distant
                    StringBuilder stringBuilder = new StringBuilder();
                    String lineBuffer = null;
                    while ((lineBuffer = bufferedReader.readLine()) != null){
                        stringBuilder.append(lineBuffer);
                    }
                    String data = stringBuilder.toString();
                    JSONArray jsonArray = new JSONArray(data);

                    pois = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONParser jsonParser = new JSONParser();
                        pois.add(jsonParser.JsonToSignboard(jsonObject));
                    }

                    for(POI poi : pois){

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e("EXCEPTION", e.getLocalizedMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("EXCEPTION", e.getLocalizedMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("EXCEPTION", e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<POI> signboards){
                super.onPostExecute(pois);
            }
        }.execute(baseUrl);
    }
}
