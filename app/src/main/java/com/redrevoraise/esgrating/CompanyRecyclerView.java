package com.redrevoraise.esgrating;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CompanyRecyclerView {
    private Context mContext;
    private CompanyRecyclerView.CompanyAdapter mCompanyAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<CompanyFront> company){
        mContext = context;
        mCompanyAdapter = new CompanyRecyclerView.CompanyAdapter(company);
        mCompanyAdapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new CompanyRecyclerView.CompanyAdapter(company));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    class CompanyItemView extends RecyclerView.ViewHolder{

        private TextView companyName;
        private TextView companyRating;
        private TextView companyScore;
        private TextView companyTicker;
        private RelativeLayout backgroundCompany;


        public CompanyItemView(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.company_layout, parent, false));

            companyName = itemView.findViewById(R.id.companyName);
            companyRating = itemView.findViewById(R.id.companyRating);
            companyScore = itemView.findViewById(R.id.companyScore);
            companyTicker = itemView.findViewById(R.id.companyTicker);
            backgroundCompany = itemView.findViewById(R.id.backgroundCompany);
        }
        public void bind(CompanyFront company){
            companyName.setText(company.getCompany_name());
            companyName.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            companyRating.setText(company.getTotal_grade());
            companyScore.setText(company.getTotal().toString());
            companyTicker.setText(company.getStock_symbol());

            backgroundCompany.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CompanyActivity.class);
                    intent.putExtra("ticker", company.getStock_symbol());
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                            Pair.create(companyName, "name"), Pair.create(companyScore, "esg"),
                            Pair.create(companyRating, "grade"), Pair.create(companyTicker, "ticker"));
                    mContext.startActivity(intent, options.toBundle());
                }
            });
        }
    }

    class CompanyAdapter extends RecyclerView.Adapter<CompanyItemView>{
        private List<CompanyFront> mCompanyList;

        public CompanyAdapter(List<CompanyFront> mCompanyList) {
            this.mCompanyList = mCompanyList;
        }

        @NonNull
        @Override
        public CompanyItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CompanyItemView(parent);
        }

        @Override
        public long getItemId(int position) {
            if (position < mCompanyList.size()){
                mCompanyList.get(position).getCompany_name();
            }
            return RecyclerView.NO_ID;
        }

        @Override
        public void onBindViewHolder(@NonNull CompanyItemView holder, int position) {
            holder.bind(mCompanyList.get(position));
        }

        @Override
        public int getItemCount() {
            return mCompanyList.size();
        }
    }
}


