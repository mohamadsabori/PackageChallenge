package com.mobiquityinc.packer;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class TestRunner {
    private static Log log = LogFactory.getLog(TestRunner.class);
    public static void main(String[] args){
        log.info("Start checking all tests!");
        log.info("Start PackerTest class");
        Result result = JUnitCore.runClasses(PackerTest.class);
        if(!result.wasSuccessful()){
            log.fatal("PackerTest failures : ");
        }
        result.getFailures().stream().forEach(log::error);
        log.info("PackerTest was successful : " + result.wasSuccessful());
        result = JUnitCore.runClasses(PackerHugeTests.class);
        log.info("Start PackerHugeTests class");
        if(!result.wasSuccessful()){
            log.fatal("PackerHugeTests failures : ");
        }
        result.getFailures().stream().forEach(log::error);
        log.info("PackerHugeTests was successful : " + result.wasSuccessful());
    }
}
