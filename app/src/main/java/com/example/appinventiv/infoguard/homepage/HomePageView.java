package com.example.appinventiv.infoguard.homepage;

import android.support.v4.app.Fragment;
import com.example.appinventiv.infoguard.pojo.CategoryData;
import java.util.ArrayList;

public interface HomePageView {

    void showDialog();
    void dismissDialog();
    void hideFAB();
    void showFAB();
    void hideRecyclerView();
    void showRecyclerView();
    void showToast(String message);
    void fillEditText();
    void setEditText();
    void addFragment(Fragment fragment, String category,String tag);
    void removeFragment();
    void addFragmentForEdit(Fragment fragment,String tag, int position, String category, String username, String number, String email, String dob);
    void addDataInRecycerView(ArrayList<CategoryData> dataArrayList);
    void notifyAdapterForUpdate(int position, String category, String username, String dob, String email, String phoneNumber);
    void notifyAdapterForSave(String category, String username, String dob, String email, String phoneNumber);
    void setTvDob(int day, int month, int year);
}
