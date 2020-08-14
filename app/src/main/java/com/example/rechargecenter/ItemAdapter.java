package com.example.rechargecenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private int select = 0;
    private List<PhoneGoodsBean.ResultBean> resultBeanList;

    public ItemAdapter() {
        resultBeanList = new ArrayList<>();
    }

    public int getSelect() {
        return select;
    }

    public void setResultBeanList(List<PhoneGoodsBean.ResultBean> resultBeanList) {
        this.resultBeanList = resultBeanList;
        notifyDataSetChanged();
    }

    public List<PhoneGoodsBean.ResultBean> getResultBeanList() {
        return resultBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hf_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.item_hf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select = viewHolder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv_origin_price.setText(resultBeanList.get(i).getAdvicePrice() + "元");
        viewHolder.tv_sale_price.setText("售价：" + resultBeanList.get(i).getSs_amount());
        if (viewHolder.getAdapterPosition() == select) {
            viewHolder.item_hf.setSelected(true);
            viewHolder.tv_origin_price.setSelected(true);
            viewHolder.tv_sale_price.setSelected(true);
        } else {
            viewHolder.item_hf.setSelected(false);
            viewHolder.tv_origin_price.setSelected(false);
            viewHolder.tv_sale_price.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return resultBeanList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_origin_price;
        private TextView tv_sale_price;
        private LinearLayout item_hf;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_origin_price = itemView.findViewById(R.id.tv_origin_price);
            this.tv_sale_price = itemView.findViewById(R.id.tv_sale_price);
            this.item_hf = itemView.findViewById(R.id.item_hf);
        }
    }
}



