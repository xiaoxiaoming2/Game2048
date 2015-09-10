package com.xiaoxiaoming.game2048;

import android.app.Fragment;
import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/9/9.
 */
public class Card extends FrameLayout {

    public Card(Context context) {
        super(context);
        label = new TextView(context);
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff);
        label.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);


        setNumber(0);
    }

    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        if (number == 0)
            label.setText("");
        else
            label.setText(number + "");
    }

    private TextView label;

    public boolean equals(Card o) {
        return getNumber() == o.getNumber();
    }
}
