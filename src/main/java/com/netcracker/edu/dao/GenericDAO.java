package com.netcracker.edu.dao;

import com.netcracker.edu.bobjects.*;

import java.sql.SQLException;

/**
 * Created by Zhassulan on 29.12.2015.
 */
// TODO: 05.01.2016 complete this and use
public  class GenericDAO<T extends BusinessObject> {
    private DAObject dao;

    public GenericDAO(DAObject dao) {
        this.dao = dao;
    }

    public void save(T obj) throws SQLException,IllegalArgumentException {
        if (obj == null) {
            throw new IllegalArgumentException("obj can't be null");
        }
        if (obj.getClass().equals(Airplane.class)) {
            dao.addAirplane((Airplane) obj);
            return;
        }
        if (obj.getClass().equals(City.class)) {
            dao.addCity((City) obj);
            return;
        }
        if (obj.getClass().equals(Flight.class)) {
            dao.addFlight((Flight) obj);
            return;
        }
        if (obj.getClass().equals(Passenger.class)) {
            dao.addPassenger((Passenger) obj);
            return;
        }
        if (obj.getClass().equals(Ticket.class)) {
            dao.addTicket((Ticket) obj);
            return;
        }
        if (obj.getClass().equals(User.class)) {
            dao.addUser((User) obj);
            return;
        }
        throw new IllegalArgumentException("illegal class of the object");
    }

}
