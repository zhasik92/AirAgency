package entity;

import java.math.BigInteger;

/**
 * Created by Zhassulan on 20.10.2015.
 */
public class Ticket extends HasIdObject {
    private double price;
    private Passenger passenger;
    private Flight flight;
    private boolean status;

    public Ticket(BigInteger id, Passenger passenger, double price, Flight flight, boolean status) {
        super(id);
        this.passenger = passenger;
        this.price = price;
        this.flight = flight;
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
