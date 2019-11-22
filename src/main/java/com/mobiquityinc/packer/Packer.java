package main.java.com.mobiquityinc.packer;

import main.java.com.mobiquityinc.exception.APIException;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.HashMap;
import java.util.Map;

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
        InputStreamReader reader = new InputStreamReader(input,decoder);
        try (BufferedReader br = new BufferedReader(reader)) {
            String line = null;
            StringBuffer allLinesResult = new StringBuffer();
            while ((line = br.readLine()) != null) {
                String[] splitedLines = line.split(" : ");
                float weightOfPackage = Float.parseFloat(splitedLines[0]);
                Map<Integer,Float> result = prepareAllLines(splitedLines[1], weightOfPackage);
                float maxPackagePossible = findPossiblePackages(result);
                allLinesResult.append(prepareOutPut(result, maxPackagePossible)).append("\n");
            }
            return allLinesResult.toString();
        } catch (IOException e) {
            throw new APIException(e.getMessage());
        }
    }

    private static Map<Integer, Float> prepareAllLines(String splitedLine, float weightOfPackage) {
        Map<Integer, Float> result = new HashMap<>();
        for (String s : splitedLine.split(" ")) {
            String[] packageValues = s.split(",");
            if(Float.parseFloat(packageValues[1]) < weightOfPackage){
                result.put( Integer.parseInt(packageValues[0].substring(1))
                        , Float.valueOf(packageValues[2].substring(0,packageValues[2].indexOf(')'))));
            }
        }
        return result;
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
        if(result.isEmpty()){
            buffer.append("-");
        }else{
            for (Map.Entry<Integer, Float> resultValue : result.entrySet()) {
                if( resultValue.getValue() == maxPackagePossible){
                    buffer.append(resultValue.getKey()).append(",");
                }
            }
            buffer.replace(buffer.lastIndexOf(","),buffer.length(),"");
        }
        return buffer.toString();
    }

}
