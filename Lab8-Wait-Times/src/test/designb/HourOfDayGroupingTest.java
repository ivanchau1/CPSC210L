package model.designb;

import model.HourFormatter;
import model.WaitTimeRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HourOfDayGroupingTest {
    // TODO
    private HourOfDayGrouping grouping;

    @BeforeEach
    public void setUp() {
        grouping = new HourOfDayGrouping();
    }

    @Test
    public void testMidnightGroup() {
        testGroupWithHour(0, "12AM");
    }

    @Test
    public void testDawnGroup() {
        testGroupWithHour(6, "6AM");
    }

    @Test
    public void testNoonGroup() {
        testGroupWithHour(12, "12PM");
    }

    @Test
    public void testEveningGroup() {
        testGroupWithHour(18, "6PM");
    }

    @Test
    public void testLateNightGroup() {
        testGroupWithHour(23, "11PM");
    }

    private void testGroupWithHour(int hourOfDay, String expectedGroup) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        Date date = calendar.getTime();
        WaitTimeRecord record = new WaitTimeRecord("TestHospital", date, 123);

        assertEquals(expectedGroup, grouping.getGroupFor(record));
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

        assertEquals(requiredGroupsForHod, grouping.getRequiredGroups());
    }

    @Test
    public void testGroupingLabelHourOfDay() {
        assertEquals("Hour of day", grouping.getGroupingLabel());
    }
}
