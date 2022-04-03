package com.infovistar.recylerviewexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    private RecyclerView rvTransaction;
    private LinearLayout centerProgressLayout;
    private LinearLayout bottomProgressLayout;
    private LinearLayout noItemLayout;
    private LinearLayoutManager layoutManager;

    private ApiService apiService;
    private int startId     = 0;
    private int indexId     = 0;
    private int loadLimit   = 50;
    private List<TransactionResultModel> resultModelList;
    private TransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        loadTransactionList(startId);
        rvTransaction.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        layoutManager.scrollToPosition(resultModelList.size() - 1);
                        indexId++;
                        startId = loadLimit * indexId;
                        loadTransactionList(startId);
                    }
                }
            }
        });
    }

    private void loadTransactionList(int startId) {
        if(startId == 0) {
            centerProgressLayout.setVisibility(View.VISIBLE);
            rvTransaction.setVisibility(View.GONE);
            noItemLayout.setVisibility(View.GONE);
            bottomProgressLayout.setVisibility(View.GONE);
        } else {
            bottomProgressLayout.setVisibility(View.VISIBLE);
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("start_limit", startId);
        hashMap.put("load_limit", loadLimit);
        apiService
                .getTransactionList(hashMap)
                .enqueue(new Callback<TransactionModel>() {
                    @Override
                    public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                        if(response.body() != null) {
                            if(response.body().getStatus()) {
                                if(startId == 0) {
                                    centerProgressLayout.setVisibility(View.GONE);
                                    rvTransaction.setVisibility(View.VISIBLE);
                                    noItemLayout.setVisibility(View.GONE);
                                }
                                bottomProgressLayout.setVisibility(View.GONE);
                                populateTransactionList(response.body().getResult());
                            } else {
                                if(startId == 0) {
                                    centerProgressLayout.setVisibility(View.GONE);
                                    centerProgressLayout.setVisibility(View.GONE);
                                    noItemLayout.setVisibility(View.VISIBLE);
                                }
                                bottomProgressLayout.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TransactionModel> call, Throwable t) {
                        if(startId == 0) {
                            centerProgressLayout.setVisibility(View.GONE);
                            centerProgressLayout.setVisibility(View.GONE);
                            noItemLayout.setVisibility(View.VISIBLE);
                        }
                        bottomProgressLayout.setVisibility(View.GONE);
                    }
                });
    }

    private void populateTransactionList(List<TransactionResultModel> result) {
        resultModelList.addAll(result);
        transactionAdapter.notifyDataSetChanged();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getString(R.string.app_name));

        rvTransaction           = findViewById(R.id.rv_transaction);
        centerProgressLayout    = findViewById(R.id.center_progress_layout);
        noItemLayout            = findViewById(R.id.no_item_layout);
        bottomProgressLayout    = findViewById(R.id.bottom_progress_layout);
        layoutManager           = new LinearLayoutManager(this);
        rvTransaction.setLayoutManager(layoutManager);
        rvTransaction.setHasFixedSize(true);
        resultModelList         = new ArrayList<>();
        transactionAdapter      = new TransactionAdapter(resultModelList);
        rvTransaction.setAdapter(transactionAdapter);

        apiService              = ApiRequestHelper.getApiClient(this).create(ApiService.class);
    }
    
    class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
        
        private List<TransactionResultModel> resultModelList;
        
        public TransactionAdapter(List<TransactionResultModel> resultModelList) {
            this.resultModelList = resultModelList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transaction, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TransactionResultModel resultModel = resultModelList.get(position);
            holder.setTransactionNumber(resultModel.getTransactionNumber());
            holder.setVendorName(resultModel.getVendorName());
            holder.setReceiveDate(resultModel.getReceiveDate());
            holder.setCurrencyAmount(resultModel.getCurrencyAmount() + " " + resultModel.getCurrency());
        }

        @Override
        public int getItemCount() {
            return resultModelList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tvTransactionNumber;
            private TextView tvClientName;
            private TextView tvDate;
            private TextView tvAmount;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                tvTransactionNumber = itemView.findViewById(R.id.tv_transaction_number);
                tvClientName        = itemView.findViewById(R.id.tv_client_name);
                tvDate              = itemView.findViewById(R.id.tv_date);
                tvAmount            = itemView.findViewById(R.id.tv_amount);
            }

            public void setTransactionNumber(String transactionNumber) {
                tvTransactionNumber.setText(transactionNumber);
            }

            public void setVendorName(String vendorName) {
                tvClientName.setText(vendorName);
            }

            public void setReceiveDate(String receiveDate) {
                tvDate.setText(receiveDate);
            }

            public void setCurrencyAmount(String amount) {
                tvAmount.setText(amount);
            }
        }
    }
}