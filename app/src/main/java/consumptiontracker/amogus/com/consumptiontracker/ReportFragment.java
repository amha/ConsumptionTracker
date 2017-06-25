package consumptiontracker.amogus.com.consumptiontracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import consumptiontracker.amogus.com.consumptiontracker.model.Count;

public class ReportFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.report_media_out)
    TextView mediaOut;
    @BindView(R.id.chore_output)
    TextView choreOut;
    @BindView(R.id.self_out)
    TextView selfOut;
    @BindView(R.id.spinner)
    Spinner mSpinner;
    @BindView(R.id.chart)
    PieChart mPieChart;

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

        ArrayAdapter<CharSequence> spinnerAdapter =
                ArrayAdapter.createFromResource(getContext(),
                        R.array.spinner_options,
                        android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(this);

        // Get names of all user tasks being counted in the app
        String[] chores = getResources().getStringArray(R.array.chore_actions);
        String[] media = getResources().getStringArray(R.array.media_actions);
        String[] self = getResources().getStringArray(R.array.self_actions);

        // Configuring the Pie Chart
        List<PieEntry> entryList = new ArrayList<>();
        entryList.add(new PieEntry(((float)
                Count.find(Count.class, "count_category = ?", "Chores").size()), "Chores"));
        entryList.add(new PieEntry(((float)
                Count.find(Count.class, "count_category = ?", "Media").size()), "Media"));
        entryList.add(new PieEntry(((float)
                Count.find(Count.class, "count_category = ?", "Self").size()), "Self"));

        PieDataSet dataSet = new PieDataSet(entryList, "Summary");
        dataSet.setColors(new int[]{
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent}, getContext());
        PieData data = new PieData(dataSet);
        mPieChart.setData(data);

        Description description = new Description();
        description.setText("Summary");
        description.setPosition(100, 100);
        mPieChart.setDescription(description);
        mPieChart.invalidate();

        // Populating the data table
        for (String chore : chores) {
            mediaOut.append(chore + " - "
                    + Count.find(Count.class, "count_action = ?", chore).size()
                    + "\n");
        }

        for (String mediaItem : media) {
            choreOut.append(mediaItem + " - "
                    + Count.find(Count.class, "count_action = ?", mediaItem).size()
                    + "\n");
        }

        for (String selfItem : self) {
            selfOut.append(selfItem + " - "
                    + Count.find(Count.class, "count_action = ?", selfItem).size()
                    + "\n");
        }
        return layout;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Log.d(Utils.TAG, "Item selected");
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
}
