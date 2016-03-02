package com.netcracker.edu.bobjects;

import org.apache.commons.lang.time.FastDateFormat;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Zhassulan on 20.10.2015.
 */
public class Ticket extends HasIdObject implements Serializable {
    private static final long serialVersionUID=-9112295186131870382L;
    private BigInteger passenger;
    private BigInteger flight;
    private boolean status;
    private Calendar flightDate;
    private Calendar ticketBoughtDate;

    public Ticket(BigInteger id, BigInteger passenger, BigInteger flight,Calendar flightDate, Calendar ticketBoughtDate) {
        super(id);
        setPassengerId(passenger);
        setFlightId(flight);
        this.status = false;
        this.flightDate=flightDate;
        this.ticketBoughtDate=ticketBoughtDate;
    }

    public Ticket(BigInteger id, BigInteger passenger, BigInteger flight,boolean status,Calendar flightDate, Calendar ticketBoughtDate) {
        super(id);
        setPassengerId(passenger);
        setFlightId(flight);
        this.status = status;
        this.flightDate=flightDate;
        this.ticketBoughtDate=ticketBoughtDate;
    }


    public BigInteger getPassengerId() {
        return passenger;
    }

    public void setPassengerId(BigInteger passenger) {
        if (passenger == null||passenger.compareTo(BigInteger.ZERO)<0) {
            throw new IllegalArgumentException("passenger's id can't be null or negative");
        }
        this.passenger = passenger;
    }

    public BigInteger getFlightId() {
        return flight;
    }

    public void setFlightId(BigInteger flight) {
        if (flight == null||passenger.compareTo(BigInteger.ZERO)<0) {
            throw new IllegalArgumentException("flight id can't be null or negative");
        }
        this.flight = flight;
    }

    public boolean isCanceled() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Calendar getTicketBoughtDate() {
        return ticketBoughtDate;
    }

    public Calendar getFlightDate() {
        return flightDate;
    }

    @Override
    public String toString() {
        StringBuilder result=new StringBuilder();
        String newline=System.getProperty("line.separator");
        DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
        result.append(this.getClass().getSimpleName()).append(newline);
        result.append("id: ").append(getId()).append(newline);
        result.append("passenger: ").append(getPassengerId()).append(newline);
        result.append("flight: ").append(getFlightId()).append(newline);
        result.append("status: ").append(isCanceled()).append(newline);
        result.append("flightDate: ").append(df.format(getFlightDate().getTime())).append(newline);
        result.append("ticketBoughtDate: ").append(df.format(getTicketBoughtDate().getTime())).append(newline);
        return result.toString();
    }
}
