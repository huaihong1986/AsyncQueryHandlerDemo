package cvn.china.com.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by keda on 2016/4/1.
 */
public class BankSpinnerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_ITEM = 1;
    private static final String TAG = "TaskTwonSpinnerAdapter";
    private Context mContext;
    private LayoutInflater inflater;
    private List<String> mBankDataRes;

    public BankSpinnerAdapter(Context context, List<String> mBankDataResList) {
        this.mContext = context;
        inflater = LayoutInflater.from(this.mContext);
        this.mBankDataRes = mBankDataResList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_spinner, parent, false);
                return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ITEM:
                if (mBankDataRes == null || mBankDataRes.size() == 0) {
                    break;
                }

                final ItemViewHolder h = (ItemViewHolder) holder;

                h.title.setText(mBankDataRes.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mBankDataRes == null ? 0 : mBankDataRes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView title;


        public ItemViewHolder(View v) {
            super(v);
            view = v;
            title = (TextView) view.findViewById(R.id.title);
        }

    }
    public void changeData(List<String> mBankDataResList) {
        this.mBankDataRes = mBankDataResList;
        notifyDataSetChanged();
    }
}
