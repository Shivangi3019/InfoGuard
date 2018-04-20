package com.example.appinventiv.infoguard.homepage;

import android.support.v4.app.FragmentActivity;

import com.example.appinventiv.infoguard.utils.AppConstants;

public class HomePagePresenter implements HomePageModelListener{

    private HomePageView view;
    private DataFragment dataFragment;
    private HomePageModel homePageModel;
    private String oldEmail;
    private int position;

     HomePagePresenter(HomePageView view){
        this.view=view;
        dataFragment=new DataFragment();
        homePageModel=new HomePageModel(this);
    }

    /*
     * Method to perform operation on clicking Floating Action Button of Activity.
     */
     void onFloatingButtonClicked() {
        view.showDialog();
    }

    /*
     * Method to perform operation on clicking Bank Category of Dialog.
     */
     void onTVBankClicked() {
        view.dismissDialog();
        view.hideFAB();
        view.hideRecyclerView();
         dataFragment=new DataFragment();
        view.addFragment(dataFragment,AppConstants.get().CATEGORY_BANK,AppConstants.get().FRAGMENT_TAG);
         checkPurpose(AppConstants.get().KEY_PURPOSE_SAVE);
    }

    /*
     * Method to perform operation on clicking Social Network Category of Dialog.
     */
     void onTVSocialNetworkClicked() {
        view.dismissDialog();
        view.hideFAB();
         view.hideRecyclerView();
         dataFragment=new DataFragment();
        view.addFragment(dataFragment,  AppConstants.get().CATEGORY_SOCIAL_NETWORK,AppConstants.get().FRAGMENT_TAG);
         view.setEditText();
         checkPurpose(AppConstants.get().KEY_PURPOSE_SAVE);
    }

    /*
     * Method to perform operation on clicking Email Category of Dialog.
     */
     void onTVEmailClicked() {
        view.dismissDialog();
        view.hideFAB();
         view.hideRecyclerView();
         dataFragment=new DataFragment();
        view.addFragment(dataFragment, AppConstants.get().CATEGORY_EMAIL,AppConstants.get().FRAGMENT_TAG);
         view.setEditText();
         checkPurpose(AppConstants.get().KEY_PURPOSE_SAVE);
    }

    /*
     * Method to perform operation on clicking Save Button of Fragment.
     */
    void onBTnSaveClicked(String purpose, String category, String username, String phoneNumber, String email, String dob) {
         if(purpose.equals(AppConstants.get().KEY_PURPOSE_SAVE)) {
             homePageModel.checkValidations(category, username, phoneNumber, email, dob);
         }
        else if(purpose.equals(AppConstants.get().KEY_PURPOSE_EDIT)){
             homePageModel.checkValidationsForEdit(position,oldEmail,category,username,phoneNumber,email,dob);
         }
    }

    /*
     * Method to perform action on Validation Failed.
     */
    @Override
    public void onValidationFailed(String message) {
         view.showToast(message);
    }

    /*
     * Method to perform action on Validation Succeded.
     */
    @Override
    public void onValidationSucceded(int position, String category, String username, String dob, String email, String phoneNumber) {
         if(position==-1){
             view.notifyAdapterForSave(category, username, dob, email, phoneNumber);
             view.removeFragment();
             view.showFAB();
             view.showRecyclerView();
         }
         else {
             view.notifyAdapterForUpdate(position,category, username, dob, email, phoneNumber);
             view.removeFragment();
             view.showFAB();
             view.showRecyclerView();
         }
    }

    @Override
    public void setDate(int day, int month, int year) {
        view.setTvDob(day,month,year);
    }

    void addDataInArraylist() {
         view.addDataInRecycerView(homePageModel.addData());

    }

     void onRowClicked(int position, String category, String username, String number, String email, String dob) {
        view.hideFAB();
        view.hideRecyclerView();
         dataFragment=new DataFragment();
        view.addFragmentForEdit(dataFragment,AppConstants.get().FRAGMENT_TAG,position,category,username,number,email,dob);
         view.fillEditText();
    }

      void checkPurpose(String purpose) {
         if(purpose.equals(AppConstants.get().KEY_PURPOSE_EDIT)) {
             view.fillEditText();
         }
         else {
             view.setEditText();
         }
    }

     void saveOldEmail(String email, int position) {
         oldEmail = email;
         this.position=position;
    }
    /*
     * Method for making view and model null on activity destroyed.
     */
     void detachView(){
        view = null;
        homePageModel.detachListener();
        homePageModel = null;
    }

     void onFragmentDestroyed() {
         view.removeFragment();
         view.showFAB();
         view.showRecyclerView();
    }

     void onEtDobClicked(FragmentActivity activity) {
         homePageModel.openDatePicker(activity);
    }
}