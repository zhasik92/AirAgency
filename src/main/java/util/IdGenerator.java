package util;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Zhassulan on 20.10.2015.
 */
public class IdGenerator implements Serializable{
    private static IdGenerator instance;
    private static volatile BigInteger idCounter;
    private IdGenerator() {
    }
    public static synchronized IdGenerator getInstance(){
        if(instance==null){
            return new IdGenerator();
        }
        return instance;
    }

    public static synchronized BigInteger generateId(){
        idCounter=idCounter.add(BigInteger.ONE);
        return idCounter;
    }
}
