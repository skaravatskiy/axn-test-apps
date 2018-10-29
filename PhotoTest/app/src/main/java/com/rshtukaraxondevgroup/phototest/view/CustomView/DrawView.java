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
    private int groupId = -1;
    private ArrayList<ColorBallModel> balls = new ArrayList<ColorBallModel>();
    private int balID = 0;
    private Paint paint;
    private Bitmap image;

    public DrawView(Context context, Bitmap image) {
        super(context);
        this.image = image;
        paint = new Paint();
        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (image.getWidth() > image.getHeight()) {
            double f = getWidth() / (double) image.getWidth();
            image = Bitmap.createScaledBitmap(image, getWidth(), (int) (image.getHeight() * f), false);
        } else {
            double f = getHeight() / (double) image.getHeight();
            image = Bitmap.createScaledBitmap(image, getWidth(), (int) (image.getHeight() * f), false);
        }
        canvas.drawBitmap(image, 0, 0, null);

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
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorStroke));
        paint.setStrokeWidth(2);
        canvas.drawRect(left, top, right, bottom, paint);

        //fill the rectangle
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorRectangle));
        paint.setStrokeWidth(0);
        canvas.drawRect(left, top, right, bottom, paint);

        // draw the balls on the canvas
        for (int i = 0; i < balls.size(); i++) {
            ColorBallModel ball = balls.get(i);
            canvas.drawBitmap(ball.getBitmap(),
                    ball.getX() - balls.get(0).getWidthOfBall() / 2,
                    ball.getY() - balls.get(0).getWidthOfBall() / 2,
                    paint);
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

                    balID = 2;
                    groupId = 1;
                    // declare each ball with the ColorBallModel class
                    for (Point pt : points) {
                        balls.add(new ColorBallModel(getContext(), R.drawable.gray_circle, pt));
                    }
                } else {
                    //resize rectangle
                    balID = -1;
                    groupId = -1;
                    for (int i = balls.size() - 1; i >= 0; i--) {
                        ColorBallModel ball = balls.get(i);
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

                            balID = ball.getID();
                            if (balID == 1 || balID == 3) {
                                groupId = 2;
                            } else {
                                groupId = 1;
                            }
                            invalidate();
                            break;
                        }
                        invalidate();
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE: // touch drag with the ball
                if (balID > -1) {
                    // move the balls the same as the finger
                    balls.get(balID).setX(X);
                    balls.get(balID).setY(Y);

                    if (groupId == 1) {
                        balls.get(1).setX(balls.get(0).getX());
                        balls.get(1).setY(balls.get(2).getY());
                        balls.get(3).setX(balls.get(2).getX());
                        balls.get(3).setY(balls.get(0).getY());
                    } else {
                        balls.get(0).setX(balls.get(1).getX());
                        balls.get(0).setY(balls.get(3).getY());
                        balls.get(2).setX(balls.get(3).getX());
                        balls.get(2).setY(balls.get(1).getY());
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
            if (right > image.getWidth()) {
                right = image.getWidth();
            }
            if (bottom > image.getHeight()) {
                bottom = image.getHeight();
            }

            int height = bottom - top;
            int width = right - left;
            return Bitmap.createBitmap(image, left, top, width, height);

        } else {
            return image;
        }
    }
}
