package com.example.appinventiv.infoguard.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.appinventiv.infoguard.pojo.CategoryData;
import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static  DatabaseManager instance ;
    static DatabaseManager getInstance() {
       return instance;
    }
    public static void init( Context context) {
        instance= new DatabaseManager(context);
    }
    private static final String DATABASE_NAME = "DatabaseQuery";
    private static final String TABLE_NAME = "PersonInfo";
    private static final String COLUMN_NAME = "UserName";
    private static final String COLUMN_DOB = "DOB";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_PHONE = "Phone";
    private static final String COLUMN_CATEGORY = "Category";


    private DatabaseManager(Context context) {
       super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_STUDENTINFO_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_CATEGORY + " TEXT," + COLUMN_NAME + " TEXT,"
                + COLUMN_DOB + " TEXT," + COLUMN_EMAIL +" TEXT,"+ COLUMN_PHONE +"" +
                " INTEGER"+ ")";
        sqLiteDatabase.execSQL(CREATE_STUDENTINFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /*
     * Method for add data in Database.
     */
     Boolean addInfo(String category,String name,String dob,String email, String phone){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DOB,dob);
        values.put(COLUMN_EMAIL,email);
        values.put(COLUMN_PHONE,phone);
        sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return true;
    }

    /*
     * Method for Update the data in Database.
     */
     Boolean updateInfo(int position, String oldemail, String category, String name, String dob, String newemail, String phone){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DOB,dob);
        values.put(COLUMN_EMAIL,newemail);
        values.put(COLUMN_PHONE,phone);
        sqLiteDatabase.update(TABLE_NAME, values, COLUMN_EMAIL + " = ?",
                new String[] { String.valueOf(oldemail) });
        return true;
    }

    /*
     * Method for checking whether the data already exists in Database
     * by making the query on Email.
     */
     boolean CheckIsDataAlreadyInDBorNot(String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String count = "SELECT count(*) FROM " +TABLE_NAME;
        Cursor mcursor = sqLiteDatabase.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0) {
            String Query = "Select * from " + TABLE_NAME + " where " + COLUMN_EMAIL + " = '" + email+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
            if (cursor.getCount() <= 0) {
                cursor.close();
                return false;
            }
            cursor.close();
            return true;
        }
        return false;
    }

    /*
     * Method for retrieving all the data from Database by making the query on Email.
     */
     ArrayList<CategoryData> retrieveInfo(){
        ArrayList<CategoryData> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(TABLE_NAME,new String[]{COLUMN_CATEGORY,COLUMN_NAME,COLUMN_DOB,COLUMN_EMAIL
                ,COLUMN_PHONE},null,null,null,null,null);
        if (cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                CategoryData categoryData = new CategoryData();
                categoryData.setCategory(cursor.getString(cursor.getColumnIndex("Category")));
                categoryData.setUsername(cursor.getString(cursor.getColumnIndex("UserName")));
                categoryData.setNumber(cursor.getString(cursor.getColumnIndex("Phone")));
                categoryData.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
                categoryData.setDob(cursor.getString(cursor.getColumnIndex("DOB")));
                arrayList.add(categoryData);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }
}