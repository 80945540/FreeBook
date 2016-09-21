package com.lance.freebook.Dialog;

import android.view.View;

/**
 * Created by Administrator on 2015/10/20.
 */
public abstract class Mydialog_interface {
    public View.OnClickListener no(){
        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyno();
            }
        };
        return onClickListener;
    }
    public View.OnClickListener yes(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyyes();
            }
        };
        return onClickListener;
    }

    public  abstract void onMyyes();
    public abstract void onMyno();

}
