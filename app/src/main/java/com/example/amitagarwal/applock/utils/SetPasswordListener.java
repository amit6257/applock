package com.example.amitagarwal.applock.utils;

import android.content.Intent;
import android.widget.Toast;

import com.example.amitagarwal.applock.activity.AppsListMainActivity;
import com.example.amitagarwal.applock.activity.EnterPasswordBaseActivity;
import com.example.amitagarwal.applock.activity.SetPassConfirmationActivity;
import com.example.amitagarwal.applock.broadcastreceiver.*;
import com.example.amitagarwal.applock.views.Figure;
import com.example.amitagarwal.applock.views.MyCustomAlertDialog;
import com.example.amitagarwal.applock.views.MyCustomDialog;
import com.example.amitagarwal.applocks.R;

import java.util.ArrayList;

/**
 * Created by amitagarwal on 1/17/15.
 */
public class SetPasswordListener{

    private EnterPasswordBaseActivity enterPasswordBaseActivity;
    private ArrayList<EnterPasswordFigure> passwordEntered = new ArrayList<>();

    public SetPasswordListener(EnterPasswordBaseActivity enterPasswordBaseActivity) {
        this.enterPasswordBaseActivity = enterPasswordBaseActivity;
    }

    public void addFigure(EnterPasswordFigure figure) {
        passwordEntered.add(figure);
        enterPasswordBaseActivity.addStarsToPassword();
    }

    public void nextClicked() {
        if(passwordEntered.size() < PasswordManager.MINIMUM_PWD_LENGTH){
            clearPassword();
            MyCustomAlertDialog dialog = new MyCustomAlertDialog(enterPasswordBaseActivity);
            dialog.showErrorMessage("Sorry..Minimum password length is " + PasswordManager.MINIMUM_PWD_LENGTH);
            return;
        }
        AppLockContextCache.instatnce().putItem(PasswordManager.PASSWORD_CONFIGURED,passwordEntered);
        Intent confirmationActivity = new Intent(enterPasswordBaseActivity,SetPassConfirmationActivity.class);
        enterPasswordBaseActivity.startActivity(confirmationActivity);
        enterPasswordBaseActivity.finish();
    }

    public void backClicked() {
        enterPasswordBaseActivity.removeStarsFromPassword();
        if(passwordEntered.size() > 0)
            passwordEntered.remove(passwordEntered.size()-1);
    }
    public boolean savePassword(){
        ArrayList<EnterPasswordFigure> passFromLastScreen = (ArrayList<EnterPasswordFigure>) AppLockContextCache.instatnce().getItem(PasswordManager.PASSWORD_CONFIGURED);
        if(!MyStringUtils.isNullOrEmpty(passFromLastScreen)){
            if(doPasswordsMatch(passFromLastScreen)){
                StringBuilder passToSave = new StringBuilder();
                for(int i=0;i<passFromLastScreen.size();i++){
                    passToSave.append(passFromLastScreen.get(i).toString() + ";");
                }
                String passString = passToSave.toString().substring(0, passToSave.toString().length() - 1);
                MyPreferenceManager.instance().savePassword(passString);
                Toast.makeText(enterPasswordBaseActivity, "Password Saved :)" + passString, Toast.LENGTH_SHORT).show();
                return true;
            }else{
                clearPassword();
                String errorPasswordMismatch = enterPasswordBaseActivity.getResources().getString(R.string.wrong_password);
                MyCustomDialog.showErrorMessage(errorPasswordMismatch, enterPasswordBaseActivity);
            }
        }
        return false;
    }

    private boolean doPasswordsMatch(ArrayList<EnterPasswordFigure> passFromLastScreen) {
        if(passFromLastScreen.size() == passwordEntered.size()){
            for(int i=0;i<passFromLastScreen.size();i++){
                if(passFromLastScreen.get(i).isNumber() != passwordEntered.get(i).isNumber())
                    return false;
                if(passFromLastScreen.get(i).isColor() != passwordEntered.get(i).isColor())
                    return false;
                if(passFromLastScreen.get(i).isNumber()){
                    if(passFromLastScreen.get(i).getNumber() != passwordEntered.get(i).getNumber())
                        return false;
                }
                if(passFromLastScreen.get(i).isColor()){
                    if(passFromLastScreen.get(i).getColor() != passwordEntered.get(i).getColor())
                        return false;
                }
            }
            return true;
        }
        return false;
    }
    public EnterPasswordBaseActivity getEnterPasswordBaseActivity() {
        return enterPasswordBaseActivity;
    }

    public void doneClicked() {
        boolean pwdSaved = savePassword();
        if(pwdSaved){
            if(MyPreferenceManager.instance().isFirstTimeHomepageLoad()){
                Intent startMainActivity = new Intent(enterPasswordBaseActivity, AppsListMainActivity.class);
                enterPasswordBaseActivity.startActivity(startMainActivity);
                enterPasswordBaseActivity.finish();
            }
        }

    }
    private void clearPassword(){
        enterPasswordBaseActivity.clearPassword();
        passwordEntered.clear();
    }
}