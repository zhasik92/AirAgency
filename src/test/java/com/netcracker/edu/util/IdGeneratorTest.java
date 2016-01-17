package com.netcracker.edu.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Zhassulan on 20.10.2015.
 */
public class IdGeneratorTest {

    @Test
    //concurrent testing will be added later
    public void testGenerateId() throws Exception {
        for (int i = 0; i < 1000; i++) {
            Assert.assertEquals(i+1, IdGenerator.getInstance().getId().intValue());
        }


    }
}