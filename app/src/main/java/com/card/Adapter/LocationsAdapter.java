package com.card.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.card.Model.LocationModel;
import com.card.R;

import java.util.List;


public class LocationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LocationModel> list ;
    private Context context;
    public LocationsAdapter(Context context, List<LocationModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        Holder newsHolder = (Holder) holder;
        String bold = "fonts/bold.ttf";
        Typeface boldB = Typeface.createFromAsset(context.getAssets(), bold);

        LocationModel purchaseModel = list.get(position);

        newsHolder.title.setText(purchaseModel.getName());
        newsHolder.title.setTypeface(boldB);
        newsHolder.location.setText(purchaseModel.getLocation());

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
        TextView title,location ;
        public Holder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            location = (TextView) itemView.findViewById(R.id.location);

        }

    }
}






