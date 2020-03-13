package ch.ls.wherethefuckami.Models;

public class Information {
    private Double Long;
    private Double Lat;
    private Double Speed;

    public Information(double Long, double Lat, double Speed){
        this.Long = Long;
        this.Lat = Lat;
        this.Speed = Speed * 3.6;
    }

    public Double getLong() {
        return Long;
    }

    public Double getLat() {
        return Lat;
    }

    public Double getSpeed() {
        return Speed;
    }

    public void setSpeed(Double speed) {
        Speed = speed;
    }
}
