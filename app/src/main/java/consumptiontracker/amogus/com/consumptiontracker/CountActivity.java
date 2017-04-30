package consumptiontracker.amogus.com.consumptiontracker;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class CountActivity extends AppCompatActivity {

    //  Tag used to create and retrieve a given fragment
    final String FRAGMENT_NAME = "countFragment";

    // ?
    final String SELECTED_PARAM = "ID_NUMBER";

    //  List of user actions to be counted.
    CountFragment countFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        // Get title parameter from intent
        String title = String.valueOf(getIntent().getExtras().get(SELECTED_PARAM));
        Log.d(Utils.TAG, "ACTIVITY - title = " + title);

        if (savedInstanceState != null) {
            // Restore fragment if it was saved...
            countFragment = (CountFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, FRAGMENT_NAME);
        } else {
            //  ...otherwise create a new fragment
            countFragment = CountFragment.newInstance(title);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.count_frame, countFragment, FRAGMENT_NAME);
            transaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, FRAGMENT_NAME, countFragment);
    }
}
