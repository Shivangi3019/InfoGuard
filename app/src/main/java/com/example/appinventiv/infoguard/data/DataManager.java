package com.example.appinventiv.infoguard.data;

import com.example.appinventiv.infoguard.pojo.CategoryData;
import java.util.ArrayList;

public class DataManager {
    private DatabaseManager databaseManager;
    private static DataManager instance;

    /*
     * Making instance of DataManager class.
     */
    private DataManager() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            synchronized (DataManager.class){
                if (instance == null)
                    instance = new DataManager();
            }
        }
        return instance;
    }

    /*
     * Method call the method of DatabaseManager to add data in Database.
     * @Return boolean
     */
    public Boolean getData(String category,String name,String dob,String email, String phone){
      return   databaseManager.addInfo(category,name,dob,email,phone);
    }

    /*
     * Method call the method of DatabaseManager to check data already exists in Database.
     * @Return boolean
     */
    public Boolean checkEmail(String email){
      return databaseManager.CheckIsDataAlreadyInDBorNot(email);
    }

    /*
     * Method call the method of DatabaseManager to update data in Database.
     * @Return boolean
     */
    public Boolean updateData(int position, String oldemail, String category, String name, String dob, String email, String phone){
       return databaseManager.updateInfo(position,oldemail,category,name,dob,email,phone);
    }

    /*
     * Method call the method of DatabaseManager to retreive data in Database.
     * @Return boolean
     */
    public ArrayList<CategoryData> addData(){
        return databaseManager.retrieveInfo();
    }
}
