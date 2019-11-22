package main.java.com.mobiquityinc.packer;

import main.java.com.mobiquityinc.exception.APIException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.*;

public class Packer {

//    81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9)
// (6,46.34,€48)

    public static void main(String[] args) throws APIException {
        System.out.println(pack("C:\\Project\\java\\bluerockassignment\\samples\\pack.txt"));
    }

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
        decoder.onMalformedInput(CodingErrorAction.IGNORE);
        FileInputStream input = null;
        try {
            input = new FileInputStream((new File(filePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader reader = new InputStreamReader(input, decoder);
        try (BufferedReader br = new BufferedReader(reader)) {
            String line = null;
            StringBuffer allLinesResult = new StringBuffer();
            while ((line = br.readLine()) != null) {
                SortedMap<Float, List> finalValues = new TreeMap<>();
                String[] splitedLines = line.split(" : ");
                float weightOfPackage = Float.parseFloat(splitedLines[0]);
                finalValues = prepareAllLines(splitedLines[1], weightOfPackage);
//                float maxPackagePossible = findPossiblePackages(result);
//                allLinesResult.append(prepareOutPut(result, maxPackagePossible)).append("\n");
                allLinesResult.append(prepareOutPut(finalValues)).append("\n");
            }
            return allLinesResult.toString();
        } catch (IOException e) {
            throw new APIException(e.getMessage());
        }
    }

    private static String prepareOutPut(SortedMap<Float, List> finalValues) {
        if(finalValues.isEmpty()){
            return "-";
        }
        List<Integer> list = finalValues.get(finalValues.lastKey());
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            buffer.append(list.get(i)).append(i == list.size() - 1 ? "" : ",");
        }
        return buffer.toString();
    }

    private static void sortResultByCost(Map<Integer, Float> result) {

    }

    private static SortedMap<Float, List> prepareAllLines(String splitedLine, float weightOfPackage) {
        SortedMap<Float, List> finalValues = new TreeMap<>();
        for (String s : splitedLine.split(" ")) {
            String[] packageValues = s.split(",");
            if (Float.parseFloat(packageValues[1]) < weightOfPackage) {
                Float costOfPackage = Float.valueOf(packageValues[2].substring(0, packageValues[2].indexOf(')')));
                int idOfPackage = Integer.parseInt(packageValues[0].substring(1));
                if(!finalValues.containsKey(costOfPackage)){
                    finalValues.put(costOfPackage, new ArrayList());
                }
                finalValues.get(costOfPackage).add(idOfPackage);
            }
        }
        return finalValues;
    }

    private static float findPossiblePackages(Map<Integer, Float> result) {
        float maxPackagePossible = 0;
        for (Map.Entry<Integer, Float> resultEntrySet : result.entrySet()) {
            maxPackagePossible = maxPackagePossible < resultEntrySet.getValue() ? resultEntrySet.getValue() : maxPackagePossible;
        }
        return maxPackagePossible;
    }

    private static String prepareOutPut(Map<Integer, Float> result, float maxPackagePossible) {
        StringBuffer buffer = new StringBuffer();
        if (result.isEmpty()) {
            buffer.append("-");
        } else {
            for (Map.Entry<Integer, Float> resultValue : result.entrySet()) {
                if (resultValue.getValue() == maxPackagePossible) {
                    buffer.append(resultValue.getKey()).append(",");
                }
            }
            buffer.replace(buffer.lastIndexOf(","), buffer.length(), "");
        }
        return buffer.toString();
    }

}
