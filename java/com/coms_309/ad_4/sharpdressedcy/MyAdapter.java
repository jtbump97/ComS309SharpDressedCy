package com.coms_309.ad_4.sharpdressedcy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This instantiates the adapter for a RecyclerView, allowing the data to be loaded and changed.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public MyViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    /**
     * This loads the initial dataset
     * @param myDataset
     *          String array of the dataset
     */
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    /**
     * This creates new views based on the parent
     * @param parent
     *          ViewGroup to start from
     * @param viewType
     *          type of view
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_closet, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    /**
     * This sets the content of the view at the given position
     * @param holder
     *          ViewHolder to edit data of
     * @param position
     *          position to set text
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);

    }

    /**
     * Returns the size of the dataset
     * @return
     *          dataset size
     */
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public void updateDataset(ArrayList<String> dataset) {
        String[] tempSet = dataset.toArray(new String[dataset.size()]);
        mDataset = new String[tempSet.length];
        for(int i=0; i<tempSet.length; i++) {
            mDataset[i] = tempSet[i];
        }
        notifyDataSetChanged();
    }
}
