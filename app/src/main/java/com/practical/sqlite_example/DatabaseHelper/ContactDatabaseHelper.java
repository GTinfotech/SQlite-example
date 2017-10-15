package com.practical.sqlite_example.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.practical.sqlite_example.Model.CategoryModel;
import com.practical.sqlite_example.Utils.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jay on 10/15/2017.
 */

public class ContactDatabaseHelper extends SQLiteOpenHelper {

    utils ut = new utils();

    // Logcat tag
    private static final String LOG = "ContactDatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Table Names
    private static final String TABLE_CATEGORY = "category";
    private static final String TABLE_CONTACT = "contact";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // Category Table - column names
    private static final String KEY_CAT_NAME = "category_name";

    // Contact Table - column names
    private static final String KEY_CON_NAME = "con_name";
    private static final String KEY_CON_MOBILE = "con_number";
    private static final String KEY_CON_AGE = "con_age";
    private static final String KEY_CON_CATEGORY = "con_category";
    private static final String KEY_CON_CATEGORY_ID = "con_category_id";
    private static final String KEY_CON_EMAIL = "con_email";


    // Table Create Statements
    // Category table create statement
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "
            + TABLE_CATEGORY + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CAT_NAME
            + " TEXT," + KEY_CREATED_AT
            + " DATETIME" + ")";

    // Contact table create statement
    private static final String CREATE_TABLE_CONTACT = "CREATE TABLE " + TABLE_CONTACT
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CON_NAME + " TEXT,"+ KEY_CON_MOBILE + " TEXT,"
            + KEY_CON_AGE +" INTEGER,"+ KEY_CON_CATEGORY + " TEXT,"+ KEY_CON_CATEGORY_ID +" INTEGER,"
            + KEY_CON_EMAIL +" TEXT,"+ KEY_CREATED_AT + " DATETIME" + ")";



    public ContactDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // creating required tables
        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // on upgrade drop older tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);

        // create new tables
        onCreate(sqLiteDatabase);
    }


    /*
	 * Creating Category
	 */
    public long createCategory(CategoryModel categoryModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CAT_NAME, categoryModel.getCategory_name());
        values.put(KEY_CREATED_AT, ut.getDateTime());

        // insert row
        long cat_id = db.insert(TABLE_CATEGORY, null, values);
        Log.d("Category_ID", ""+cat_id);

        return cat_id;
    }


    /**
     * getting all Category
     * */
    public List<CategoryModel> getAllCategory() {
        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                CategoryModel catm = new CategoryModel();
                catm.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                catm.setCategory_name((c.getString(c.getColumnIndex(KEY_CAT_NAME))));
                catm.setCreatedat(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to category list
                categoryModelList.add(catm);
            } while (c.moveToNext());
        }

        return categoryModelList;
    }


    /*
	 * Deleting a Category
	 */
    public void deleteCategory(int cat_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, KEY_ID + " = ?",
                new String[] { String.valueOf(cat_id) });
    }

    /*
      * Updating a Category
      */
    public int updateCategory(CategoryModel categoryModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CAT_NAME, categoryModel.getCategory_name());

        // updating row
        return db.update(TABLE_CATEGORY, values, KEY_ID + " = ?",
                new String[] { String.valueOf(categoryModel.getId()) });
    }




}
