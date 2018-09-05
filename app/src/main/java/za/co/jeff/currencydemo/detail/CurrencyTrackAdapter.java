package za.co.jeff.currencydemo.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.models.CurrencyRecord;

public class CurrencyTrackAdapter extends RecyclerView.Adapter<CurrencyTrackAdapter.UserViewHolder> {

    private List<CurrencyRecord> currencyTrackList;

    public CurrencyTrackAdapter( List<CurrencyRecord> currencyTrackList) {
        this.currencyTrackList = currencyTrackList;
    }

    @Override
    public CurrencyTrackAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card= LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_record_card,parent,false);
        return new CurrencyTrackAdapter.UserViewHolder(card);
    }

    @Override
    public void onBindViewHolder(CurrencyTrackAdapter.UserViewHolder holder, int position) {
        CurrencyRecord currencyRecord=currencyTrackList.get(position);
        holder.name.setText(currencyRecord.getTimeStamp());
        holder.surname.setText(currencyRecord.getValue()+"");
    }


    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return currencyTrackList.size();
    }


    public void addCurrencyRecord(List<CurrencyRecord> list) {
        this.currencyTrackList = list;
        notifyDataSetChanged();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.times_Stamp)
        TextView name;
        @BindView(R.id.name)
        TextView surname;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
