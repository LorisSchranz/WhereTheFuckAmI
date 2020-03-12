package ch.ls.wherethefuckami.Models;

public class CurrentLocation {
    public Double Long;
    public Double Lat;

    public CurrentLocation(double Long, double Lat){
        this.Long = Long;
        this.Lat = Lat;
    }

    public Double getLong() {
        return Long;
    }

    public void setLong(Double aLong) {
        Long = aLong;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }
}
