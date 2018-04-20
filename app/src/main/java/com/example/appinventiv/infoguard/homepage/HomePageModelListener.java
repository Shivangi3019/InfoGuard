package com.example.appinventiv.infoguard.homepage;

public interface HomePageModelListener {
    void onValidationFailed(String messae);
    void onValidationSucceded(int position, String category, String username, String dob, String email, String phoneNumber);
    void setDate(int day, int month, int year);
}