package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.utils.ActivityUtils;
import com.example.amitagarwal.applock.utils.ScreenMathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by amitagarwal on 1/3/15.
 */
public class PasswordView extends LinearLayout {


    private final static int[] numbersList = new int[]{1,2,3,4};
    private final static String[] coloursList = new String[]{"RED","GREEN","BLUE","YELLOW"};
    private int screenWidth;
    private int screenHeight;
    private int headerHeight ;
    private PasswordEvaluator passwordEvaluator;

    public PasswordView(Context context,PasswordEvaluator passwordEvaluator) {
        super(context);
        this.passwordEvaluator = passwordEvaluator;

        setOrientation(LinearLayout.VERTICAL);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(lp);

        ArrayList<Figure> figureList  = getPassword();
        headerHeight = ScreenMathUtils.dpToPx(20, context);
        screenWidth = ActivityUtils.getWidthInPx(context);
        screenHeight = ActivityUtils.getHeightInPx(context);
        RowLayout row1 = new RowLayout(context,screenHeight/3,screenWidth/3,figureList.get(0),figureList.get(1),figureList.get(2),passwordEvaluator);
        RowLayout row2 = new RowLayout(context,screenHeight/3,screenWidth/3,figureList.get(3),figureList.get(4),figureList.get(5),passwordEvaluator);
        RowLayout row3 = new RowLayout(context,screenHeight/3,screenWidth/3,figureList.get(6),figureList.get(7),figureList.get(8),passwordEvaluator);

        addView(row1);
        addView(row2);
        addView(row3);

    }

    private ArrayList<Figure> getPassword(){

        ArrayList<Figure> figureList = new ArrayList<>();
        Figure figure;
        for(int i=0;i<4;i++){
            figure = new Figure();
            figure.setNumber(numbersList[i]);
            figureList.add(figure);
        }
        //randomise so that each colour goes with unknown number
        Collections.shuffle(figureList);

        //add colors
        for(int i=0;i<4;i++){
            figureList.get(i).setColour(coloursList[i]);
        }

        //now fill the remaining 6 password figures
        Random random = new Random();
        int randomNumber;
        int randomColor;
        for(int i=0;i<6;i++){
            randomNumber = random.nextInt(4);
            randomColor = random.nextInt(4);
            figure = new Figure(numbersList[randomNumber],coloursList[randomColor]);
            figureList.add(figure);
        }

        return figureList;
    }

}
