package com.example.amitagarwal.applock.views;

import android.content.Intent;
import android.widget.Toast;

import com.example.amitagarwal.applock.activity.ChangePasswordActivity;
import com.example.amitagarwal.applock.activity.PasswordBaseActivity;
import com.example.amitagarwal.applock.activity.SetPassFirstActivity;
import com.example.amitagarwal.applock.utils.Constants;
import com.example.amitagarwal.applock.utils.PasswordManager;

import java.util.ArrayList;

/**
 * Created by amitagarwal on 1/1/15.
 */
public class PasswordEvaluator {

    private ArrayList<Object> password;
    private ArrayList<Figure> passwordEntered = new ArrayList<>();
    private int savedPasswordLength;



    private PasswordBaseActivity passwordBaseActivity;
    public PasswordBaseActivity getPasswordBaseActivity() {
        return passwordBaseActivity;
    }
    public PasswordEvaluator(PasswordBaseActivity passwordBaseActivity) {
        this.passwordBaseActivity = passwordBaseActivity;
        password = PasswordManager.getSavedPassword();
        savedPasswordLength = password.size();
    }

    public boolean checkPassword(){
        Figure temp;
        Object obj;
        if(passwordEntered.size() == savedPasswordLength){

            boolean passwordCorrect = true;
            for(int i=0;i< savedPasswordLength;i++){
                temp = passwordEntered.get(i);
                obj = password.get(i);
                if(obj instanceof Integer){
                    int value = ((Integer) obj).intValue();
                    if(temp.getNumber() != value){
                        passwordCorrect = false;
                        return false;
//                        break;
                    }
                }else if(obj instanceof String){
                    String valString = obj.toString();
                    if(valString != temp.getColour()){
                        passwordCorrect = false;
                        return false;
//                        break;
                    }

                }
            }
            if(passwordCorrect){
                return true;
            }
        }
        return false;
    }

    public void updateAndCheckPassword(Figure figure){
        System.out.println(figure.toString());
        passwordBaseActivity.addStarsToPassword();
        passwordEntered.add(figure);
        if(checkPassword()){
            passwordBaseActivity.finish();
            if(passwordBaseActivity instanceof ChangePasswordActivity){
                Intent intent = new Intent(passwordBaseActivity, SetPassFirstActivity.class);
                passwordBaseActivity.startActivity(intent);
            }
            Toast.makeText(passwordBaseActivity,"Correct Password", Toast.LENGTH_LONG);
        }
    }

    public void cancelClicked() {
        //go home
        passwordBaseActivity.onBackPressed();
    }

    public void backClicked() {
        passwordBaseActivity.removeStarsFromPassword();
        if(passwordEntered.size() > 0)
            passwordEntered.remove(passwordEntered.size()-1);
    }

    public void changePasswordNextClicked() {
        if(checkPassword()){
            //this condition won't be used coz as the correct pwd is hit the activity disappears
            Intent intent = new Intent(passwordBaseActivity, SetPassFirstActivity.class);
            passwordBaseActivity.startActivity(intent);
            passwordBaseActivity.finish();
        }else{
            passwordEntered.clear();
            passwordBaseActivity.clearPassword();
            MyCustomAlertDialog dialog = new MyCustomAlertDialog(passwordBaseActivity);
            dialog.showErrorMessage("Incorrect Password");
        }
    }
}
