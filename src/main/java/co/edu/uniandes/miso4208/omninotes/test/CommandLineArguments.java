package co.edu.uniandes.miso4208.omninotes.test;

import com.beust.jcommander.Parameter;

public class CommandLineArguments {

    @Parameter(description = "Path of the app under test")
    private String appUnderTestPath;

    @Parameter(names = {"-o", "--output"}, description = "Directory where is going to be saved the test results.")
    private String outputDirectory;

    @Parameter(names = {"-s", "--server"}, description = "Appium server URL.")
    private String appiumUrl;

    public String getAppUnderTestPath() {
        return appUnderTestPath;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public String getAppiumUrl() {
        return appiumUrl;
    }
}
