package consumptiontracker.amogus.com.consumptiontracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import consumptiontracker.amogus.com.consumptiontracker.model.Count;

public class CountFragment extends Fragment {

    final static String SELECTED_CATEGORY = "category";
    final static String SELECTED_ACTON = "action";
    // Counter model associated with this fragment
    private static Count counter;
    // Determine whether to create a new table counter
    // based on boolean value
    boolean flag = false;
    String userAction;
    String userCategory;

    // Data binding views via butterknife
    @BindView(R.id.button)
    Button clicker;

    @BindView(R.id.count_output)
    TextView countOutput;

    // Default constructor
    public CountFragment() {
        // Required empty public constructor
    }

    public static CountFragment newInstance(String category, String action) {
        CountFragment fragment = new CountFragment();
        Bundle args = new Bundle();
        args.putString(SELECTED_CATEGORY, category);
        args.putString(SELECTED_ACTON, action);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userAction = getArguments().getString(SELECTED_ACTON);
            userCategory = getArguments().getString(SELECTED_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_count, container, false);
        ButterKnife.bind(this, layout);

//        // Update toolbar with selected item
//        toolbar.setTitle(userAction);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        String currentActionCount = getActionCount(userAction);
        if (Integer.valueOf(currentActionCount) > 0) {
            countOutput.setText(String.valueOf(currentActionCount));
        }

        clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Count temp = new Count(userCategory, userAction);
                temp.count = temp.count + 1;
                temp.timestamp = new Date(System.currentTimeMillis());
                temp.save();
                countOutput.setText(getActionCount(userAction));
                out();
            }
        });
        return layout;
    }

    private void out() {
        List<Count> books = Count.listAll(Count.class);
        for (Count count : books) {
            Log.d(Utils.TAG, "Out: " + count.count + " - " + count.countCategory + " - " + count.countAction + " - " + count.timestamp);
        }
    }

    private String getActionCount(String action) {
        return String.valueOf(Count.find(Count.class, "count_action = ?", action).size());
    }
}
