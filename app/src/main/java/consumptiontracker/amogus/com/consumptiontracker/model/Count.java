package consumptiontracker.amogus.com.consumptiontracker.model;

import com.orm.SugarRecord;

import java.util.Date;

public class Count extends SugarRecord {

    // Category associated with user activity
    public String countCategory;
    // When the user recorded the activity
    public Date timestamp;
    // Frequency count for a given activity
    public int count;
    // Type of user activity being counted
    public String countAction;

    public Count(){
        // Empty constructor
    }

    public Count(String category, String action) {
        this.countCategory = category;
        this.countAction = action;
        this.count = 0;
        this.timestamp = new Date(System.currentTimeMillis());
    }
}
