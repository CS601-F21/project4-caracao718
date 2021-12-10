package utilities;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * A class to read in the database configuration Json file
 */
public class DatabaseUtilities {
    public static final String configFileName = "database.json";

    /**
     * Read in the configuration file.
     * @return
     */
    public static DatabaseConfig readConfig() {

        DatabaseConfig config = null;
        Gson gson = new Gson();
        try {
            config = gson.fromJson(new FileReader(configFileName), DatabaseConfig.class);
        } catch (FileNotFoundException e) {
            System.err.println("Config file config.json not found: " + e.getMessage());
        }
        return config;
    }
}
