package consumptiontracker.amogus.com.consumptiontracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import consumptiontracker.amogus.com.consumptiontracker.ListFragment.OnListFragmentInteractionListener;
import consumptiontracker.amogus.com.consumptiontracker.model.Count;

public class ListItemRecyclerViewAdapter extends RecyclerView.Adapter<ListItemRecyclerViewAdapter.ViewHolder> {

    private final String[] mValues;
    private final OnListFragmentInteractionListener mListener;
    private String adapterCategory;

    public ListItemRecyclerViewAdapter(String[] items, String category,
                                       OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        adapterCategory = category;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mediaitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues[position];
        holder.actionLabel.setText(adapterCategory);
        holder.mIdView.setText(getCount(mValues[position]));
        holder.mContentView.setText(mValues[position]);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemClicked(adapterCategory, holder.mItem);
                }
            }
        });
    }

    private String getCount(String action) {
        return String.valueOf(Count.find(Count.class, "count_action = ?", action).size());
    }

    @Override
    public int getItemCount() {
        return mValues.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView actionLabel;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            actionLabel = (TextView) view.findViewById(R.id.ActionLabel);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
