package pantin.diiage.org.chillindijon.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jordan on 3/16/2018.
 */

public class JSONParser
{
    public POI JsonToSignboard(JSONObject json) throws JSONException
    {
        POI POI = new POI(json.getString("type"), JsonToLocation(json.getJSONObject("location")));
        return POI;
    }

    public Location JsonToLocation(JSONObject json) throws  JSONException
    {
        Location location = new Location();
        location.setAddress(json.getString("adress"));
        location.setPosition(JsonToPosition(json.getJSONObject("position")));
        return location;
    }

    public Position JsonToPosition(JSONObject json) throws JSONException {
        Position position = new Position();
        position.setLat(json.getJSONObject("position").getDouble("lat"));
        position.setLon(json.getJSONObject("position").getDouble("lon"));
        return position;
    }
}
