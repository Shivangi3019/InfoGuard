package com.example.appinventiv.infoguard.interfaces;

import android.view.View;

public interface OnItemClickListener {
    public void onItemClick(View view, int position, String category, String username, String number, String email, String dob);
}
