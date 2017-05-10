package consumptiontracker.amogus.com.consumptiontracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import consumptiontracker.amogus.com.consumptiontracker.model.Count;

public class ReportFragment extends Fragment {

    public ReportFragment() {
        // Required empty public constructor
    }

    public static ReportFragment newInstance() {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_report, container, false);
        TextView out = (TextView) layout.findViewById(R.id.temp_report_output);

        String[] chores = getResources().getStringArray(R.array.chore_actions);
        String[] media = getResources().getStringArray(R.array.media_actions);
        String[] self = getResources().getStringArray(R.array.self_actions);

        for (String chore : chores) {
            out.append(chore + " - "
                    + Count.find(Count.class, "count_action = ?", chore).size()
                    + "\n"
            );
        }

        for (String mediaItem : media) {
            out.append(mediaItem + " - "
                    + Count.find(Count.class, "count_action = ?", mediaItem).size()
                    + "\n"
            );
        }

        for (String selfItem : self) {
            out.append(selfItem + " - "
                    + Count.find(Count.class, "count_action = ?", selfItem).size()
                    + "\n"
            );
        }

        return layout;
    }


}
