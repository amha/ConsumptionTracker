package consumptiontracker.amogus.com.consumptiontracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class CountActivity extends AppCompatActivity {

    CountFragment countFragment;
    final String FRAGMENT_NAME = "countFragment";
    final String SELECTED_PARAM = "ID_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        String title = String.valueOf(getIntent().getExtras().get(SELECTED_PARAM));

        if (savedInstanceState != null) {
           countFragment = (CountFragment) getSupportFragmentManager()
                   .getFragment(savedInstanceState, FRAGMENT_NAME);
        }
        else {
            countFragment = countFragment.newInstance(title);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.count_frame, countFragment, FRAGMENT_NAME);
            transaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        getSupportFragmentManager().putFragment(outstate, FRAGMENT_NAME, countFragment);
    }
}
