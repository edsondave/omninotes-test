package co.edu.uniandes.miso4208.omninotes.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public final class TestConfiguration {

    public static final String DEFAULT_APP_UNDER_TEST_PATH = new File("omninotes.apk").getAbsolutePath();
    public static final File DEFAULT_OUTPUT_DIRECTORY;
    public static final URL DEFAULT_APPIUM_SERVER_URL;

    private static TestConfiguration instance;
    private String appUnderTestPath = DEFAULT_APP_UNDER_TEST_PATH;
    private File outputDir = DEFAULT_OUTPUT_DIRECTORY;
    private URL appiumServerUrl = DEFAULT_APPIUM_SERVER_URL;

    static {

        String path = new StringBuilder()
                .append("test-output/")
                .append(UUID.randomUUID().toString())
                .append("/")
                .toString();
        DEFAULT_OUTPUT_DIRECTORY = new File(path);

        URL appiumServerUrl = null;
        try {
            appiumServerUrl = new URL("http://localhost:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DEFAULT_APPIUM_SERVER_URL = appiumServerUrl;

    }

    private TestConfiguration() {
    }

    public static TestConfiguration getInstance() {
        if (instance == null) {
            instance = new TestConfiguration();
        }
        return instance;
    }

    public File getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    public String getAppUnderTestPath() {
        return appUnderTestPath;
    }

    public void setAppUnderTestPath(String appUnderTestPath) {
        this.appUnderTestPath = appUnderTestPath;
    }

    public URL getAppiumServerUrl() {
        return appiumServerUrl;
    }

    public void setAppiumServerUrl(URL appiumServerUrl) {
        this.appiumServerUrl = appiumServerUrl;
    }

}