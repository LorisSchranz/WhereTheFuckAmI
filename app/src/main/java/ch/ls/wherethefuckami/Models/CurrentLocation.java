package ch.ls.wherethefuckami.Models;

public class CurrentLocation {
    private Double Long;
    private Double Lat;

    public CurrentLocation(double Long, double Lat){
        this.Long = Long;
        this.Lat = Lat;
    }

    public Double getLong() {
        return Long;
    }

    public Double getLat() {
        return Lat;
    }

}
