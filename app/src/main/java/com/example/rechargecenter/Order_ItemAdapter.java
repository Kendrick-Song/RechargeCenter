package com.example.rechargecenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Order_ItemAdapter extends RecyclerView.Adapter<Order_ItemAdapter.ViewHolder>{


    @NonNull
    @Override
    public Order_ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Order_ItemAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView chargetype;
        private TextView paytype;
        private TextView amount;
        private TextView time;
        private RelativeLayout item_order;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chargetype = itemView.findViewById(R.id.order_chargetype);
            this.paytype = itemView.findViewById(R.id.order_paytype);
            this.amount = itemView.findViewById(R.id.order_amount);
            this.time = itemView.findViewById(R.id.order_time);
            this.item_order = itemView.findViewById(R.id.item_order);

        }
    }
}



