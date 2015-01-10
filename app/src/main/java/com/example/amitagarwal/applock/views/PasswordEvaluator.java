package com.example.amitagarwal.applock.views;

import android.widget.Toast;

import com.example.amitagarwal.applock.activity.PasswordScreen;
import com.example.amitagarwal.applock.utils.Constants;
import com.example.amitagarwal.applock.utils.PasswordManager;

import java.util.ArrayList;

/**
 * Created by amitagarwal on 1/1/15.
 */
public class PasswordEvaluator {

    ArrayList<Object> password;
    private ArrayList<Figure> passwordEntered = new ArrayList<>();
    private PasswordScreen passwordScreen;

    public PasswordEvaluator(PasswordScreen passwordScreen) {
        this.passwordScreen = passwordScreen;
        password = PasswordManager.getSavedPassword();
    }

    public void updateAndCheckPassword(Figure figure){
        System.out.println(figure.toString());
        passwordEntered.add(figure);
        Figure temp;
        Object obj;
        if(passwordEntered.size() == Constants.PASSWORD_LENGTH){

            boolean passwordCorrect = true;
            for(int i=0;i< Constants.PASSWORD_LENGTH;i++){
                temp = passwordEntered.get(i);
                obj = password.get(i);
                if(obj instanceof Integer){
                    int value = ((Integer) obj).intValue();
                    if(temp.getNumber() != value){
                        passwordCorrect = false;
                        break;
                    }
                }else if(obj instanceof String){
                    String valString = obj.toString();
                    if(valString != temp.getColour()){
                        passwordCorrect = false;
                        break;
                    }

                }
            }
            if(passwordCorrect){
                passwordScreen.finish();
                Toast.makeText(passwordScreen,"Correct Password", Toast.LENGTH_LONG);
            }


        }
    }

    public void onBackPressed(){
        if(passwordEntered.size() > 0){
            passwordEntered.remove(passwordEntered.size() - 1);
        }
    }

    public void cancelClicked() {
        //go to home
    }

    public void backClicked() {
        //change ui + additional things
    }
}
