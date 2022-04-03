package com.infovistar.recylerviewexample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionResultModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("payee_name")
    @Expose
    private String payeeName;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("balance_amount")
    @Expose
    private String balanceAmount;
    @SerializedName("receive_paid_amount")
    @Expose
    private String receivePaidAmount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currency_rate")
    @Expose
    private String currencyRate;
    @SerializedName("currency_amount")
    @Expose
    private String currencyAmount;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("transaction_number")
    @Expose
    private String transactionNumber;
    @SerializedName("receive_date")
    @Expose
    private String receiveDate;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getReceivePaidAmount() {
        return receivePaidAmount;
    }

    public void setReceivePaidAmount(String receivePaidAmount) {
        this.receivePaidAmount = receivePaidAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(String currencyRate) {
        this.currencyRate = currencyRate;
    }

    public String getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(String currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
