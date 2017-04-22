package consumptiontracker.amogus.com.consumptiontracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import consumptiontracker.amogus.com.consumptiontracker.model.Count;

public class CountFragment extends Fragment {

    final String TAG = "COUNTER_OUT";
    final String categories = "Media";
    final String type = "Reading";

    private Count counter;

    public CountFragment() {
        // Required empty public constructor
    }

    public static CountFragment newInstance(String param1, String param2) {
        CountFragment fragment = new CountFragment();
        Bundle args = new Bundle();
        fragment.setRetainInstance(true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        counter = new Count(categories, type);
        counter.save();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_count, container, false);
        Button clicker = (Button)layout.findViewById(R.id.button);
        clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Count temp = counter.findById(Count.class, 1);
                temp.count = temp.count + 1;
                temp.save();
                out();
            }
        });
        return layout;
    }

    private void out() {
        List<Count> books = Count.listAll(Count.class);
        for (Count count : books) {
            Log.d(TAG, "out: " + count.count + " - " + count.countCategory);
        }
    }
}
