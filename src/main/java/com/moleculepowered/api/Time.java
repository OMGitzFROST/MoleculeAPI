package com.moleculepowered.api;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Time {

    private static final String DEFAULT_DATE_FORMAT = "MM/dd/yyy";

    /*
    DATE MODIFIERS
     */

    /**
     * <p>Adds a specified amount of time to the start date, please note that this method does require
     * a specific format to be entered as its input, That format is as follows "[#][unit]", for example "2h".</p>
     *
     * <p>With that input, it will tell this method to add 2 hours to the provided date, please take a look at
     * the table below for all available unit abbreviations that are accepted by this method</p>
     *
     * <table>
     *     <col width="30%"/>
     *     <col width="10%"/>
     *     <col width="60%"/>
     *     <thead>
     *     <tr>
     *         <th>Unit</th>
     *         <th></th>
     *         <th>Abbreviation</th>
     *     </tr>
     *     <thead>
     *     <tbody>
     *      <tr>
     *          <td>Millisecond</td>
     *          <td></td>
     *          <td>[ms, milli, millisecond, milliseconds]</td>
     *      </tr>
     *      <tr>
     *          <td>Second</td>
     *          <td></td>
     *          <td>[s, sec, second, seconds]</td>
     *      </tr>
     *      <tr>
     *          <td>Minute</td>
     *          <td></td>
     *          <td>[m, min, minute, minutes]</td>
     *      </tr>
     *      <tr>
     *          <td>Hour</td>
     *          <td></td>
     *          <td>[h, hr, hour, hours]</td>
     *      </tr>
     *      <tr>
     *          <td>Day</td>
     *          <td></td>
     *          <td>[d, day, days]</td>
     *      </tr>
     *      <tr>
     *          <td>Week</td>
     *          <td></td>
     *          <td>[wk, week, weeks]</td>
     *      </tr>
     *      <tr>
     *          <td>Month</td>
     *          <td></td>
     *          <td>[mo, month, months]</td>
     *      </tr>
     *      <tr>
     *          <td>Year</td>
     *          <td></td>
     *          <td>[y, year, years]</td>
     *      </tr>
     *   </tbody>
     * </table>
     *
     * @param start The date that will be modified
     * @param input The time that will be added to the start date
     * @return The new date
     * @throws IllegalArgumentException when an invalid input is entered
     */
    public static @NotNull Date add(@NotNull Date start, @NotNull String input) {

        for (String current : input.split("\\s+")) {

            // CONFIGURE PARAMETERS
            int quantity = getQuantity(current);
            String unit = getUnit(current);

            // CREATE CALENDAR OBJECT
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(start);

            switch (unit.toLowerCase()) {
                case "ms":
                case "milli":
                case "millisecond":
                case "milliseconds":
                    startDate.add(Calendar.MILLISECOND, quantity);
                    start = startDate.getTime();
                    break;
                case "s":
                case "sec":
                case "second":
                case "seconds":
                    startDate.add(Calendar.SECOND, quantity);
                    start = startDate.getTime();
                    break;
                case "m":
                case "min":
                case "minute":
                case "minutes":
                    startDate.add(Calendar.MINUTE, quantity);
                    start = startDate.getTime();
                    break;
                case "h":
                case "hr":
                case "hour":
                case "hours":
                    startDate.add(Calendar.HOUR, quantity);
                    start = startDate.getTime();
                    break;
                case "d":
                case "day":
                case "days":
                    startDate.add(Calendar.DATE, quantity);
                    start = startDate.getTime();
                    break;
                case "wk":
                case "week":
                case "weeks":
                    startDate.add(Calendar.WEEK_OF_MONTH, quantity);
                    start = startDate.getTime();
                    break;
                case "mo":
                case "month":
                case "months":
                    startDate.add(Calendar.MONTH, quantity);
                    start = startDate.getTime();
                    break;
                case "y":
                case "year":
                case "years":
                    startDate.add(Calendar.YEAR, quantity);
                    start = startDate.getTime();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid time format provided: " + current);
            }
        }
        return start;
    }

    /**
     * <p>Adds a specified amount of time to today's date, please note that this method does require a specific
     * format to be entered as its input, That format is as follows "[#][unit]", for example "2h".</p>
     *
     * <p>With that input, it will tell this method to add 2 hours to today's date, please take a look at
     * the table below for all available unit abbreviations that are accepted by this method</p>
     *
     * <table>
     *     <col width="30%"/>
     *     <col width="10%"/>
     *     <col width="60%"/>
     *     <thead>
     *     <tr>
     *         <th>Unit</th>
     *         <th></th>
     *         <th>Abbreviation</th>
     *     </tr>
     *     <thead>
     *     <tbody>
     *      <tr>
     *          <td>Millisecond</td>
     *          <td></td>
     *          <td>[ms, milli, millisecond, milliseconds]</td>
     *      </tr>
     *      <tr>
     *          <td>Second</td>
     *          <td></td>
     *          <td>[s, sec, second, seconds]</td>
     *      </tr>
     *      <tr>
     *          <td>Minute</td>
     *          <td></td>
     *          <td>[m, min, minute, minutes]</td>
     *      </tr>
     *      <tr>
     *          <td>Hour</td>
     *          <td></td>
     *          <td>[h, hr, hour, hours]</td>
     *      </tr>
     *      <tr>
     *          <td>Day</td>
     *          <td></td>
     *          <td>[d, day, days]</td>
     *      </tr>
     *      <tr>
     *          <td>Week</td>
     *          <td></td>
     *          <td>[wk, week, weeks]</td>
     *      </tr>
     *      <tr>
     *          <td>Month</td>
     *          <td></td>
     *          <td>[mo, month, months]</td>
     *      </tr>
     *      <tr>
     *          <td>Year</td>
     *          <td></td>
     *          <td>[y, year, years]</td>
     *      </tr>
     *   </tbody>
     * </table>
     *
     * @param input The time that will be added to today's date
     * @return The new date
     * @throws IllegalArgumentException when an invalid input is entered
     */
    public static @NotNull Date add(@NotNull String input) {
        return add(new Date(), input);
    }

    /**
     * <p>Subtracts a specified amount of time to the start date, please note that this method does require
     * a specific format to be entered as its input, That format is as follows "[#][unit]", for example "2h".</p>
     *
     * <p>With that input, it will tell this method to subtracts 2 hours to the date, please take a look at
     * the table below for all available unit abbreviations that are accepted by this method</p>
     *
     * <table>
     *     <col width="30%"/>
     *     <col width="10%"/>
     *     <col width="60%"/>
     *     <thead>
     *     <tr>
     *         <th>Unit</th>
     *         <th></th>
     *         <th>Abbreviation</th>
     *     </tr>
     *     <thead>
     *     <tbody>
     *      <tr>
     *          <td>Millisecond</td>
     *          <td></td>
     *          <td>[ms, milli, millisecond, milliseconds]</td>
     *      </tr>
     *      <tr>
     *          <td>Second</td>
     *          <td></td>
     *          <td>[s, sec, second, seconds]</td>
     *      </tr>
     *      <tr>
     *          <td>Minute</td>
     *          <td></td>
     *          <td>[m, min, minute, minutes]</td>
     *      </tr>
     *      <tr>
     *          <td>Hour</td>
     *          <td></td>
     *          <td>[h, hr, hour, hours]</td>
     *      </tr>
     *      <tr>
     *          <td>Day</td>
     *          <td></td>
     *          <td>[d, day, days]</td>
     *      </tr>
     *      <tr>
     *          <td>Week</td>
     *          <td></td>
     *          <td>[wk, week, weeks]</td>
     *      </tr>
     *      <tr>
     *          <td>Month</td>
     *          <td></td>
     *          <td>[mo, month, months]</td>
     *      </tr>
     *      <tr>
     *          <td>Year</td>
     *          <td></td>
     *          <td>[y, year, years]</td>
     *      </tr>
     *   </tbody>
     * </table>
     *
     * @param start The date that will be modified
     * @param input The time that will be subtracted from the start date
     * @return A new date
     * @throws IllegalArgumentException when an invalid input is entered
     */
    public static @NotNull Date subtract(@NotNull Date start, @NotNull String input) {

        // ADD NEGATIVE VALUES TO EACH TIME UNIT TO ALLOW THIS METHOD TO SUBTRACT
        for (String current : input.split("\\s+")) {
            input = input.replaceAll(current, "-" + current);
        }
        return add(start, input);
    }

    /**
     * <p>Subtracts a specified amount of time to the start date, please note that this method does require
     * a specific format to be entered as its input, That format is as follows "[#][unit]", for example "2h".</p>
     *
     * <p>With that input, it will tell this method to subtract 2 hours to the date, please take a look at
     * the table below for all available unit abbreviations that are accepted by this method</p>
     *
     * <table>
     *     <col width="30%"/>
     *     <col width="10%"/>
     *     <col width="60%"/>
     *     <thead>
     *     <tr>
     *         <th>Unit</th>
     *         <th></th>
     *         <th>Abbreviation</th>
     *     </tr>
     *     <thead>
     *     <tbody>
     *      <tr>
     *          <td>Millisecond</td>
     *          <td></td>
     *          <td>[ms, milli, millisecond, milliseconds]</td>
     *      </tr>
     *      <tr>
     *          <td>Second</td>
     *          <td></td>
     *          <td>[s, sec, second, seconds]</td>
     *      </tr>
     *      <tr>
     *          <td>Minute</td>
     *          <td></td>
     *          <td>[m, min, minute, minutes]</td>
     *      </tr>
     *      <tr>
     *          <td>Hour</td>
     *          <td></td>
     *          <td>[h, hr, hour, hours]</td>
     *      </tr>
     *      <tr>
     *          <td>Day</td>
     *          <td></td>
     *          <td>[d, day, days]</td>
     *      </tr>
     *      <tr>
     *          <td>Week</td>
     *          <td></td>
     *          <td>[wk, week, weeks]</td>
     *      </tr>
     *      <tr>
     *          <td>Month</td>
     *          <td></td>
     *          <td>[mo, month, months]</td>
     *      </tr>
     *      <tr>
     *          <td>Year</td>
     *          <td></td>
     *          <td>[y, year, years]</td>
     *      </tr>
     *   </tbody>
     * </table>
     *
     * @param input The time that will be subtracted to the start date
     * @return A new date
     * @throws IllegalArgumentException when an invalid input is entered
     */
    public static @NotNull Date subtract(@NotNull String input) {
        return subtract(new Date(), input);
    }

    /*
    DATE PARSERS
     */

    /**
     * Parses the input into a usable {@link Date} object. This method takes a format as its first
     * parameter but note that this format must match the format provided as the input.
     *
     * @param format Format representation of the input
     * @param input Target date
     * @return A date from a string
     * @throws IllegalArgumentException when this method fails to parse the input into a date
     */
    public static @NotNull Date parseDate(@NotNull String format, @NotNull String input) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.parse(input);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse date for this input: " + input);
        }
    }

    /**
     * Parses the input into a usable {@link Date} object. Please note that the input must follow
     * the following format <b>"MM/dd/yyyy"</b> or otherwise an error will be thrown.
     *
     * @param input Target date
     * @return A date from a string
     * @throws IllegalArgumentException when this method fails to parse the input into a date
     */
    public static @NotNull Date parseDate(@NotNull String input) {
        return parseDate(DEFAULT_DATE_FORMAT, input);
    }

    /*
    INTERVAL PARSING
     */

    /**
     * Mainly designed for repeating tasks, this method takes an input and converts it into a usable
     * value, used by schedulers and similar.
     *
     * @param input Provided input
     * @return A usable interval
     * @throws IllegalArgumentException when the input is not valid
     */
    public static long parseInterval(@NotNull String input) {
        int quantity = getQuantity(input);
        String unit = getUnit(input);

        switch (unit) {
            case "ms":
            case "milli":
            case "millisecond":
            case "milliseconds":
                return quantity;
            case "s":
            case "sec":
            case "second":
            case "seconds":
                return 1000L * quantity;
            case "m":
            case "min":
            case "minute":
            case "minutes":
                return 60000L * quantity;
            case "h":
            case "hr":
            case "hour":
            case "hours":
                return 3600000L * quantity;
            case "d":
            case "day":
            case "days":
                return 86400000L * quantity;
            case "wk":
            case "week":
            case "weeks":
                return 604800000L * quantity;
            case "mo":
            case "month":
            case "months":
                return 2629800000L * quantity;
            case "y":
            case "year":
            case "years":
                return 31557600000L * quantity;
            default:
                throw new IllegalArgumentException("Invalid interval format provided: " + input);
        }
    }

    /*
    DATE COMPARING
     */

    /**
     * A method used to test whether a date is before a different date.
     *
     * @param target     The date being tested.
     * @param comparedTo The date you will compare the target to.
     * @return Whether the target date is before the compared date.
     * @apiNote If the date is equal to the compared one, this method will always return false.
     */
    public boolean isBeforeDate(String target, String comparedTo) {
        Validate.notNull(target, "The target date cannot be null!");
        Validate.notNull(comparedTo, "The compared to date cannot be null!");
        return parseDate(target).before(parseDate(comparedTo));
    }

    /**
     * A method used to test whether a date is before a different date.
     *
     * @param target     The date being tested.
     * @param comparedTo The date you will compare the target to.
     * @return Whether the target date is before the compared date.
     * @apiNote If the date is equal to the compared one, this method will always return false.
     */
    public boolean isBeforeDate(String target, Date comparedTo) {
        Validate.notNull(target, "The target date cannot be null!");
        Validate.notNull(comparedTo, "The compared to date cannot be null!");
        return parseDate(target).before(comparedTo);
    }

    /**
     * A method used to test whether a date is before a different date.
     *
     * @param target     The date being tested.
     * @param comparedTo The date you will compare the target to.
     * @return Whether the target date is before the compared date.
     * @apiNote If the date is equal to the compared one, this method will always return false.
     */
    public boolean isBeforeDate(@NotNull Date target, @NotNull String comparedTo) {
        Validate.notNull(target, "The target date cannot be null!");
        Validate.notNull(comparedTo, "The compared to date cannot be null!");
        return target.before(parseDate(comparedTo));
    }

    /**
     * A method used to test whether a date is before a different date.
     *
     * @param target     The date being tested.
     * @param comparedTo The date you will compare the target to.
     * @return Whether the target date is before the compared date.
     * @apiNote If the date is equal to the compared one, this method will always return false.
     */
    public boolean isBeforeDate(@NotNull Date target, @NotNull Date comparedTo) {
        Validate.notNull(target, "The target date cannot be null!");
        Validate.notNull(comparedTo, "The compared to date cannot be null!");
        return target.before(comparedTo);
    }

    /**
     * A method used to test whether a date is after a different date.
     *
     * @param target     The date being tested.
     * @param comparedTo The date you will compare the target to.
     * @return Whether the target date is after the compared date.
     * @apiNote If the date is equal to the compared one, this method will always return false.
     */
    public boolean isAfterDate(@NotNull String target, @NotNull String comparedTo) {
        Validate.notNull(target, "The target date cannot be null!");
        Validate.notNull(comparedTo, "The compared to date cannot be null!");
        return parseDate(target).after(parseDate(comparedTo));
    }

    /**
     * A method used to test whether a date is after a different date.
     *
     * @param target     The date being tested.
     * @param comparedTo The date you will compare the target to.
     * @return Whether the target date is after the compared date.
     * @apiNote If the date is equal to the compared one, this method will always return false.
     */
    public boolean isAfterDate(@NotNull Date target, @NotNull Date comparedTo) {
        Validate.notNull(target, "The target date cannot be null!");
        Validate.notNull(comparedTo, "The compared to date cannot be null!");
        return target.after(comparedTo);
    }

    /**
     * A method used to test whether a date is after a different date.
     *
     * @param target     The date being tested.
     * @param comparedTo The date you will compare the target to.
     * @return Whether the target date is after the compared date.
     * @apiNote If the date is equal to the compared one, this method will always return false.
     */
    public boolean isAfterDate(@NotNull String target, @NotNull Date comparedTo) {
        Validate.notNull(target, "The target date cannot be null!");
        Validate.notNull(comparedTo, "The compared to date cannot be null!");
        return parseDate(target).after(comparedTo);
    }

    /**
     * A method used to test whether a date is after a different date.
     *
     * @param target     The date being tested.
     * @param comparedTo The date you will compare the target to.
     * @return Whether the target date is after the compared date.
     * @apiNote If the date is equal to the compared one, this method will always return false.
     */
    public boolean isAfterDate(@NotNull Date target, @NotNull String comparedTo) {
        Validate.notNull(target, "The target date cannot be null!");
        Validate.notNull(comparedTo, "The compared to date cannot be null!");
        return target.after(parseDate(comparedTo));
    }

    /*
    GET TODAY
     */

    /**
     * Used to return today's date.
     *
     * @return Today's date
     */
    public static @NotNull Date getToday() {
        return new Date();
    }

    /**
     * Used to return today's date as a string.
     *
     * @return Today's date as a string
     */
    public static @NotNull String getTodayAsString() {
        return getToday().toString();
    }

    /**
     * Used to return today's date as a string, this method will parse the date into a
     * more readable format.
     *
     * @param format Target format
     * @return Today's date as a formatted string
     */
    public static @NotNull String getTodayAsString(String format) {
        return new SimpleDateFormat(format).format(getToday());
    }

    /*
    UTILITY METHODS
     */

    /**
     * <p>Returns the quantity portion from a string, in the example "2h", the 2 is considered the
     * quantity and thus will be returned by this method.</p>
     *
     * <p>Please note that if a valid quantity could not be found, this method will return 0 in its place,
     * essentially rendering this method pointless</p>
     *
     *
     * @param input Provided input
     * @return The quantity or "0"
     */
    private static @NotNull Integer getQuantity(String input) {
        Matcher quantityMatcher = Pattern.compile("-?\\d+").matcher(input != null ? input : "0");
        return quantityMatcher.find() ? Integer.parseInt(quantityMatcher.group()) : 0;
    }

    /**
     * Returns the unit portion from a string, in the example "2h", the "h" is considered the unit
     * and thus will be returned by this method.
     *
     * <p>Please note that if a valid unit could not be found, this method will return "s" in its place, which
     * stands for "seconds", essentially making any side effect minor</p>
     *
     * @param input Provided input
     * @return The unit or "s"
     */
    private static @NotNull String getUnit(String input) {
        Matcher unitMatcher = Pattern.compile("[a-zA-Z]+").matcher(input != null ? input : "s");
        return unitMatcher.find() ? unitMatcher.group() : "s";
    }
}
