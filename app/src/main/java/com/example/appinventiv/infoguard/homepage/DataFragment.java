package com.example.appinventiv.infoguard.homepage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.appinventiv.infoguard.R;
import com.example.appinventiv.infoguard.pojo.CategoryData;
import com.example.appinventiv.infoguard.utils.AppConstants;
import java.util.ArrayList;

public class DataFragment extends Fragment implements HomePageView {

    private Button btnSave;
    private EditText etUsername,etPhoneNumber,etEmail;
    private TextView tvDOB;
    private Spinner spinnerNumber;
    private HomePagePresenter homePagePresenter;

    public DataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        btnSave=view.findViewById(R.id.btn_save);
        etUsername=view.findViewById(R.id.et_username);
        etPhoneNumber=view.findViewById(R.id.et_phone_number);
        etEmail=view.findViewById(R.id.et_email);
        tvDOB=view.findViewById(R.id.et_dob);
        spinnerNumber=view.findViewById(R.id.spinner_number);
        homePagePresenter = new HomePagePresenter(this);
        homePagePresenter.checkPurpose(getArguments().getString(AppConstants.get().KEY_PURPOSE));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                homePagePresenter.onBTnSaveClicked(getArguments().getString(AppConstants.get().KEY_PURPOSE),getArguments().getString(AppConstants.get().KEY_CATEGORY), etUsername.getText().toString(),etPhoneNumber.getText().toString(), etEmail.getText().toString(), tvDOB.getText().toString());
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePagePresenter.onEtDobClicked(getActivity());
            }
        });
        return view;
    }

    /*
    * Method to open Dialog.
    */
    @Override
    public void showDialog() {
        ((HomePageActivity)getActivity()).showDialog();
    }

    /*
    * Method to close Dialog.
    */
    @Override
    public void dismissDialog() {
        ((HomePageActivity)getActivity()).dismissDialog();
    }

    /*
     * Method to make FloatingActionButton invisible.
     */
    @Override
    public void hideFAB() {
        ((HomePageActivity)getActivity()).hideFAB();
    }

    /*
     * Method to make FloatingActionButton visible.
     */
    @Override
    public void showFAB() {
        ((HomePageActivity)getActivity()).showFAB();
    }

    /*
     * Method to make Recycler View invisible.
     */
    @Override
    public void hideRecyclerView() {
        ((HomePageActivity)getActivity()).hideRecyclerView();
    }

    /*
     * Method to make Recycler View visible.
     */
    @Override
    public void showRecyclerView() {
        ((HomePageActivity)getActivity()).showRecyclerView();
    }

    /*
     * Method to show Toast.
     */
    @Override
    public void showToast(String message) {
        ((HomePageActivity)getActivity()).showToast(message);
    }

    /*
     * Method to pre fill the Edittexts in case of data Editing.
     */
    @Override
    public void fillEditText() {
        etUsername.setText(getArguments().getString(AppConstants.get().KEY_USERNAME));
        etPhoneNumber.setText(getArguments().getString(AppConstants.get().KEY_NUMBER));
        etEmail.setText(getArguments().getString(AppConstants.get().KEY_EMAIL));
        tvDOB.setText(getArguments().getString(AppConstants.get().KEY_DOB));
        btnSave.setText(R.string.text_update);
        homePagePresenter.saveOldEmail(getArguments().getString(AppConstants.get().KEY_EMAIL),getArguments().getInt(AppConstants.get().KEY_POSITION));
    }

    /*
     * Method to empty the Edittexts in case of data saving.
     */
    public void setEditText() {
        etUsername.setText("");
        etPhoneNumber.setText("");
        etEmail.setText("");
        tvDOB.setText("");
        btnSave.setText(R.string.text_save);
    }

    /*
    * Method to add Fragment in case data saving.
    */
    @Override
    public void addFragment(Fragment fragment, String category,String tag) {
        ((HomePageActivity)getActivity()).addFragment(fragment,category,tag);
    }

    /*
    * Method to remove Fragment.
    */
    @Override
    public void removeFragment() {
        ((HomePageActivity)getActivity()).removeFragment();
    }

    /*
    * Method to add Fragment in case data editing.
    */
    @Override
    public void addFragmentForEdit(Fragment fragment, String tag,int position, String category, String username, String number, String email, String dob) {
        ((HomePageActivity)getActivity()).addFragmentForEdit(fragment,tag,position,category,username,number,email,dob);
    }

    /*
    * Method to add data in Recycler View.
    */
    @Override
    public void addDataInRecycerView(ArrayList<CategoryData> dataArrayList) {
        ((HomePageActivity)getActivity()).addDataInRecycerView(dataArrayList);
    }

    /*
    * Method to notify Adapter in case of data editing.
    */
    @Override
    public void notifyAdapterForUpdate(int position, String category, String username, String dob, String email, String phoneNumber) {
        ((HomePageActivity)getActivity()).notifyAdapterForUpdate(position, category,username,dob,email,phoneNumber);
    }

    /*
    * Method to notify Adapter in case of data saving.
    */
    @Override
    public void notifyAdapterForSave(String category, String username, String dob, String email, String phoneNumber) {
        ((HomePageActivity)getActivity()).notifyAdapterForSave(category,username,dob,email,phoneNumber);
    }

    @Override
    public void setTvDob(int day, int month, int year) {
        tvDOB.setText(day+"/"+month+"/"+year);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homePagePresenter.onFragmentDestroyed();
    }
}