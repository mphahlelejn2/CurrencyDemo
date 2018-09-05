package za.co.jeff.currencydemo.currency;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.detail.DetailActivity;
import za.co.jeff.currencydemo.models.Currency;


public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.UserViewHolder> {

    private List<Currency> currencyList;
    private ICurrency.View view;
    private ItemFilter mFilter = new ItemFilter();
    private List<Currency> filteredData = null;

    public CurrencyAdapter(ICurrency.View view, List<Currency> currencyList) {
        this.currencyList=currencyList;
       this.filteredData= currencyList;
        this.view=view;
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(view.getRecyclerView());
    }

    @Override
    public CurrencyAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View card= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_story_card,parent,false);
        return new CurrencyAdapter.UserViewHolder(card);
    }

    @Override
    public void onBindViewHolder(CurrencyAdapter.UserViewHolder holder, int position) {
        Currency currency =filteredData.get(position);
        holder.name.setText(currency.getCurrencyCode());
        holder.surname.setText(currency.getCurrencyDescription());
        holder.view.setOnClickListener(view -> {
            openDetailActivity(currency);
        });
    }

    private void openDetailActivity(Currency currency) {
        Intent intent=new Intent(view.getContext(),DetailActivity.class);
        Gson gson = new Gson();
        String jsonString = gson.toJson(currency);
        intent.putExtra("data", jsonString );
        view.getActivity().startActivity(intent);
    }

    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public void addCurrency(List<Currency> list) {
        this.currencyList = list;
       this.filteredData= list;
        notifyDataSetChanged();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.surname)
        TextView surname;
        @BindView(R.id.cardView)
        public View view;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.RIGHT) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getActivity());
                builder.setMessage("Are you sure you want to delete?");
                builder.setPositiveButton("REMOVE", (dialog, which) -> {
                    view.deleteCurrency(filteredData.get(position));
                    notifyDataSetChanged();
                    return;
                }).setNegativeButton("CANCEL", (dialog, which) -> {
                    notifyDataSetChanged();
                    return;
                }).show();
            }
        }
    };


    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<Currency> list = currencyList;
            int count = list.size();
            final ArrayList<Currency> nlist = new ArrayList<>(count);
            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getCurrencyCode();
                if (filterableString.toLowerCase().contains(filterString)
                        ||list.get(i).getCurrencyCode().toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Currency>) results.values;
            notifyDataSetChanged();
        }
    }

}
