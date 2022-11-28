package skieg.travel.Utility;

public class DatabaseParse {

    public static String parseDataValue(String value) {
        String[] data = value.split("=");
        return data[1];
    }

    public static String parseLastDataValue(String value) {
        String[] data = value.split("=");
        // Remove last curly bracket
        return data[1].substring(0, data[1].length()-1);
    }

}
