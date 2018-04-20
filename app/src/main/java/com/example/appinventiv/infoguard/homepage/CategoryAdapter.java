package com.example.appinventiv.infoguard.homepage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.appinventiv.infoguard.R;
import com.example.appinventiv.infoguard.interfaces.OnItemClickListener;
import com.example.appinventiv.infoguard.pojo.CategoryData;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<CategoryData> arrayList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public CategoryAdapter(ArrayList<CategoryData>arrayList, Context context, OnItemClickListener onItemClickListener) {
        this.arrayList=arrayList;
        this.context=context;
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_show_data,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        holder.tvcategory.setText(arrayList.get(position).getCategory());
        holder.tvUsername.setText(arrayList.get(position).getUsername());
        holder.tvnumber.setText(arrayList.get(position).getNumber());
        holder.tvEmail.setText(arrayList.get(position).getEmail());
        holder.tvDOB.setText(arrayList.get(position).getDob());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,position,arrayList.get(position).getCategory(),arrayList.get(position).getUsername(),
                        arrayList.get(position).getNumber(), arrayList.get(position).getEmail(),arrayList.get(position).getDob());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

     class CategoryViewHolder extends RecyclerView.ViewHolder {
       private TextView tvcategory,tvUsername,tvnumber,tvEmail,tvDOB;
        private ImageView ivEdit;

         CategoryViewHolder(View itemView) {
            super(itemView);
            tvcategory=itemView.findViewById(R.id.tv_category);
            tvUsername=itemView.findViewById(R.id.tv_username_value);
            tvnumber=itemView.findViewById(R.id.tv_number_value);
            tvEmail=itemView.findViewById(R.id.tv_email_value);
            tvDOB=itemView.findViewById(R.id.tv_dob_value);
            ivEdit=itemView.findViewById(R.id.iv_edit);
        }
    }
}