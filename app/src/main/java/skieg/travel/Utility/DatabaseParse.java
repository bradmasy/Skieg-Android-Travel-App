package skieg.travel.Utility;

/**
 * Database Parse Class.
 */
public class DatabaseParse {

    /**
     * Parses the data values.
     *
     * @param value the values to be parsed.
     * @return the parsed data.
     */
    public static String parseDataValue(String value) {
        String[] data = value.split("=");
        return data[1];
    }

    /**
     * Parses the final data value of a JSON object.
     *
     * @param value the values to be parsed.
     * @return the parsed data.
     */
    public static String parseLastDataValue(String value) {
        String[] data = value.split("=");
        // Remove last curly bracket
        return data[1].substring(0, data[1].length()-1);
    }

}
