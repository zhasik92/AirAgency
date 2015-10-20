package entity;

import java.math.BigInteger;
import java.sql.Time;

/**
 * Created by Zhassulan on 20.10.2015.
 */
public class Flight extends HasIdObject {
    private City from;
    private City to;
    private Time departureTime;
    private Time arrivalTime;
    private Airplane airplane;

    public Flight(BigInteger id, City from, City to, Time departureTime, Time arrivalTime, Airplane airplane) {
        super(id);
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airplane = airplane;
    }

    public City getFrom() {
        return from;
    }

    public void setFrom(City from) {
        this.from = from;
    }

    public City getTo() {
        return to;
    }

    public void setTo(City to) {
        this.to = to;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }
}
