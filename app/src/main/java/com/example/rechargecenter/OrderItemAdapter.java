package com.example.rechargecenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    private List<OrderItemBean.ResultEntity> resultEntityList;

    public OrderItemAdapter() {
        resultEntityList = new ArrayList<>();
    }

    public void setResultEntityList(List<OrderItemBean.ResultEntity> resultEntityList) {
        this.resultEntityList = resultEntityList;
        notifyDataSetChanged();
    }

    public List<OrderItemBean.ResultEntity> getResultEntityList() {
        return resultEntityList;
    }

    @NonNull
    @Override
    public OrderItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.ViewHolder viewHolder, int i) {
        viewHolder.chargetype.setText(resultEntityList.get(i).getType());
        viewHolder.paytype.setText(resultEntityList.get(i).getPayment_method());
        viewHolder.amount.setText(resultEntityList.get(i).getRechargeAmount());
        viewHolder.time.setText(resultEntityList.get(i).getAddtime());
        viewHolder.status.setText(resultEntityList.get(i).getStatus());
    }

    @Override
    public int getItemCount() {
        return resultEntityList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView chargetype;
        private TextView paytype;
        private TextView amount;
        private TextView time;
        private TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chargetype = itemView.findViewById(R.id.order_chargetype);
            this.paytype = itemView.findViewById(R.id.order_paytype);
            this.amount = itemView.findViewById(R.id.order_amount);
            this.time = itemView.findViewById(R.id.order_time);
            this.status = itemView.findViewById(R.id.tv_status);
        }
    }
}



