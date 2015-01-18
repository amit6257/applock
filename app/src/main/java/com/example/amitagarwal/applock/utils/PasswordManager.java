package com.example.amitagarwal.applock.utils;

import com.example.amitagarwal.applock.views.Figure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by amitagarwal on 1/10/15.
 */
public class PasswordManager {

    private static final int OBJECT_TYPE_COUNT = 10;
    private static final int NO_OF_COLOURS = 4;
    private static final int NO_OF_INTEGERS = 6;
    int[] numbersList = new int[]{0,1, 2, 3, 4, 5};
    private static String[] coloursList = new String[]{"RED", "GREEN", "BLUE", "YELLOW"};
    public static final String PASSWORD_CONFIGURED = "PASSWORD_CONFIGURED";


    public static ArrayList<Object> getSavedPassword(){

        ArrayList<Object> password = new ArrayList<>();
        password.add(Integer.parseInt("4"));
        password.add(Integer.parseInt("2"));
        password.add("BLUE");
        password.add("GREEN");
        return password;
    }

    public static ArrayList<Figure> getPassword() {


        ArrayList<Figure> figureList = new ArrayList<>();
        //find all integers in password and create two instances of them
        int digitsFreqInPwd[] = getDigitFrequencyInPwd();
        Figure figure;
        //the number list now has 6 digits 0,1, 2, 3, 4, 5
        for (int i = 0; i < 6; i++) {
            if(digitsFreqInPwd[i] > 0){
                //adding first instance of this digit in password view
                figure = new Figure();
                figure.setNumber(i);
                figureList.add(figure);

                //adding 2nd instance instance
                figure = new Figure();
                figure.setNumber(i);
                figureList.add(figure);
            }
        }
        //randomise
        Collections.shuffle(figureList);

        //fill in the rest of remaining numbers,we can use multiple strategies
        fillInRemainingDigitsStrategy1(figureList);

        ///////////////***** now we have figureList with integers set...we only need to set colours *****//////////////////

        fillInColoursStrategy1(figureList);
        //shuffle again
        Collections.shuffle(figureList);

        return figureList;
    }

    private static void fillInColoursStrategy1(ArrayList<Figure> figureList) {
        //first fill in 4 colours
        for(int i=0;i<PasswordManager.NO_OF_COLOURS;i++){
            figureList.get(i).setColour(coloursList[i]);
        }

        //randomise the remaining color positions (truly random)
        Random random = new Random();
        int randomColour;
        for (int i = PasswordManager.NO_OF_COLOURS; i < figureList.size(); i++) {
            randomColour = random.nextInt(PasswordManager.NO_OF_COLOURS);
            figureList.get(i).setColour(coloursList[randomColour]);
        }
    }

    private static void fillInRemainingDigitsStrategy1(ArrayList<Figure> figureList) {

        //add any number randomly
        Random random = new Random();
        int randomNumber;
        Figure figure;
        for (int i = figureList.size(); i < PasswordManager.OBJECT_TYPE_COUNT; i++) {
            randomNumber = random.nextInt(6);
            figure = new Figure();
            figure.setNumber(randomNumber);
            figureList.add(figure);
        }
    }

    public static int[] getDigitFrequencyInPwd() {

        ArrayList<Object> password = getSavedPassword();
        //we have only 6 digits now viz: 0,1,2,3,4,5
        int digitsFreqInPwd[] = new int[6];
        for(int i=0;i<6;i++){
            digitsFreqInPwd[i] = 0;
        }
        Object obj;
        for(int i = 0;i<password.size();i++){
            obj = password.get(i);
            int digit;
            if(obj instanceof Integer){
                digit = ((Integer) obj).intValue();
                digitsFreqInPwd[digit]++;
            }
        }

        return digitsFreqInPwd;
    }

    public static ArrayList<EnterPasswordFigure> getFigureListForConfigurePassword() {

        ArrayList<EnterPasswordFigure> figureList = new ArrayList<>();
        EnterPasswordFigure figure;
        for(int i=0;i<NO_OF_INTEGERS;i++){
            figure = new EnterPasswordFigure();
            figure.setNumber(i);
            figureList.add(figure);
        }
        for(int i=0;i<NO_OF_COLOURS;i++){
            figure = new EnterPasswordFigure();
            figure.setColor(coloursList[i]);
            figureList.add(figure);
        }
        return figureList;
    }
}
