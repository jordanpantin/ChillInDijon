package pantin.diiage.org.chillindijon;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import pantin.diiage.org.chillindijon.Models.JSONParser;
import pantin.diiage.org.chillindijon.Models.Location;
import pantin.diiage.org.chillindijon.Models.Place;
import pantin.diiage.org.chillindijon.Models.Position;

public class MainActivity extends AppCompatActivity {

    private static class PoiViewHolder extends RecyclerView.ViewHolder {

        public PoiViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String baseUrlApi = getResources().getString(R.string.base_url_api);

        //Creation de l'urrl de la string recup dans les resources (string.xml)
        URL baseUrl = null;
        try {
            baseUrl = new URL(baseUrlApi);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        AsyncTask<URL,Integer,ArrayList<Place>> task = new AsyncTask<URL, Integer, ArrayList<Place>>() {
            ArrayList<Place> places = null;

            @Override
            protected ArrayList<Place> doInBackground(URL... urls) {

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

                    places = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONParser jsonParser = new JSONParser();
                        places.add(jsonParser.JsonToSignboard(jsonObject));
                    }

                    for(Place place : places){

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
            protected void onPostExecute(ArrayList<Place> signboards){
                super.onPostExecute(places);
            }
        }.execute(baseUrl);
    }
}
