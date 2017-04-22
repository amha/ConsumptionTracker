package consumptiontracker.amogus.com.consumptiontracker.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by amhamogus on 4/22/17.
 */

public class Count extends SugarRecord {

    // Category associated with user activity
    public String countCategory;

    // Type of user activity being counted
    String countType;

    // When the user recorded the activity
    Date timestamp;

    // Frequency count for a given activity
    public int count;

    public Count(){
        // Empty constructor
    }

    public Count(String category, String type){
        this.countCategory = category;
        this.countType = type;
        this.count = 0;
        this.timestamp = new Date(System.currentTimeMillis());
    }

}
