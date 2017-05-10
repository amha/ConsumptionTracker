package consumptiontracker.amogus.com.consumptiontracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * An fragment
 */
public class ListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "columnCount";
    private static final String ARG_SELECTED_TAB = "selectedTab";
    //  Collection of user actions to track.
    List<String> mediaItems;
    //  The category associated with a set of user actions.
    private String category;
    //  ?
    private int mColumnCount = 1;

    //  Pass user clicks to the parent activity.
    private OnListFragmentInteractionListener mListener;

    public ListFragment() {
    }

    @SuppressWarnings("unused")
    public static ListFragment newInstance(int columnCount, String selectedCategory) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_SELECTED_TAB, selectedCategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            category = getArguments().getString(ARG_SELECTED_TAB);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mediaitem_list, container, false);

        mediaItems = new ArrayList<String>();
        String[] mediaArray;

        Log.d(Utils.TAG, "List Fragment | category value = " + category);

        switch (category) {
            case "Media":
                mediaArray = getResources().getStringArray(R.array.media_actions);
                break;
            case "Chores":
                mediaArray = getResources().getStringArray(R.array.chore_actions);
                break;
            default:
                mediaArray = getResources().getStringArray(R.array.self_actions);
                break;
        }

        for (String item : mediaArray) {
            mediaItems.add(item);
        }

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(
                        new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(
                        new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.setAdapter(
                    new ListItemRecyclerViewAdapter(mediaItems, category, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onItemClicked(String category, String action);
    }
}