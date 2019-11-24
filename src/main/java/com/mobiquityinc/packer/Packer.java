package com.mobiquityinc.packer;

import com.mobiquityinc.model.Thing;
import com.mobiquityinc.exception.APIException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * The main Packer class which handles packaging THINGS based on their weights and
 * maximum possible to pack.
 * This class can implement as interface
 * This class has only one method 'pack' and only calls resolve method from PackerResolver class
 */
public class Packer{
    private static final Log LOGGER = LogFactory.getLog(Packer.class);
    public static void main(String[] args) throws APIException {
        LOGGER.info(pack("C:\\Project\\java\\PackageChallenge\\src\\main\\resources\\pack.txt"));
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
        return new PackerResolver().resolve(filePath);
    }
}
