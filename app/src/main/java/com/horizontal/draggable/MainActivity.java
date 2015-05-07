package com.horizontal.draggable;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    private ImageView draggable;
    private int windowWidth;
    private static final String TAG = "TouchListener";
    public float deltaX;
    public float deltaY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        draggable = (ImageView) findViewById(R.id.dragger);
        windowWidth = getWindowManager().getDefaultDisplay().getWidth(); //Get window width for horizontal scroll

        //Listener interface
        draggable.setOnTouchListener(new View.OnTouchListener() {

            double leftMargin = windowWidth * 0.01; //Set max value for left margin
            double rightMargin = windowWidth * 0.85; //Set max value for right margin

            float deltaX;
            float deltaY;

            // We can be in one of these 3 states
            public static final int NONE = 0;
            public static final int DRAG = 1;
            public static final int ZOOM = 2;
            public int mode = NONE;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        deltaX = motionEvent.getX();
                        deltaY = motionEvent.getY();
                        mode = DRAG;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        mode = ZOOM;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            if ((draggable.getX() + motionEvent.getX() - deltaX) > rightMargin) {
                                draggable.setX((float) rightMargin);
                                draggable.setY(draggable.getY());
                            } else if ((draggable.getX() + motionEvent.getX() - deltaX) < leftMargin) {
                                draggable.setX((float) leftMargin);
                                draggable.setY(draggable.getY());
                            } else {
                                draggable.setX(draggable.getX() + motionEvent.getX() - deltaX);
                                draggable.setY(draggable.getY());
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
