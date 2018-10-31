package com.rshtukaraxondevgroup.phototest.view.CustomView;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;

import com.rshtukaraxondevgroup.phototest.R;


public class DrawView extends View {
    private Point[] points = new Point[4];
    private int mGroupId = -1;
    private ArrayList<ColorBallModel> mBalls = new ArrayList<ColorBallModel>();
    private int mBallID = 0;
    private Paint mPaint;
    private Bitmap mImage;

    public DrawView(Context context, Bitmap image) {
        super(context);
        this.mImage = image;
        mPaint = new Paint();
        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mImage.getWidth() > mImage.getHeight()) {
            double f = getWidth() / (double) mImage.getWidth();
            mImage = Bitmap.createScaledBitmap(mImage, getWidth(), (int) (mImage.getHeight() * f), false);
        } else {
            double f = getHeight() / (double) mImage.getHeight();
            mImage = Bitmap.createScaledBitmap(mImage, getWidth(), (int) (mImage.getHeight() * f), false);
        }
        canvas.drawBitmap(mImage, 0, 0, null);

        if (points[3] == null) //point4 null when user did not touch and move on screen.
            return;

        int left, top, right, bottom;
        left = points[0].x;
        top = points[0].y;
        right = points[0].x;
        bottom = points[0].y;
        for (int i = 1; i < points.length; i++) {
            left = left > points[i].x ? points[i].x : left;
            top = top > points[i].y ? points[i].y : top;
            right = right < points[i].x ? points[i].x : right;
            bottom = bottom < points[i].y ? points[i].y : bottom;
        }

        //draw stroke
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorStroke));
        mPaint.setStrokeWidth(2);
        canvas.drawRect(left, top, right, bottom, mPaint);

        //fill the rectangle
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorRectangle));
        mPaint.setStrokeWidth(0);
        canvas.drawRect(left, top, right, bottom, mPaint);

        // draw the mBalls on the canvas
        for (int i = 0; i < mBalls.size(); i++) {
            ColorBallModel ball = mBalls.get(i);
            canvas.drawBitmap(ball.getBitmap(),
                    ball.getX() - mBalls.get(0).getWidthOfBall() / 2,
                    ball.getY() - mBalls.get(0).getWidthOfBall() / 2,
                    mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                if (points[0] == null) {
                    //initialize rectangle.
                    points[0] = new Point();
                    points[0].x = X;
                    points[0].y = Y;

                    points[1] = new Point();
                    points[1].x = X;
                    points[1].y = Y + 30;

                    points[2] = new Point();
                    points[2].x = X + 30;
                    points[2].y = Y + 30;

                    points[3] = new Point();
                    points[3].x = X + 30;
                    points[3].y = Y;

                    mBallID = 2;
                    mGroupId = 1;
                    // declare each ball with the ColorBallModel class
                    for (Point pt : points) {
                        mBalls.add(new ColorBallModel(getContext(), R.drawable.gray_circle, pt));
                    }
                } else {
                    //resize rectangle
                    mBallID = -1;
                    mGroupId = -1;
                    for (int i = mBalls.size() - 1; i >= 0; i--) {
                        ColorBallModel ball = mBalls.get(i);
                        // check if inside the bounds of the ball (circle)
                        // get the center for the ball
                        int centerX = ball.getX();
                        int centerY = ball.getY();
                        // calculate the radius from the touch to the center of the
                        // ball
                        double radCircle = Math
                                .sqrt((double) (((centerX - X) * (centerX - X)) + (centerY - Y)
                                        * (centerY - Y)));

                        if (radCircle < ball.getWidthOfBall()) {

                            mBallID = ball.getID();
                            if (mBallID == 1 || mBallID == 3) {
                                mGroupId = 2;
                            } else {
                                mGroupId = 1;
                            }
                            invalidate();
                            break;
                        }
                        invalidate();
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE: // touch drag with the ball
                if (mBallID > -1) {
                    // move the mBalls the same as the finger
                    mBalls.get(mBallID).setX(X);
                    mBalls.get(mBallID).setY(Y);

                    if (mGroupId == 1) {
                        mBalls.get(1).setX(mBalls.get(0).getX());
                        mBalls.get(1).setY(mBalls.get(2).getY());
                        mBalls.get(3).setX(mBalls.get(2).getX());
                        mBalls.get(3).setY(mBalls.get(0).getY());
                    } else {
                        mBalls.get(0).setX(mBalls.get(1).getX());
                        mBalls.get(0).setY(mBalls.get(3).getY());
                        mBalls.get(2).setX(mBalls.get(3).getX());
                        mBalls.get(2).setY(mBalls.get(1).getY());
                    }
                    invalidate();
                }
                break;
        }
        // redraw the canvas
        invalidate();
        return true;
    }

    public Bitmap save() {
        if (points[3] != null) {

            int left, top, right, bottom;
            left = points[0].x;
            top = points[0].y;
            right = points[0].x;
            bottom = points[0].y;
            for (int i = 1; i < points.length; i++) {
                left = left > points[i].x ? points[i].x : left;
                top = top > points[i].y ? points[i].y : top;
                right = right < points[i].x ? points[i].x : right;
                bottom = bottom < points[i].y ? points[i].y : bottom;
            }
            if (left < 0) {
                left = 0;
            }
            if (top < 0) {
                top = 0;
            }
            if (right > mImage.getWidth()) {
                right = mImage.getWidth();
            }
            if (bottom > mImage.getHeight()) {
                bottom = mImage.getHeight();
            }

            int height = bottom - top;
            int width = right - left;
            return Bitmap.createBitmap(mImage, left, top, width, height);

        } else {
            return mImage;
        }
    }
}
