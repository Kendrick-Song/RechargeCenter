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
    public boolean isDefault; //0为默认状态，1为非默认状态

    public ItemAdapter() {
        resultBeanList = new ArrayList<>();
        setDefault();
    }

    public int getSelect() {
        return select;
    }

    public void setResultBeanList(List<PhoneGoodsBean.ResultBean> resultBeanList) {
        this.resultBeanList = resultBeanList;
        isDefault = false;
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

    public void setDefault() {
        String[] itemId = {"151302", "151303", "151304", "151305", "151306", "151307"};
        String[] inPrice = {"29.790", "49.850", "99.500", "198.800", "297.700", "496.250"};
        String numberChoice = "1-10";
        String province = "";
        String city = "";
        String operator = "";
        String[] advicePrice = {"30.00", "50.00", "100.00", "200.00", "300.00", "500.00"};
        String[] ss_amount = {"30", "50", "100", "200", "300", "500"};
        isDefault = true;
        resultBeanList.clear();
        for (int i = 0; i < 6; i++) {
            resultBeanList.add(i, new PhoneGoodsBean.ResultBean());
            resultBeanList.get(i).setItemId(itemId[i]);
            resultBeanList.get(i).setInPrice(inPrice[i]);
            resultBeanList.get(i).setNumberChoice(numberChoice);
            resultBeanList.get(i).setProvince(province);
            resultBeanList.get(i).setCity(city);
            resultBeanList.get(i).setOperator(operator);
            resultBeanList.get(i).setItemName(province + operator + "话费" + ss_amount[i] + "元直充");
            resultBeanList.get(i).setRechargeAmount(ss_amount[i]);
            resultBeanList.get(i).setAdvicePrice(advicePrice[i]);
            resultBeanList.get(i).setSs_amount(ss_amount[i]);
        }
    }
}



