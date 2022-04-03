package com.infovistar.recylerviewexample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<TransactionResultModel> result = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<TransactionResultModel> getResult() {
        return result;
    }

    public void setResult(List<TransactionResultModel> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
