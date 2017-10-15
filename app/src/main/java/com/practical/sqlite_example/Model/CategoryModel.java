package com.practical.sqlite_example.Model;

/**
 * Created by Jay on 10/15/2017.
 */

public class CategoryModel {

    int id;
    String category_name;
    String createdat;

    public CategoryModel(String category_name) {
        this.category_name = category_name;
    }

    public CategoryModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }
}
