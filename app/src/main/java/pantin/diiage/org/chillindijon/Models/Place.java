package pantin.diiage.org.chillindijon.Models;

/**
 * Created by Jordan on 3/16/2018.
 */

public class Place
{
    private Location location;
    private String type;

    public Place(String type, Location location)
    {
        this.location = location;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
