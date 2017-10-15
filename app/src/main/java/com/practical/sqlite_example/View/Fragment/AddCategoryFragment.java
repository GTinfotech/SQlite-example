package com.practical.sqlite_example.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.practical.sqlite_example.Adpters.CategoryAdapter;
import com.practical.sqlite_example.DatabaseHelper.ContactDatabaseHelper;
import com.practical.sqlite_example.Model.CategoryModel;
import com.practical.sqlite_example.R;
import com.practical.sqlite_example.Utils.KeyboardUtils;
import com.practical.sqlite_example.Utils.StringUtils;
import com.practical.sqlite_example.Utils.utils;

import java.util.ArrayList;

/**
 * Created by Jay on 10/15/2017.
 */

public class AddCategoryFragment extends Fragment implements View.OnClickListener {

    View rootView;
    Button buttonSave;
    EditText editTextCategory;
    TextView textViewNoCategory;

    RecyclerView recyclerViewCategory;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    // Database Helper
    ContactDatabaseHelper db;
    KeyboardUtils keyboardUtils;
    utils ut;
    int pos;

    public ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        GetData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_add_category, container, false);

        db = new ContactDatabaseHelper(getActivity());
        keyboardUtils = new KeyboardUtils();
        ut = new utils();

        //initialize view
        initView();

        buttonSave.setOnClickListener(this);

        return rootView;
    }


    public void initView()
    {
        buttonSave = (Button) rootView.findViewById(R.id.buttonSave);
        editTextCategory = (EditText) rootView.findViewById(R.id.editTextCategory);
        textViewNoCategory = (TextView) rootView.findViewById(R.id.textViewNoCategory);

        keyboardUtils.hideKeyboard(getActivity());

        //initialize recycleview
        recyclerViewCategory = (RecyclerView) rootView.findViewById(R.id.recyclerViewCategory);
        recyclerViewCategory.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCategory.setLayoutManager(layoutManager);
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());

    }

    public void GetData()
    {
        categoryModelArrayList.clear();
        categoryModelArrayList = (ArrayList<CategoryModel>) db.getAllCategory();
        if(categoryModelArrayList.size() > 0) {
            recyclerViewCategory.setVisibility(View.VISIBLE);
            textViewNoCategory.setVisibility(View.GONE);
        }else
        {
            recyclerViewCategory.setVisibility(View.GONE);
            textViewNoCategory.setVisibility(View.VISIBLE);
            textViewNoCategory.setText(getString(R.string.category_not));
        }

        adapter = new CategoryAdapter(categoryModelArrayList, new CategoryAdapter.CategoryDetailsAdapterListener() {
            @Override
            public void iconEditViewOnClick(View v, int position) {
                editTextCategory.setText(categoryModelArrayList.get(position).getCategory_name());
                buttonSave.setText(getString(R.string.update));
                pos = position;
            }

            @Override
            public void iconDeleteViewOnClick(View v, int position) {
                int categoryModel = categoryModelArrayList.get(position).getId();
                db.deleteCategory(categoryModel);
                categoryModelArrayList.remove(position);
                adapter.notifyDataSetChanged();
                if(categoryModelArrayList.size() > 0) {
                    recyclerViewCategory.setVisibility(View.VISIBLE);
                    textViewNoCategory.setVisibility(View.GONE);
                }else
                {
                    recyclerViewCategory.setVisibility(View.GONE);
                    textViewNoCategory.setVisibility(View.VISIBLE);
                    textViewNoCategory.setText(getString(R.string.category_not));
                }

            }
        });

        recyclerViewCategory.setAdapter(adapter);

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSave)
        {
            if(buttonSave.getText().toString().equals(getString(R.string.save))) {
                submitForm();
            }else
            {
                Boolean isValidate = true;

                if (!validateCategory()) {
                    isValidate = false;
                }
                if(isValidate)
                {
                    int cat_id = categoryModelArrayList.get(pos).getId();
                    CategoryModel categoryModel = new CategoryModel();
                    categoryModel.setCreatedat(ut.getDateTime());
                    categoryModel.setId(cat_id);
                    categoryModel.setCategory_name(editTextCategory.getText().toString());
                    db.updateCategory(categoryModel);
                    categoryModelArrayList.set(pos, categoryModel);
                    adapter.notifyDataSetChanged();
                    buttonSave.setText(getString(R.string.save));
                    editTextCategory.setText("");
                }
            }
        }
    }


    private void submitForm() {
        Boolean isValidate = true;


        if (!validateCategory()) {
            isValidate = false;
        }

        //if validation done Insert category
        if(isValidate) {
            // Creating category
            CategoryModel categoryModel = new CategoryModel(editTextCategory.getText().toString());
            long id = db.createCategory(categoryModel);
            editTextCategory.setText("");
            int cat_id = Integer.parseInt(String.valueOf(id));
            categoryModel.setId(cat_id);
            categoryModel.setCreatedat(ut.getDateTime());
            categoryModelArrayList.add(categoryModel);
            if(categoryModelArrayList.size() > 0) {
                recyclerViewCategory.setVisibility(View.VISIBLE);
                textViewNoCategory.setVisibility(View.GONE);
            }else
            {
                recyclerViewCategory.setVisibility(View.GONE);
                textViewNoCategory.setVisibility(View.VISIBLE);
                textViewNoCategory.setText(getString(R.string.category_not));
            }
            adapter.notifyDataSetChanged();
        }
    }

    private boolean validateCategory() {

        if (StringUtils.isEmpty(editTextCategory.getText().toString().trim())) {
            editTextCategory.setError(getString(R.string.err_enter_category));
            requestFocus(editTextCategory);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Reqest Focus for edittext
     */
    public void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
