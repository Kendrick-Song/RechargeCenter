package com.example.rechargecenter;

import java.util.List;

public class PhoneGoodsBean {
    private String status;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /*  "itemId": "151302",
            "inPrice": "29.790",
            "numberChoice": "1-10",
            "province": "广东",
            "city": "广东全省",
            "operator": "联通",
            "itemName": "广东联通话费30元直充",
            "rechargeAmount": "30",
            "advicePrice": "30.00",
            "mobileNo": "17666275435",
            "reverseFlag": "0",
            "ss_amount": "30" */
        private String itemId;
        private String inPrice;
        private String numberChoice;
        private String province;
        private String city;
        private String operator;
        private String itemName;
        private String rechargeAmount;
        private String advicePrice;
        private String mobileNo;
        private String reverseFlag;
        private String ss_amount;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getInPrice() {
            return inPrice;
        }

        public void setInPrice(String inPrice) {
            this.inPrice = inPrice;
        }

        public String getNumberChoice() {
            return numberChoice;
        }

        public void setNumberChoice(String numberChoice) {
            this.numberChoice = numberChoice;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getRechargeAmount() {
            return rechargeAmount;
        }

        public void setRechargeAmount(String rechargeAmount) {
            this.rechargeAmount = rechargeAmount;
        }

        public String getAdvicePrice() {
            return advicePrice;
        }

        public void setAdvicePrice(String advicePrice) {
            this.advicePrice = advicePrice;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getReverseFlag() {
            return reverseFlag;
        }

        public void setReverseFlag(String reverseFlag) {
            this.reverseFlag = reverseFlag;
        }

        public String getSs_amount() {
            return ss_amount;
        }

        public void setSs_amount(String ss_amount) {
            this.ss_amount = ss_amount;
        }
    }
}
