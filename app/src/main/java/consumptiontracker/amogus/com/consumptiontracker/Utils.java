package consumptiontracker.amogus.com.consumptiontracker;

import android.util.Log;

import java.util.List;

import consumptiontracker.amogus.com.consumptiontracker.model.Count;

/**
 * Collection of static helper methods.
 */

public class Utils {

    final static String TAG = "CT_AM";

    public static void printToConsole(List<Count> listItems) {

        if (listItems != null) {
            for (Count item : listItems) {
                Log.d(TAG, "RECORD: " + item.countCategory + " | "
                        + item.countAction + " | " + item.timestamp);
            }
        } else {
            Log.d(TAG, "null list passed to printToConsole method");
        }
    }
}
