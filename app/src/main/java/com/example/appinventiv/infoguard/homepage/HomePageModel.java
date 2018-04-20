package com.example.appinventiv.infoguard.homepage;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.widget.DatePicker;

import com.example.appinventiv.infoguard.data.DataManager;
import com.example.appinventiv.infoguard.pojo.CategoryData;
import com.example.appinventiv.infoguard.utils.AppMethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

class HomePageModel implements DatePickerDialog.OnDateSetListener{

    private HomePageModelListener homePageModelListener;

     HomePageModel(HomePageModelListener homePageModelListener){
        this.homePageModelListener=homePageModelListener;
    }

     private DataManager getDataManager() {
        return DataManager.getInstance();
    }

    /*
     * Method for check Validaions in case of data saving.
     */
     void checkValidations(String category, String username, String phoneNumber, String email, String dob) {
        if(username.equals("")){
            homePageModelListener.onValidationFailed("Enter valid Username");
        }else if(phoneNumber.equals("") || !AppMethods.get().isValidMobile(phoneNumber) || phoneNumber.length()!=10 ){
            homePageModelListener.onValidationFailed("Enter valid Phone Number ");
        }else if(email.equals("") || !AppMethods.get().isValidEmail(email)){
            homePageModelListener.onValidationFailed("Enter valid Email");
        }else if (getDataManager().checkEmail(email)){
            homePageModelListener.onValidationFailed("This Email already exists");
        } else if(dob.equals("")){
            homePageModelListener.onValidationFailed("Enter valid DOB");
        }else {
            if(getDataManager().getData(category, username, dob, email, phoneNumber)) {
                homePageModelListener.onValidationSucceded(-1, category, username, dob, email, phoneNumber);
            }
        }
    }

     ArrayList<CategoryData> addData() {
      return  getDataManager().addData();
    }

    /*
     * Method for check Validaions in case of data editing.
     */
     void checkValidationsForEdit(int position, String oldemail, String category, String username, String number, String newemail, String dob) {
        if(username.equals("")){
            homePageModelListener.onValidationFailed("Enter valid Username");
        }else if(number.equals("") || !AppMethods.get().isValidMobile(number) || number.length()!=10){
            homePageModelListener.onValidationFailed("Enter valid Phone Number ");
        }else if(newemail.equals("") || !AppMethods.get().isValidEmail(newemail) ){
            homePageModelListener.onValidationFailed("Enter valid Email");
        }else if(!Objects.equals(oldemail, newemail) && getDataManager().checkEmail(newemail)){
            homePageModelListener.onValidationFailed("This Email already exists");
        } else if(dob.equals("")){
            homePageModelListener.onValidationFailed("Enter valid DOB");
        }else {
            if (getDataManager().updateData(position, oldemail, category, username, dob, newemail, number)) {
                homePageModelListener.onValidationSucceded(position,category, username, dob, newemail, number);
            }
        }
    }

    /*
     * Method to make the listener null on activity destroyed.
     */
     void detachListener() {
        homePageModelListener=null;
    }

     void openDatePicker(FragmentActivity activity) {
         Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
         DatePickerDialog dialog = new DatePickerDialog(activity, this,
                 calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                 calendar.get(Calendar.DAY_OF_MONTH));
         dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
         dialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
         homePageModelListener.setDate(day,month,year);
    }
}