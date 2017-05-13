package consumptiontracker.amogus.com.consumptiontracker;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreferenceActivity extends AppCompatActivity {

    @BindView(R.id.preference_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Settings");

        getFragmentManager().beginTransaction()
                .replace(R.id.preferences_wrapper, new MyPrefFragment(), "pref")
                .commit();
    }

    public static class MyPrefFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            Log.d(Utils.TAG, "Preference Fragment launched");
        }
    }
}