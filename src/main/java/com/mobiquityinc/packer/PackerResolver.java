package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.LineValues;
import com.mobiquityinc.model.Thing;
import com.mobiquityinc.model.TotalObjectsInPackage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * It first evaluates which of the things can be packed in the space. Based on this
 * result, it finds the most expensive ones.
 */

public class PackerResolver {

    private static final Log LOGGER = LogFactory.getLog(PackerResolver.class);

    /**
     * This is main function that call all part of solution step by step
     * @param filePath Path of the file
     * @return The Package values should use
     * @throws APIException It will throw in any issue related to file such as bad file format or not found
     */

    public String resolve(String filePath) throws APIException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            List<TotalObjectsInPackage> finalResult = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                LineValues values = getLineValuesAsobject(line);
                List<Thing> things = createThingObject(values);
                // It should return list because we may have some equal price ones
                List<Thing> allThingResults = getPackingResult(things);
                finalResult.add(setFinalValues(allThingResults,values.getWeightOfPackage()));
            }
            String lines = prepareFinalValues(finalResult);
            LOGGER.info("Final result :" + lines);
            return lines;
        } catch (IOException e) {
            LOGGER.fatal(e.getMessage());
            throw new APIException("File not found!");
        }
    }


    /**
     * This function save all things that can handle by each package and create Total Object which contains package
     * weight and total price of things inside that package
     * @param allThingResults The result of packing objects
     * @param weightOfPackage The Maximum weight of the package
     * @return TotalObjectsInPackage this class holds all needed data for preparing output
     */
    public TotalObjectsInPackage setFinalValues(List<Thing> allThingResults, Float weightOfPackage) {
        Float totalCostOfObjectsInPackage = 0.0f;
        for (Thing allThingResult : allThingResults) {
            totalCostOfObjectsInPackage += allThingResult.getPrice();
        }
        return new TotalObjectsInPackage(allThingResults, totalCostOfObjectsInPackage, weightOfPackage);
    }

    /**
     * And finally after all calculation we create output with some conditions
     * 1- if cant find any thing to save in package return -
     * 2- if there is any possible thing(s) return , seperated values
     * 3- if there is same price between some packages return less weight package weight
     * @param finalResult The result of packing
     * @return The final prepared output solution step
     */

    public String prepareFinalValues(List<TotalObjectsInPackage> finalResult) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TotalObjectsInPackage objectsInPackage : finalResult) {
            if (finalResult.stream()
                    .filter(e -> !e.getWeightOfPackage().equals(objectsInPackage.getWeightOfPackage()))
                    .anyMatch(e -> e.getTotalCostOfObjectsInPackage().equals(objectsInPackage.getTotalCostOfObjectsInPackage()))
            ) {
                List<TotalObjectsInPackage> sameCosts = finalResult.stream().filter(e -> e.getTotalCostOfObjectsInPackage().equals(objectsInPackage.getTotalCostOfObjectsInPackage())).collect(Collectors.toList());
                Float lessWeight = sameCosts.get(0).getWeightOfPackage();
                for (TotalObjectsInPackage sameCost : sameCosts) {
                    if(sameCost.getWeightOfPackage() < lessWeight){
                        lessWeight = sameCost.getWeightOfPackage();
                    }
                }
                stringBuilder.append(lessWeight);
                stringBuilder.append("\n");
            } else if (objectsInPackage.getThings() == Collections.EMPTY_LIST) {
                stringBuilder.append("-");
                stringBuilder.append("\n");
            } else {
                for (int i = 0; i < objectsInPackage.getThings().size(); i++) {
                    stringBuilder.append(objectsInPackage.getThings().get(i).getId()).append(i == objectsInPackage.getThings().size() - 1 ? "" : ",");
                }
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * This function extracts line values and instantiates the LineValues object to get all line values easily
     * @param line The line containing the thing values and package weight
     * @return The Value of the Line read
     * @throws APIException if the file format is corrupt
     */
    public LineValues getLineValuesAsobject(String line) throws APIException {
        try{
            String[] inputLines = line.split(" : ");
            float weightOfPackage = Float.parseFloat(inputLines[0]);
            LineValues values = new LineValues(weightOfPackage,inputLines[1]);
            return values;
        }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            LOGGER.fatal(e.getMessage());
            throw new APIException("Text file is corrupted");
        }
    }

    /**
     * Extracts the values from the input file and instantiates the objects
     * in this function we have to split saved value in LineString property with ',' character to extract each thing properties
     * @param values The Lines read from the file
     * @return A list of THING constructed objects
     */
    public List<Thing> createThingObject(LineValues values) {
        List<Thing> things = new ArrayList<>();
        for (String s : values.getThingsValue()) {
            String[] packageValues = s.split(",");
            if (Float.parseFloat(packageValues[1]) < values.getWeightOfPackage()) {
                float costOfPackage = Float.parseFloat(packageValues[2].substring(0, packageValues[2].indexOf(')')).substring(1));
                int idOfPackage = Integer.parseInt(packageValues[0].substring(1));
                things.add(new Thing(idOfPackage,Float.parseFloat(packageValues[1]),costOfPackage,values.getWeightOfPackage()));
            }
        }
        return things;
    }

    /**
     * Prepares the output solution part using delimiters
     * @param finalValues The final output result of solution
     * @return The constructed solution part as a String
     */
    public String prepareOutPut(List<Integer> finalValues) {
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
    public List<Thing> getPackingResult(List<Thing> things) {
        SortedMap<Float, List<Thing>> finalValues = new TreeMap<>();
        for (Thing thing : things) {
            if(!finalValues.containsKey(thing.getPrice())){
                finalValues.put(thing.getPrice(), new ArrayList<>());
            }
            finalValues.get(thing.getPrice()).add(thing);
        }
        if(finalValues.isEmpty()){
            return Collections.emptyList();
        }
        return finalValues.get(finalValues.lastKey()) ;
    }
}