package com.siti.clientvisit.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.siti.clientvisit.R;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //就啊索朗多吉法拉第解放了空间啊了
        //到风口浪尖撒了点附近拉开距离
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean getIsRememberPassword() {
        return false;
    }

    @Override
    public boolean getIsAutoLogin() {
        return false;
    }

    @Override
    public void clearUserName() {

    }

    @Override
    public void clearPassword() {

    }

    @Override
    public void setRememberPassword(boolean is) {

    }

    @Override
    public void setAutoLogin(boolean is) {

    }

    @Override
    public void startProgress() {

    }

    @Override
    public void stopProgress() {

    }
}
