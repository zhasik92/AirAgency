package com.netcracker.edu.dao;

import com.netcracker.edu.bobjects.*;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by Zhassulan on 29.12.2015.
 */
// TODO: 05.01.2016 complete this and use
public  interface GenericDAO<T extends BusinessObject> {
    DAObject dao=DAOFactory.getDAObject();

    void addBO(T bo) throws SQLException;

    void addAllTickets(Collection<Ticket> tickets) throws SQLException;

    Collection<T> getAllBOs(Class<T> t) throws SQLException;

    // TODO: 05.01.2016 remove it?
    Collection<Ticket> getAllCanceledTicketsInFlight(BigInteger flightId, Calendar flightDate) throws SQLException;

    int getNumberOfSoldTicketsInFlight(BigInteger flightId, Calendar flightDate) throws SQLException;

    Airplane findAirplaneByName(String airplane) throws SQLException;

    City findCityByName(String city) throws SQLException;

    Flight findFlightById(BigInteger id) throws SQLException;

    Passenger findPassengerById(BigInteger id) throws SQLException;

    Passenger findPassengerByPassportNumberAndCitizenship(String passportNumber, String citizenship) throws SQLException;

    Ticket findTicketById(BigInteger id) throws SQLException;

    User findUserByLogin(String login) throws SQLException;

    void updateBO(T bo) throws SQLException;
}
