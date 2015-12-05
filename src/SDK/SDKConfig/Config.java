package SDK.SDKConfig;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Kahlen on 01-11-2015.
 */
public class Config {
    //Create variables for each of the JSON objects.

    //Database settings
    private static String host;
    private static String port;

    // Create init-method to read from the config.json.dist file
    // and parse it to the variables in the class.
    public static void init() {

        //Initialize imported Java-class JSONParser as jsonParser object.
        JSONParser jsonParser = new JSONParser();

        try {
            //Initialize imported Java-class FileReader as json object
            //with the specific path to the .json file.
            FileReader json = new FileReader("src/SDK/SDKConfig/config.json");

            //Initialize Object class as json, parsed by jsonParsed.
            Object obj = jsonParser.parse(json);

            //Instantiate JSONObject class as jsonObject equal to obj object.
            JSONObject jsonObject = (JSONObject) obj;

            //Use set-methods for defining static variables from json-file.
            setHost((String) jsonObject.get("host"));
            setPort((String) jsonObject.get("port"));

        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    //Created getters and setters for each of the variables.
    public static String getHost() {
        return host;
    }
    public static String getPort() {
        return port;
    }

    private static void setHost(String host) {
        Config.host = host;
    }
    private static void setPort(String port) {
        Config.port = port;
    }


}
