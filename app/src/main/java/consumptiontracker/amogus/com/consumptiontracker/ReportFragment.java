package consumptiontracker.amogus.com.consumptiontracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import consumptiontracker.amogus.com.consumptiontracker.model.Count;

public class ReportFragment extends Fragment {

    @BindView(R.id.report_media_out)
    TextView mediaOut;
    @BindView(R.id.chore_output)
    TextView choreOut;
    @BindView(R.id.self_out)
    TextView selfOut;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, layout);

        String[] chores = getResources().getStringArray(R.array.chore_actions);
        String[] media = getResources().getStringArray(R.array.media_actions);
        String[] self = getResources().getStringArray(R.array.self_actions);

        for (String chore : chores) {
            mediaOut.append(chore + " - "
                    + Count.find(Count.class, "count_action = ?", chore).size()
                    + "\n"
            );
        }

        for (String mediaItem : media) {
            choreOut.append(mediaItem + " - "
                    + Count.find(Count.class, "count_action = ?", mediaItem).size()
                    + "\n"
            );
        }

        for (String selfItem : self) {
            selfOut.append(selfItem + " - "
                    + Count.find(Count.class, "count_action = ?", selfItem).size()
                    + "\n"
            );
        }

        return layout;
    }


}
