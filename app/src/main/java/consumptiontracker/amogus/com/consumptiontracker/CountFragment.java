package consumptiontracker.amogus.com.consumptiontracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import consumptiontracker.amogus.com.consumptiontracker.model.Count;

@SuppressWarnings("WeakerAccess")
public class CountFragment extends DialogFragment {

    private final static String SELECTED_CATEGORY = "category";
    private final static String SELECTED_ACTON = "action";
    // Data binding views via butterknife
    @BindView(R.id.button)
    Button clicker;
    @BindView(R.id.count_output)
    TextView countOutput;
    @BindView(R.id.cancelCountButton)
    Button cancelButton;
    @BindView(R.id.saveCountButton)
    Button saveButton;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    private String userAction;
    private String userCategory;

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

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Count temp = new Count(userCategory, userAction);
                temp.count = Integer.valueOf(countOutput.getText().toString());
                temp.timestamp = new Date(System.currentTimeMillis());
                temp.rating = Math.round(ratingBar.getRating());
                temp.save();

                Toast.makeText(getActivity().getApplicationContext(),
                        "Successfully updated! Rating = " + ratingBar.getRating(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        String currentActionCount = getActionCount(userAction);
        final int count = Integer.valueOf(currentActionCount);

        if (count > 0) {
            countOutput.setText(String.valueOf(currentActionCount));
        }

        clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countOutput.setText(String.valueOf(count + 1));
            }
        });
        return layout;
    }

    private String getActionCount(String action) {
        return String.valueOf(Count.find(Count.class, "count_action = ?", action).size());
    }


}
