package model.designb;

import model.WaitTimeRecord;

import java.util.*;

// represents the grouping for "by the day of the week of the record"
public class HospitalGrouping implements GroupingMechanism {
    // EFFECTS: returns the day of the week (e.g. "Monday") that corresponds with the record's time
    @Override
    public String getGroupFor(WaitTimeRecord record) {
        String hospital = record.getHospital();

        return hospital;
    }

    // EFFECTS: returns a list of the days of the week starting from Sunday ("Sunday", "Monday", ...)
    @Override
    public List<String> getRequiredGroups() {
        return Arrays.asList(
                "VGH","SPH","MSJ","RHS","BCHBCCHILDREN","LGH","UBCH","COMNVCUPCC",
                "SGHSQUAMISH","WHCWHISTLER","PEMPEMBERTON","COMSEUPCC","SSHSECHELT",
                "ARH","BH","CGH","DH","ERH","LMH","PAH","RMH","RCH","SMH"
        );
    }

    // EFFECTS: returns the label/name of this mechanism. In this case, "Hospital"
    @Override
    public String getGroupingLabel() {
        return "Hospital";
    }
}
