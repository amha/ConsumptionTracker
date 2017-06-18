package consumptiontracker.amogus.com.consumptiontracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreferenceActivity extends AppCompatActivity {

    public static final String KEY_APP_THEME = "themeKey";
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


    public static class MyPrefFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            ListPreference appThemePref = (ListPreference) findPreference("themeKey");

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            Log.d(Utils.TAG, "pref change called");

            if (preference instanceof ListPreference) {
                String selectedValue = newValue.toString();
                ListPreference listPreference = (ListPreference) preference;
                listPreference.setSummary(selectedValue);

                SharedPreferences.Editor editor = getActivity()
                        .getPreferences(Context.MODE_PRIVATE).edit();
                editor.putString("themeKey", selectedValue);
                editor.commit();

                getActivity().setTheme(R.style.Light);
                getActivity().finish();

                final Intent intent = getActivity().getIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
            }
            return false;
        }
    }
}