package com.example.areslauncher;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * listen to the user swipe and does the math of it.
 */
public class SwipeListener implements View.OnTouchListener {
    //VARIABLES
    private final GestureDetector gestureDetector;

    //CONSTRUCTOR

    /**
     * Initialize the variables
     * @param context Context
     */
    public SwipeListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    //OVERRIDE METHODS
    /**
     * Returns when the view has been touched.
     * @param v View
     * @param event MotionEvent
     * @return boolean
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    //INNER CLASSES
    /**
     * Private class that extends from GestureDetector.SimpleOnGestureListener and makes the math of the swipe.
     */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener{
        //VARIABLES-CONSTANTS
        private static final int SWIPE_THRESHOLD = 150;
        private static final int SWIPE_VELOCITY_THRESHOLD = 150;

        /**
         * Detects when is pressed
         * @param e MotionEvent
         * @return boolean
         */
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        /**
         * Calculates when the swipe is right and calls the proper methods
         * @param e1 MotionEvent
         * @param e2 MotionEvent
         * @param velocityX float
         * @param velocityY float
         * @return boolean
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                //Differences between the Y start axis and the end axis
                float diffY = e2.getY() - e1.getY();
                //Differences between the X start axis and the end axis
                float diffX = e2.getX() - e1.getX();
                //When swiped horizontally
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {//detect right swipe
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {//left swipe
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                //when swiped vertically
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {//detect down swipe
                        onSwipeBottom();
                    } else {//detect up swipe
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    //PUBLIC METHODS

    /**
     * Will be overrided by the class that wants to use the SwipeListener class, this will be called when the right swipe is detected
     */
    public void onSwipeRight() {
    }

    /**
     * Will be overrided by the class that wants to use the SwipeListener class, this will be called when the left swipe is detected
     */
    public void onSwipeLeft() {
    }

    /**
     * Will be overrided by the class that wants to use the SwipeListener class, this will be called when the up swipe is detected
     */
    public void onSwipeTop() {
    }

    /**
     * Will be overrided by the class that wants to use the SwipeListener class, this will be called when the down swipe is detected
     */
    public void onSwipeBottom() {
    }
}