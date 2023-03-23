package swt.group;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperties {

    final private static Logger LOG = LoggerFactory.getLogger(ApplicationProperties.class);

    private static final Properties properties;
    private static final String DEFAULT = null;

    static {
        properties = new Properties();
        try (InputStream input = ApplicationProperties.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                LOG.error("Unable to find application.properties file.");
            }
            properties.load(input);
            //db_password will be logged here
            LOG.info("Application properties successfully loaded: {}", properties);
        } catch (Exception e) {
            LOG.error("An error ocurred:", e);
        }
    }


    public static String getDbUrl() {
        return properties.getProperty("db_url", DEFAULT);
    }

    public static String getDbUser() {
        return properties.getProperty("db_username", DEFAULT);
    }

    public static String getDbPassword() {
        return properties.getProperty("db_password", DEFAULT);
    }
}
