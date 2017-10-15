package com.practical.sqlite_example.Adpters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practical.sqlite_example.Model.CategoryModel;
import com.practical.sqlite_example.R;

import java.util.ArrayList;

/**
 * Created by Jay on 10/15/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private ArrayList<CategoryModel> dataSet;
    public CategoryDetailsAdapterListener onClickListener;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewEdit,imageViewDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.imageViewEdit = (ImageView) itemView.findViewById(R.id.imgeEdit);
            this.imageViewDelete = (ImageView) itemView.findViewById(R.id.imgeDelete);
        }
    }

    public CategoryAdapter(ArrayList<CategoryModel> data, CategoryDetailsAdapterListener listener) {
        this.dataSet = data;
        this.onClickListener = listener;

    }

    public CategoryAdapter(ArrayList<CategoryModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rawitem_category, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        final ImageView imageViewDelete = holder.imageViewDelete;
        final ImageView imageViewEdit = holder.imageViewEdit;

        textViewName.setText(dataSet.get(listPosition).getCategory_name());

        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.iconDeleteViewOnClick(imageViewDelete, listPosition);
            }
        });

        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.iconEditViewOnClick(imageViewEdit, listPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface CategoryDetailsAdapterListener {

        void iconEditViewOnClick(View v, int position);

        void iconDeleteViewOnClick(View v, int position);

    }
}
