package com.example.appinventiv.infoguard.homepage;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.appinventiv.infoguard.R;
import com.example.appinventiv.infoguard.interfaces.OnItemClickListener;
import com.example.appinventiv.infoguard.pojo.CategoryData;
import com.example.appinventiv.infoguard.utils.AppConstants;
import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity implements HomePageView,View.OnClickListener,OnItemClickListener {

    private HomePagePresenter homePagePresenter;
    private Dialog dialog;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private ArrayList<CategoryData> arrayList ;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        floatingActionButton=findViewById(R.id.floating_action_button);
        homePagePresenter = new HomePagePresenter(this);
        floatingActionButton.setOnClickListener(this);
        recyclerView=findViewById(R.id.recycler_view);
        arrayList = new ArrayList<>();
        homePagePresenter.addDataInArraylist();
    }

    /*
     * Method to open Dialog.
     */
    @Override
    public void showDialog() {
        dialog = new Dialog(this,R.style.Theme_AppCompat_Light_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_category);
        TextView tvBank = dialog.findViewById(R.id.tv_bank);
        TextView tvEmail = dialog.findViewById(R.id.tv_email);
        TextView tvSocialNetwork = dialog.findViewById(R.id.tv_social_network);
        tvBank.setOnClickListener(this);
        tvEmail.setOnClickListener(this);
        tvSocialNetwork.setOnClickListener(this);
        dialog.show();
    }

    /*
     * Method to close Dialog.
     */
    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    /*
     * Method to make FloatingActionButton invisible.
     */
    @Override
    public void hideFAB() {
        floatingActionButton.setVisibility(View.GONE);
    }

    /*
     * Method to make FloatingActionButton visible.
     */
    @Override
    public void showFAB() {
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    /*
     * Method to make Recycler View invisible.
     */
    @Override
    public void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
    }

    /*
     * Method to make Recycler View visible.
     */
    @Override
    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    /*
     * Method to show Toast.
     */
    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    /*
     * Method to pre fill the Edittexts in case of data Editing.
     */
    @Override
    public void fillEditText() {
        Fragment fragment = getCurrentFragment();
        if(fragment!=null) {
            ((DataFragment) fragment).fillEditText();
        }

    }

    /*
    * Method to empty the Edittexts in case of data saving.
    */
    @Override
    public void setEditText() {
        Fragment fragment = getCurrentFragment();
        if(fragment!=null){
            ((DataFragment)fragment).setEditText();
        }
    }

    /*
     * Method to get the current Fragment.
     */
    private Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
       // String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() -1).getName();
        return fragmentManager.findFragmentByTag(AppConstants.get().FRAGMENT_TAG);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floating_action_button:
                homePagePresenter.onFloatingButtonClicked();
                break;

            case R.id.tv_bank:
                homePagePresenter.onTVBankClicked();
                break;

            case R.id.tv_email:
                homePagePresenter.onTVEmailClicked();
                break;

            case R.id.tv_social_network:
                homePagePresenter.onTVSocialNetworkClicked();
                break;
        }
    }

    /*
     * Method to add Fragment in case data saving.
     */
    @Override
    public void addFragment(Fragment fragment, String category,String tag){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment,tag).addToBackStack(AppConstants.get().FRAGMENT_TAG).commit();
        Bundle bundle =  new Bundle();
        bundle.putString(AppConstants.get().KEY_CATEGORY,category);
        bundle.putString(AppConstants.get().KEY_PURPOSE,AppConstants.get().KEY_PURPOSE_SAVE);
        fragment.setArguments(bundle);
    }

    /*
    * Method to remove Fragment.
    */
    @Override
    public void removeFragment(){
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fl_container)).commitAllowingStateLoss();
    }

    /*
    * Method to add Fragment in case data editing.
    */
    @Override
    public void addFragmentForEdit(Fragment fragment,String tag, int position, String category, String username, String number, String email, String dob){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment,tag).addToBackStack(AppConstants.get().FRAGMENT_TAG).commit();
        Bundle bundle =  new Bundle();
        bundle.putString(AppConstants.get().KEY_CATEGORY,category);
        bundle.putString(AppConstants.get().KEY_USERNAME,username);
        bundle.putString(AppConstants.get().KEY_NUMBER,number);
        bundle.putString(AppConstants.get().KEY_EMAIL,email);
        bundle.putString(AppConstants.get().KEY_DOB,dob);
        bundle.putString(AppConstants.get().KEY_PURPOSE,AppConstants.get().KEY_PURPOSE_EDIT);
        bundle.putInt(AppConstants.get().KEY_POSITION,position);
        fragment.setArguments(bundle);
    }

    /*
    * Method to add data in Recycler View.
    */
    @Override
    public void addDataInRecycerView(ArrayList<CategoryData> dataArrayList) {
        arrayList=dataArrayList;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(arrayList, this, this);
        recyclerView.setAdapter(categoryAdapter);
    }

    /*
     * Method to notify Adapter in case of data editing.
     */
    @Override
    public void notifyAdapterForUpdate(int position, String category, String username, String dob, String email, String phoneNumber) {
        CategoryData categoryData = new CategoryData();
        arrayList.remove(position);
        categoryData.setCategory(category);
        categoryData.setUsername(username);
        categoryData.setNumber(phoneNumber);
        categoryData.setEmail(email);
        categoryData.setDob(dob);
        arrayList.add(position,categoryData);
        categoryAdapter.notifyDataSetChanged();
    }

    /*
    * Method to notify Adapter in case of data saving.
    */
    @Override
    public void notifyAdapterForSave(String category, String username, String dob, String email, String phoneNumber) {
        CategoryData categoryData = new CategoryData();
        categoryData.setCategory(category);
        categoryData.setUsername(username);
        categoryData.setNumber(phoneNumber);
        categoryData.setEmail(email);
        categoryData.setDob(dob);
        arrayList.add(categoryData);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void setTvDob(int day, int month, int year) {
        Fragment fragment = getCurrentFragment();
        if(fragment!=null){
            ((DataFragment)fragment).setEditText();
        }
    }

    /*
     * Click Listner on recycler view's row.
     */
    @Override
    public void onItemClick(View view, int position, String category, String username, String number, String email, String dob) {
        homePagePresenter.onRowClicked(position,category,username,number,email,dob);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePagePresenter.detachView();
        homePagePresenter=null;
    }
}