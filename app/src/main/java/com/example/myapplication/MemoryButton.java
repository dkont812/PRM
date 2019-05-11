package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.GridLayout;


public class MemoryButton extends android.support.v7.widget.AppCompatButton {

    protected int row;
    protected int column;
    protected int frontDrawableId;

    protected boolean isFlipped = false;
    protected boolean isMatched = false;

    protected Drawable front;
    protected Drawable back;


    public boolean isMatched() {
        return isMatched;
    }


    public void setMatched(boolean matched) {
        isMatched = matched;
    }


    public int getFrontDrawableId() {
        return frontDrawableId;
    }


    public void flip(){
        if(isMatched)
            return;
        if(isFlipped){
            setBackground(back);
            isFlipped = false;
        } else {
            setBackground(front);
            isFlipped = true;
        }
    }


    @SuppressLint("RestrictedApi")
    public MemoryButton(Context context, int r, int c,int cellSizePx, int frontImageDrawableId){
        super(context);
        row = r;
        column = c;
        frontDrawableId = frontImageDrawableId;

        front = AppCompatDrawableManager.get().getDrawable(context,frontImageDrawableId);
        back = AppCompatDrawableManager.get().getDrawable(context, R.drawable.unknown);

        setBackground(back);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));
        tempParams.width= cellSizePx;
        tempParams.height= cellSizePx;
        setLayoutParams(tempParams);
    }
}
