package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.LineValues;
import com.mobiquityinc.model.Thing;
import com.mobiquityinc.model.TotalObjectsInPackage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PackerTest {

    /***
     * Simple Test for checking all process and final result with two text file as input
     * @throws APIException
     */
    @Test
    public void pack() throws APIException {
        String result = Packer.pack("C:\\Project\\java\\PackageChallenge\\src\\test\\resources\\test.txt");
//        String result = Packer.pack(getClass().getClassLoader().getResource("test.txt").getFile());
        assertEquals("-\n3\n-\n",result);
        result = Packer.pack("C:\\Project\\java\\PackageChallenge\\src\\test\\resources\\pack.txt");
//        result = Packer.pack(getClass().getClassLoader().getResource("pack.txt").getFile());
        assertEquals("4\n" +
                "-\n" +
                "2,7\n" +
                "6,8\n",result);

    }

    /**
     * This function some type exposing THINGS objects with some different values
     * We can can mocking
     */
    @Test
    public void createThingObject() throws APIException {
        PackerResolver packerResolver = new PackerResolver();
        LineValues lineValues = packerResolver.getLineValuesAsobject("8 : (1,15.3,€34)");
        List<Thing> things = packerResolver.createThingObject(lineValues);
        assertEquals(0 , things.size());
        lineValues = packerResolver.getLineValuesAsobject("14 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)");
        things = packerResolver.createThingObject(lineValues);
        assertEquals(1 , things.size());
        assertEquals(3 , things.get(0).getId());
        lineValues = packerResolver.getLineValuesAsobject("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
        things = packerResolver.createThingObject(lineValues);
        assertEquals(5 , things.size());
        lineValues = packerResolver.getLineValuesAsobject("75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)");
        things = packerResolver.createThingObject(lineValues);
        assertEquals(5 , things.size());
        lineValues = packerResolver.getLineValuesAsobject("56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)");
        things = packerResolver.createThingObject(lineValues);
        assertEquals(7 , things.size());
    }

    @Test
    public void prepareOutPut() {
        PackerResolver packerResolver = new PackerResolver();
        List<Integer> finalValues = Arrays.asList(1);
        String result = packerResolver.prepareOutPut(finalValues);
        assertEquals("1",result);
        finalValues = Arrays.asList(1,5,6,7);
        result = packerResolver.prepareOutPut(finalValues);
        assertEquals("1,5,6,7",result);
    }

    @Test
    public void getPackingResult() throws APIException {
        PackerResolver packerResolver = new PackerResolver();
        LineValues lineValues = packerResolver.getLineValuesAsobject("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
        List<Thing> things = packerResolver.createThingObject(lineValues);
        List<Thing> finalValues = packerResolver.getPackingResult(things);
        assertEquals(1,finalValues.size());
        assertEquals(4 ,finalValues.get(0).getId());
    }

    @Test
    public void setFinalValues() throws APIException {
        PackerResolver packerResolver = new PackerResolver();
        LineValues lineValues = packerResolver.getLineValuesAsobject("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
        List<Thing> things = packerResolver.createThingObject(lineValues);
        List<Thing> finalValues = packerResolver.getPackingResult(things);
        TotalObjectsInPackage totalObject = packerResolver.setFinalValues(things, lineValues.getWeightOfPackage());
        assertEquals(totalObject.getThings().size(), things.size());
    }

    @Test
    public void prepareFinalValues() throws APIException {
        PackerResolver packerResolver = new PackerResolver();
        LineValues lineValues = packerResolver.getLineValuesAsobject("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
        List<Thing> things = packerResolver.createThingObject(lineValues);
        List<Thing> finalValues = packerResolver.getPackingResult(things);
        List<TotalObjectsInPackage> finalResult = new ArrayList<>();
        finalResult.add(packerResolver.setFinalValues(finalValues, lineValues.getWeightOfPackage()));
        String result = packerResolver.prepareFinalValues(finalResult);
        assertEquals("4\n", result);
    }
}
