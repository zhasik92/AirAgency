package com.netcracker.edu.util;

import com.netcracker.edu.bobjects.BusinessObject;

import java.math.BigInteger;

/**
 * Created by Zhassulan on 15.01.2016.
 */
public class GenericIdWrapper<T extends BusinessObject> {
    private final BigInteger id;

    public GenericIdWrapper() {
        id=IdGenerator.getInstance().getId();
    }
    public BigInteger getId(){
        return id;
    }
}
