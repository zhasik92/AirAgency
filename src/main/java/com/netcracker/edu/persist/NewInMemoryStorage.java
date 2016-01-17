package com.netcracker.edu.persist;

import com.netcracker.edu.bobjects.*;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Created by Zhassulan on 11.12.2015.
 */
public class NewInMemoryStorage {
    private static final long serialVersionUID= 1524210734632465536L;
    private HashMap<BigInteger,Passenger> passengers;
    private HashMap<String,City> cities;
    private HashMap<String,Airplane> airplanes;
    private HashMap<BigInteger,Ticket> tickets;
    private HashMap<BigInteger,Flight> flights;
    private HashMap<String,User> users;

}
