package model.designa;

import model.HourFormatter;
import model.WaitTimeRecord;
import model.ex.NoSuchGroupException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WaitTimesCalculatorATest {
    private WaitTimesCalculatorA dayOfWeekCalculator;
    private WaitTimesCalculatorA hourOfDayCalculator;
    private WaitTimesCalculatorA monthOfYearCalculator;

    @BeforeEach
    public void beforeEach() {
        dayOfWeekCalculator = new WaitTimesCalculatorA(WaitTimesGrouping.DAY_OF_WEEK);
        hourOfDayCalculator = new WaitTimesCalculatorA(WaitTimesGrouping.HOUR_OF_DAY);
        // Does not exist
        monthOfYearCalculator = new WaitTimesCalculatorA(WaitTimesGrouping.MONTH_OF_YEAR);
    }

    @Test
    public void testNull() {
        assertNull(monthOfYearCalculator.getRequiredGroups());
        assertNull(monthOfYearCalculator.getGroupingLabel());
        assertNull(monthOfYearCalculator.getGroup(new WaitTimeRecord("TestHospital", createDateWithDay(1), 123)));
    }

    @Test
    public void testWaitTimeGroupingSunday() {
        testWaitTimeGroupingDay(1, "Sunday");
    }

    @Test
    public void testWaitTimeGroupingMonday() {
        testWaitTimeGroupingDay(2, "Monday");
    }

    @Test
    public void testWaitTimeGroupingTuesday() {
        testWaitTimeGroupingDay(3, "Tuesday");
    }

    @Test
    public void testWaitTimeGroupingWednesday() {
        testWaitTimeGroupingDay(4, "Wednesday");
    }

    @Test
    public void testWaitTimeGroupingThursday() {
        testWaitTimeGroupingDay(5, "Thursday");
    }

    @Test
    public void testWaitTimeGroupingFriday() {
        testWaitTimeGroupingDay(6, "Friday");
    }

    @Test
    public void testWaitTimeGroupingSaturday() {
        testWaitTimeGroupingDay(7, "Saturday");
    }

    private void testWaitTimeGroupingDay(int day, String expectedGroup) {
        WaitTimesCalculatorA calculator = dayOfWeekCalculator;
        WaitTimeRecord testRecord = new WaitTimeRecord("TestHospital", createDateWithDay(day), 123);

        // check the expected group is correct
        assertEquals(expectedGroup, calculator.getGroup(testRecord));

        calculator.addWaitTime(testRecord);

        // check it was added correctly
        List<Integer> waitTimes = calculator.getWaitTimesFor(expectedGroup);

        assertEquals(1, waitTimes.size());
        assertEquals(testRecord.getWaitTimeMinutes(), waitTimes.get(0));
    }

    @Test
    public void testAddMultipleDayGroups() {
        WaitTimesCalculatorA calculator = dayOfWeekCalculator;
        WaitTimeRecord firstRecord = new WaitTimeRecord("TestHospital", createDateWithDay(1), 1);
        WaitTimeRecord secondRecord = new WaitTimeRecord("TestHospital", createDateWithDay(2), 2);
        String firstGroup = calculator.getGroup(firstRecord);
        String secondGroup = calculator.getGroup(secondRecord);

        calculator.addWaitTime(firstRecord);
        calculator.addWaitTime(secondRecord);

        // check the first record went into its group
        List<Integer> firstWaitTimes = calculator.getWaitTimesFor(firstGroup);

        assertEquals(1, firstWaitTimes.size());
        assertEquals(firstRecord.getWaitTimeMinutes(), firstWaitTimes.get(0));

        // check the second record went into its group
        List<Integer> secondWaitTimes = calculator.getWaitTimesFor(secondGroup);

        assertEquals(1, secondWaitTimes.size());
        assertEquals(secondRecord.getWaitTimeMinutes(), secondWaitTimes.get(0));

        // make sure the map is built correctly
        Map<String, List<Integer>> waitTimesMap = calculator.getGroupWaitTimes();

        assertEquals(2, waitTimesMap.size());
        assertEquals(firstWaitTimes, waitTimesMap.get(firstGroup));
        assertEquals(secondWaitTimes, waitTimesMap.get(secondGroup));
    }

    @Test
    public void testGetAverageNoDayGroup() {
        WaitTimesCalculatorA calculator = dayOfWeekCalculator;

        // add some data just to make sure the average is not checking for ANY data
        calculator.addWaitTime(new WaitTimeRecord("TestHospital", new Date(), 1));

        try {
            calculator.getAverageWaitTime("ImpossibleGroup");
            fail("Expected getAverageWaitTime for a group with no values to throw exception");
        } catch (NoSuchGroupException ex) {
            // pass
        }
    }

    @Test
    public void testGetAverageWithDayGroup() {
        WaitTimesCalculatorA calculator = dayOfWeekCalculator;

        calculator.addWaitTime(new WaitTimeRecord("TestHospital", createDateWithDay(1), 4));
        calculator.addWaitTime(new WaitTimeRecord("TestHospital", createDateWithDay(1), 12));

        try {
            assertEquals(8, calculator.getAverageWaitTime("Sunday"));
        } catch (NoSuchGroupException ex) {
            fail("NoSuchGroupException thrown when data is available for group", ex);
        }
    }

    @Test
    public void testRequiredGroupsDayOfWeek() {
        List<String> requiredGroupsForDow = Arrays.asList(
                "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday"
        );

        assertEquals(requiredGroupsForDow, dayOfWeekCalculator.getRequiredGroups());
    }

    @Test
    public void testGroupingLabelDayOfWeek() {
        WaitTimesCalculatorA calculator = dayOfWeekCalculator;

        assertEquals("Day of the week", calculator.getGroupingLabel());
    }

    // EFFECTS: creates a date with the provided day of the week
    private Date createDateWithDay(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return calendar.getTime();
    }

    @Test
    public void testWaitTimeGroupingMidnight() {
        testWaitTimeGroupingHour(0, "12AM");
    }

    @Test
    public void testWaitTimeGroupingDawn() {
        testWaitTimeGroupingHour(6, "6AM");
    }

    @Test
    public void testWaitTimeGroupingNoon() {
        testWaitTimeGroupingHour(12, "12PM");
    }

    @Test
    public void testWaitTimeGroupingEvening() {
        testWaitTimeGroupingHour(19, "7PM");
    }

    @Test
    public void testWaitTimeGroupingLateNight() {
        testWaitTimeGroupingHour(23, "11PM");
    }

    private void testWaitTimeGroupingHour(int hour, String expectedGroup) {
        WaitTimesCalculatorA calculator = hourOfDayCalculator;
        WaitTimeRecord testRecord = new WaitTimeRecord("testHospital", createDateWithHourOfDay(hour), 123);

        // check the expected group is correct
        assertEquals(expectedGroup, calculator.getGroup(testRecord));

        calculator.addWaitTime(testRecord);

        // check it was added correctly
        List<Integer> waitTimes = calculator.getWaitTimesFor(expectedGroup);

        assertEquals(1, waitTimes.size());
        assertEquals(testRecord.getWaitTimeMinutes(), waitTimes.get(0));
    }

    @Test
    public void testAddMultipleHourGroups() {
        WaitTimesCalculatorA calculator = hourOfDayCalculator;
        WaitTimeRecord firstRecord = new WaitTimeRecord("TestHospital", createDateWithHourOfDay(1), 1);
        WaitTimeRecord secondRecord = new WaitTimeRecord("TestHospital", createDateWithHourOfDay(10), 2);
        String firstGroup = calculator.getGroup(firstRecord);
        String secondGroup = calculator.getGroup(secondRecord);

        calculator.addWaitTime(firstRecord);
        calculator.addWaitTime(secondRecord);

        // check the first record went into its group
        List<Integer> firstWaitTimes = calculator.getWaitTimesFor(firstGroup);

        assertEquals(1, firstWaitTimes.size());
        assertEquals(firstRecord.getWaitTimeMinutes(), firstWaitTimes.get(0));

        // check the second record went into its group
        List<Integer> secondWaitTimes = calculator.getWaitTimesFor(secondGroup);

        assertEquals(1, secondWaitTimes.size());
        assertEquals(secondRecord.getWaitTimeMinutes(), secondWaitTimes.get(0));

        // make sure the map is built correctly
        Map<String, List<Integer>> waitTimesMap = calculator.getGroupWaitTimes();

        assertEquals(2, waitTimesMap.size());
        assertEquals(firstWaitTimes, waitTimesMap.get(firstGroup));
        assertEquals(secondWaitTimes, waitTimesMap.get(secondGroup));
    }

    @Test
    public void testGetAverageNoHourGroup() {
        WaitTimesCalculatorA calculator = hourOfDayCalculator;

        // add some data just to make sure the average is not checking for ANY data
        calculator.addWaitTime(new WaitTimeRecord("TestHospital", new Date(), 1));

        try {
            calculator.getAverageWaitTime("ImpossibleGroup");
            fail("Expected getAverageWaitTime for a group with no values to throw exception");
        } catch (NoSuchGroupException ex) {
            // pass
        }
    }

    @Test
    public void testGetAverageWithHourGroup() {
        WaitTimesCalculatorA calculator = hourOfDayCalculator;

        calculator.addWaitTime(new WaitTimeRecord("TestHospital", createDateWithHourOfDay(1), 4));
        calculator.addWaitTime(new WaitTimeRecord("TestHospital", createDateWithHourOfDay(1), 16));

        try {
            assertEquals(10, calculator.getAverageWaitTime("1AM"));
        } catch (NoSuchGroupException ex) {
            fail("NoSuchGroupException thrown when data is available for group", ex);
        }
    }

    @Test
    public void testRequiredGroupsHourOfDay() {
        List<String> requiredGroupsForHod = Arrays.asList(
                "12AM",
                "1AM",
                "2AM",
                "3AM",
                "4AM",
                "5AM",
                "6AM",
                "7AM",
                "8AM",
                "9AM",
                "10AM",
                "11AM",
                "12PM",
                "1PM",
                "2PM",
                "3PM",
                "4PM",
                "5PM",
                "6PM",
                "7PM",
                "8PM",
                "9PM",
                "10PM",
                "11PM"
        );

        assertEquals(requiredGroupsForHod, hourOfDayCalculator.getRequiredGroups());
    }

    @Test
    public void testGroupingLabelHourOfDay() {
        WaitTimesCalculatorA calculator = hourOfDayCalculator;

        assertEquals("Hour of day", calculator.getGroupingLabel());
    }

    // EFFECTS: creates a date with the provided hour of the day
    private Date createDateWithHourOfDay(int hourOfDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        return calendar.getTime();
    }
}
