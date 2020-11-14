package co.edu.uniandes.miso4208.omninotes.test;

import com.beust.jcommander.JCommander;
import org.apache.commons.lang3.StringUtils;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {

        CommandLineArguments cliArguments = new CommandLineArguments();
        JCommander.newBuilder().addObject(cliArguments).build().parse(args);

        TestConfiguration configuration = null;
        try {
            configuration = loadConfiguration(cliArguments);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        List <XmlSuite> xmlSuites = null;
        try {
            xmlSuites = loadTestNgXml("/testng.xml");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        TestNG testNG = new TestNG();
        testNG.setXmlSuites(xmlSuites);
        testNG.setOutputDirectory(configuration.getOutputDir().getPath());
        testNG.setVerbose(2);

        System.out.println("Running test...");
        testNG.run();

    }

    private static List<XmlSuite> loadTestNgXml(String name) throws IOException {
        InputStream is = TestRunner.class.getResourceAsStream(name);
        return new Parser(is).parseToList();
    }

    private static TestConfiguration loadConfiguration(CommandLineArguments cliArguments) throws MalformedURLException {

        TestConfiguration config = TestConfiguration.getInstance();

        if (!StringUtils.isEmpty(cliArguments.getTestType())) {
            config.setTestType(cliArguments.getTestType());
        }

        if (!StringUtils.isEmpty(cliArguments.getOutputDirectory())) {
            config.setOutputDir(new File(cliArguments.getOutputDirectory()));
        }

        if (!StringUtils.isEmpty(cliArguments.getAppUnderTestPath())) {
            File classpathRoot = new File(System.getProperty("user.dir"));
            config.setAppUnderTestPath(new File(cliArguments.getAppUnderTestPath()).getAbsolutePath());
        }

        if (!StringUtils.isEmpty(cliArguments.getAppiumUrl())) {
            config.setAppiumServerUrl(new URL(cliArguments.getAppiumUrl()));
        }

        return config;

    }

    private static String getAbsolutePath(String filePath) {
        return new File(filePath).getAbsolutePath();
    }

}
