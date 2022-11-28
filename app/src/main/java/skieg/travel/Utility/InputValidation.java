package skieg.travel.Utility;

import java.time.LocalDate;

public class InputValidation {

    /**
     * Checks if a string value is empty and returns a boolean of whether or not it is valid input.
     * @param inputString: string being checked
     * @return boolean: true if the string is invalid (empty), false if string is valid (not empty)
     */
    public static boolean invalidStringInput(String inputString) {
        return inputString.trim().equals("");
    }


    /**
     * Checks conditions to see if a date string is invalid.
     * Date must follow this format to be valid: YYYY-MM-DD.
     * The year, month, and day must be numbers, and correspond to a valid value.
     * Year must be at least this year (cannot add an event for a date that already passed).
     * Month must be between 1 and 12.
     * The day must be within a valid range of numbers based on the month (30, 31 or 28 days).
     * @param dateInput: string of a date
     * @return boolean: true if the date is invalid, false if date is valid
     */
    public static boolean invalidDateInput(String dateInput) {
        // Valid date format: YYYY-MM-DD
        // Indices:           0123456789

        if (dateInput.length() != 10) {
            return true;
        }

        // First 4 characters must be numbers (represents the year)
        for (int num = 0; num < 4; num++) {
            if (!Character.isDigit(dateInput.charAt(num))) {
                return true;
            }
        }

        // Month must be 2 numbers
        for (int num = 5; num < 7; num++) {
            if (!Character.isDigit(dateInput.charAt(num))) {
                return true;
            }
        }

        // Day must be 2 numbers
        for (int num = 8; num < 10; num++) {
            if (!Character.isDigit(dateInput.charAt(num))) {
                return true;
            }
        }

        // Indices 4 and 7 must be dashes to separate the year, month, and day
        if (dateInput.charAt(4) != '-' || dateInput.charAt(7) != '-') {
            return true;
        }

        // Year cannot be smaller than the current year
        int year = Integer.parseInt(dateInput.substring(0, 4));
        if (year < LocalDate.now().getYear()) {
            return true;
        }

        // Month must be between 1 and 12
        int month = Integer.parseInt(dateInput.substring(5, 7));
        if (month < 1 || month > 12) {
            return true;
        }

        // Day must be valid based on the month
        int day = Integer.parseInt(dateInput.substring(8, 10));
        boolean thirtyOneDayMonths = (month == 1) || (month == 3) || (month == 5) || (month == 7)
                || (month == 8) || (month == 10) || (month == 12);
        boolean thirtyDayMonths = (month == 4) || (month == 6) || (month == 9) || (month == 11);

        if (month == 2) {
            return day > 28 || day < 1;
        } else if (thirtyOneDayMonths) {
            return day > 31 || day < 1;
        } else if (thirtyDayMonths) {
            return day > 30 || day < 1;
        }

        // If the input passes all these cases, it is a valid date
        return false;
    }


    /**
     * Checks if a month or day number is 1 digit and returns a string adding a 0 in front of the
     * value.
     * If the string is 2 digits, return that string since it's valid.
     * @param value: string value being checked (either day or month)
     * @return string: date value with valid format (2 numbers)
     */
    public static String makeValidDateValue(int value) {
        String dateString = "";

        if (value < 10) {
            dateString = "0" + value;
        } else {
            dateString = String.valueOf(value);
        }

        return dateString;
    }

}
