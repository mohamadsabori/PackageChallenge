package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Thing;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PackerTest {

    @Test
    public void pack() throws APIException {
        String result = Packer.pack("C:\\Project\\java\\PackageChallenge\\example\\test.txt");
        assertEquals("-\n3\n",result);
        result = Packer.pack("C:\\Project\\java\\PackageChallenge\\example\\pack.txt");
        assertEquals("4\n" +
                "-\n" +
                "2,7\n" +
                "6,8\n",result);

    }


    @Test
    public void createThingObject() {
        float weightOfPackage = 8.0f;
        List<Thing> things = Packer.createThingObject("(1,15.3,€34)", weightOfPackage);
        assertEquals(0 , things.size());
        weightOfPackage = 14.0f;
        things = Packer.createThingObject("(1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)", weightOfPackage);
        assertEquals(1 , things.size());
        assertEquals(3 , things.get(0).getId());
        assertEquals(3.98 , things.get(0).getSize());
        assertEquals(16.0 , things.get(0).getPrice());
        weightOfPackage = 81.0f;
        things = Packer.createThingObject("(1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)", weightOfPackage);
        assertEquals(5 , things.size());
        weightOfPackage = 75.0f;
        things = Packer.createThingObject("(1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)", weightOfPackage);
        assertEquals(5 , things.size());
        weightOfPackage = 56.0f;
        things = Packer.createThingObject("(1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)", weightOfPackage);
        assertEquals(7 , things.size());
    }

    @Test
    public void prepareOutPut() {
        List<Integer> finalValues = Arrays.asList(1);
        String result = Packer.prepareOutPut(finalValues);

    }

    @Test
    public void getPackingResult() {
    }
}
