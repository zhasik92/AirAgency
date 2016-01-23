package com.netcracker.edu.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

/**
 * Created by Zhassulan on 20.10.2015.
 */
public class IdGeneratorTest {
    @Before
    public void setUp(){
        Locale.setDefault(Locale.US);
    }
    @Test
    //concurrent testing will be added later
    public void testGenerateId() throws Exception {
        int id=IdGenerator.getInstance().getId().intValue();
        for (int i = 0; i < 1000; i++) {

            Assert.assertEquals(++id, IdGenerator.getInstance().getId().intValue());
        }


    }
}