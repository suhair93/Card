package com.card.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.card.Model.PurchaseModel;
import com.card.R;

import java.util.List;


public class PurchaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PurchaseModel> list ;
    private Context context;
    public PurchaseAdapter(Context context, List<PurchaseModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_purchase, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        Holder newsHolder = (Holder) holder;
        String bold = "fonts/bold.ttf";
        Typeface boldB = Typeface.createFromAsset(context.getAssets(), bold);

        PurchaseModel purchaseModel = list.get(position);

        newsHolder.price.setText(purchaseModel.getPrice());
        newsHolder.price.setTypeface(boldB);
        newsHolder.data_time.setText(purchaseModel.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);

    }
    public class Holder extends RecyclerView.ViewHolder {
        TextView price,data_time ;
        public Holder(View itemView) {
            super(itemView);

            price = (TextView) itemView.findViewById(R.id.price);
            data_time = (TextView) itemView.findViewById(R.id.date_time);

        }

    }
}






