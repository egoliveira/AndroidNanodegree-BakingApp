package nanodegree.bakingapp.view;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    private final List<T> mItems;

    public BaseAdapter() {
        this.mItems = new ArrayList<>();
    }

    public void setItems(List<T> items) {
        mItems.clear();

        if (items != null) {
            mItems.addAll(items);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public T getItem(int position) {
        T item = null;

        if ((position >= 0) && (position < mItems.size())) {
            item = mItems.get(position);
        }

        return item;
    }
}
