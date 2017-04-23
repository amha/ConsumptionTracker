package consumptiontracker.amogus.com.consumptiontracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import consumptiontracker.amogus.com.consumptiontracker.model.Count;

public class CountFragment extends Fragment {

    final String TAG = "COUNTER_OUT";

    // Determine whether to create a new table counter
    // based on boolean value
    boolean flag = false;
    final String FLAG_LABEL = "first_run";

    // Classification scheme
    final String categories = "Media";
    final String type = "Reading";

    String title;

    // Counter model associated with this fragment
    private static Count counter;

    // Data binding views via butterknife
    @BindView(R.id.button)
    Button clicker;
    @BindView(R.id.count_output)
    TextView countOutput;
    @BindView(R.id.count_toolbar)
    Toolbar toolbar;

    // Default constructor
    public CountFragment() {
        // Required empty public constructor
    }

    public static CountFragment newInstance(String title) {
        CountFragment fragment = new CountFragment();
        Bundle args = new Bundle();
        args.putString("PAGE", title);
        fragment.setRetainInstance(true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            title = getArguments().getString("PAGE");
        }

        if (savedInstanceState == null) {
            counter = new Count(categories, type);
            counter.save();
            flag = true;
        } else {
            // restore saved data
            counter = Count.findById(Count.class, 1);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        outstate.putBoolean(FLAG_LABEL, flag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_count, container, false);
        ButterKnife.bind(this, layout);

        // Update toolbar with selected item
        toolbar.setTitle(title);

        // Display current count
        if (counter != null) {
            countOutput.setText(String.valueOf(getCounter().findById(Count.class, 1).count));
        }

        // Add listener to capture counting
        clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Count temp = getCounter().findById(Count.class, 1);
                temp.count = temp.count + 1;
                temp.save();
                countOutput.setText(String.valueOf(temp.count));
                out();
            }
        });
        return layout;
    }

    private void out() {
        List<Count> books = Count.listAll(Count.class);
        for (Count count : books) {
            Log.d(TAG, "out: " + count.count + " - " + count.countCategory + " - " + count.timestamp);
        }
    }

    public Count getCounter(){
        return counter;
    }
}
