package model.designb;

import model.WaitTimeRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HospitalGroupingTest {
    private HospitalGrouping grouping;

    @BeforeEach
    public void beforeEach() {
        grouping = new HospitalGrouping();
    }

    @Test
    public void testSundayGroup() {
        testGroupWithHospital("VGH", "VGH");
    }

    @Test
    public void testMondayGroup() {
        testGroupWithHospital("SPH", "SPH");
    }

    @Test
    public void testTuesdayGroup() {
        testGroupWithHospital("MSJ", "MSJ");
    }

    @Test
    public void testWednesdayGroup() {
        testGroupWithHospital("RHS", "RHS");
    }

    @Test
    public void testThursdayGroup() {
        testGroupWithHospital("BCHBCCHILDREN", "BCHBCCHILDREN");
    }

    @Test
    public void testFridayGroup() {
        testGroupWithHospital("LGH", "LGH");
    }

    private void testGroupWithHospital(String hospital, String expectedGroup) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        Date date = calendar.getTime();
        WaitTimeRecord record = new WaitTimeRecord(hospital, date, 123);

        assertEquals(expectedGroup, grouping.getGroupFor(record));
    }

    @Test
    public void testRequiredGroupsHospital() {
        List<String> requiredGroupsForHospital = Arrays.asList(
            "VGH","SPH","MSJ","RHS","BCHBCCHILDREN","LGH","UBCH","COMNVCUPCC",
            "SGHSQUAMISH","WHCWHISTLER","PEMPEMBERTON","COMSEUPCC","SSHSECHELT",
            "ARH","BH","CGH","DH","ERH","LMH","PAH","RMH","RCH","SMH"
    );

        assertEquals(requiredGroupsForHospital, grouping.getRequiredGroups());
    }

    @Test
    public void testGroupingLabelHospital() {
        assertEquals("Hospital", grouping.getGroupingLabel());
    }
}
