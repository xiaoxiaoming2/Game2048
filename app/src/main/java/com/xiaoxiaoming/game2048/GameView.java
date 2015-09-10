package com.xiaoxiaoming.game2048;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/9.
 */
public class GameView extends GridLayout {
    public GameView(Context context) {

        super(context);
        gameView=this;
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gameView=this;
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gameView=this;
        initGameView();
    }

    private TextView score;

    private void initGameView() {
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX > 10)
                                moveRight();
                            else if (offsetX < -10)
                                moveLeft();
                        } else {
                            if (offsetY > 10)
                                moveDown();
                            else if (offsetY < -10)
                                moveUp();
                        }

                }
                return true;
            }
        });


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cardWidth = (Math.min(w, h) - 10) / 4;

        addCards(cardWidth, cardWidth);
        startGame();
    }

    private int cardWidth;

    private void addCards(int cardWidth, int cardHeiht) {
        Card c;
        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j) {
                c = new Card(getContext());
                c.setNumber(0);
                addView(c, cardWidth, cardHeiht);
                matrix[i][j] = c;
            }
    }

    private Card[][] matrix = new Card[4][4];

    private void moveUp() {
        for (int j = 0; j < 4; ++j)
            for (int i = 0; i < 4; ++i){
                for(int k=i+1;k<4;++k){
                    if(matrix[k][j].getNumber()>0){
                        if(matrix[i][j].getNumber()==0){
                            matrix[i][j].setNumber(matrix[k][j].getNumber());
                            matrix[k][j].setNumber(0);
                            --i;
                            break;
                        }
                        else if(matrix[k][j].getNumber()==matrix[i][j].getNumber()){
                            MainActivity.getInstance().addScore(matrix[i][j].getNumber());
                            matrix[i][j].setNumber(matrix[i][j].getNumber()*2);
                            matrix[k][j].setNumber(0);
                            break;
                        }

                    }
                }
            }

        addRandomDot();
    }

    private List<Point> emptyPoints = new ArrayList<>();

    private void moveDown() {
        for (int j = 3; j>=0 ; --j)
            for (int i = 3; i>=0 ; --i){
                for(int k=i-1;k>=0;--k){
                    if(matrix[k][j].getNumber()>0){
                        if(matrix[i][j].getNumber()==0){
                            matrix[i][j].setNumber(matrix[k][j].getNumber());
                            matrix[k][j].setNumber(0);
                            ++i;
                            break;
                        }
                        else if(matrix[k][j].getNumber()==matrix[i][j].getNumber()){
                            MainActivity.getInstance().addScore(matrix[i][j].getNumber());
                            matrix[i][j].setNumber(matrix[i][j].getNumber()*2);
                            matrix[k][j].setNumber(0);
                            break;
                        }

                    }
                }
            }
        addRandomDot();
    }

    private void moveLeft() {
        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j){
                for(int k=j+1;k<4;++k){
                    if(matrix[i][k].getNumber()>0){
                        if(matrix[i][j].getNumber()==0){
                            matrix[i][j].setNumber(matrix[i][k].getNumber());
                            matrix[i][k].setNumber(0);
                            --j;
                            break;
                        }
                        else if(matrix[i][k].getNumber()==matrix[i][j].getNumber()){
                            MainActivity.getInstance().addScore(matrix[i][j].getNumber());
                            matrix[i][j].setNumber(matrix[i][j].getNumber()*2);
                            matrix[i][k].setNumber(0);
                            break;
                        }

                    }
                }
            }
                addRandomDot();
    }

    private void moveRight() {
        for (int i = 0; i < 4; ++i)
            for (int j = 3; j >=0; --j){
                for(int k=j-1;k>=0;--k){
                    if(matrix[i][k].getNumber()>0){
                        if(matrix[i][j].getNumber()==0){
                            matrix[i][j].setNumber(matrix[i][k].getNumber());
                            matrix[i][k].setNumber(0);
                            ++j;
                            break;
                        }
                        else if(matrix[i][k].getNumber()==matrix[i][j].getNumber()){
                            MainActivity.getInstance().addScore(matrix[i][j].getNumber());
                            matrix[i][j].setNumber(matrix[i][j].getNumber()*2);
                            matrix[i][k].setNumber(0);
                            break;
                        }

                    }
                }
            }
        addRandomDot();
    }


    private void addRandomDot() {

        emptyPoints.clear();
        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j) {
                if(matrix[i][j].getNumber()>=2048) {
                    win();
                    return;
                }
                if (matrix[i][j].getNumber() <= 0)
                    emptyPoints.add(new Point(i, j));
            }
        if (emptyPoints.size() == 0) {
            lose();
            return;
        }


        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        matrix[p.x][p.y].setNumber(Math.random() > 0.1 ? 2 : 4);
    }

    private void win() {
        Toast.makeText(getContext(),"You Win!",Toast.LENGTH_SHORT).show();
    }

    public void startGame() {
        MainActivity.getInstance().initScore();
        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j)
                matrix[i][j].setNumber(0);

        addRandomDot();
        addRandomDot();
    }

    private void lose() {
        Toast.makeText(getContext(), "You Lose!", Toast.LENGTH_SHORT).show();
    }

    private static GameView gameView=null;
    public static GameView getGameView(){
        return gameView;
    }
}
