package com.mobiquityinc.packer;

import com.mobiquityinc.model.Thing;
import com.mobiquityinc.exception.APIException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * The main Packer class which handles packaging THINGS based on their weights and
 * maximum possible to pack.
 * It first evaluates which of the things can be packed in the space. Based on this
 * result, it finds the most expensive ones.
 */
public class Packer {

//    81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)

    public static void main(String[] args) throws APIException {
        System.out.println(pack("C:\\Project\\java\\PackageChallenge\\example\\pack.txt"));
    }

    private Packer() {
    }

    /**
     * The entrance method for packing functionality.
     * @param filePath The path of the input file to read
     * @return The solution as a String
     * @throws APIException if the input file cannot be found
     */
    public static String pack(String filePath) throws APIException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            StringBuilder allLinesResult = new StringBuilder();
            while ((line = br.readLine()) != null) {
                String[] inputLines = line.split(" : ");
                float weightOfPackage = Float.parseFloat(inputLines[0]);
                List<Thing> things = createThingObject(inputLines[1], weightOfPackage);
                List<Integer> finalValues = getPackingResult(things);
                allLinesResult.append(prepareOutPut(finalValues)).append("\n");
            }
            return allLinesResult.toString();
        } catch (IOException e) {
            throw new APIException(e.getMessage());
        }
    }

    /**
     * Extracts the values from the input file and instantiates the objects
     * @param inputLine The line containing the thing values
     * @param weightOfPackage The total weight of the package
     * @return A list of THING constructed objects
     */
    public static List<Thing> createThingObject(String inputLine, float weightOfPackage) {
        List<Thing> things = new ArrayList<>();
        for (String s : inputLine.split(" ")) {
            String[] packageValues = s.split(",");
            if (Float.parseFloat(packageValues[1]) < weightOfPackage) {
                float costOfPackage = Float.parseFloat(packageValues[2].substring(0, packageValues[2].indexOf(')')).substring(1));
                int idOfPackage = Integer.parseInt(packageValues[0].substring(1));
                things.add(new Thing(idOfPackage,Float.parseFloat(packageValues[1]),costOfPackage));
            }
        }
        return things;
    }

    /**
     * Prepares the output solution part using delimiters
     * @param finalValues The final output result of solution
     * @return The constructed solution part as a String
     */
    public static String prepareOutPut(List<Integer> finalValues) {
        if(finalValues.isEmpty()){
            return "-";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < finalValues.size(); i++) {
            builder.append(finalValues.get(i)).append(i == finalValues.size() - 1 ? "" : ",");
        }
        return builder.toString();
    }

    /**
     * Performs the calculations and procedures to get the packing result
     * @param things The list of things to be packed
     * @return A List containing the solutions
     */
    public static List<Integer> getPackingResult(List<Thing> things) {
        SortedMap<Float, List<Integer>> finalValues = new TreeMap<>();
        for (Thing thing : things) {
            if(!finalValues.containsKey(thing.getPrice())){
                finalValues.put(thing.getPrice(), new ArrayList<>());
            }
            finalValues.get(thing.getPrice()).add(thing.getId());
        }
        if(finalValues.isEmpty()){
            return Collections.emptyList();
        }
        return finalValues.get(finalValues.lastKey()) ;
    }
}
