package com.example.attendancemarker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Defines a custom SurfaceView class which handles the drawing thread
 **/
public class BaseSurface extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener, Runnable
{
    private SurfaceHolder holder;
    private Thread drawThread;
    private boolean surfaceReady = false;
    private boolean drawingActive = false;
    private Paint samplePaint = new Paint();
    private static final String LOGTAG = "surface";
    private String pattern = "086";
    private float [][]dots = {{110, 100}, {510,100},{910,100}, {110,400},{510,400},{910,400},{110,700},{510,700},{910,700}};
    private int indx = 0;
    private Context context;
    Teacher tech;
    String idx;
    ArrayList<String> sn,sc,si;

    public BaseSurface(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setOnTouchListener(this);
        // red
        samplePaint.setColor(0xffff0000);
        // smooth edges
        samplePaint.setAntiAlias(true);
    }

    public void SetPattern(Teacher teacher, ArrayList<String> sn1,ArrayList<String> sc1,ArrayList<String> si1,String idx1, String ptr, Context t){
        pattern = ptr;
        context = t;
        tech=teacher;
        sn=sn1;
        sc=sc1;
        si=si1;
        idx=idx1;
        //Toast.makeText(getContext(),si.get(0)+"",Toast.LENGTH_LONG).show();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        if (width == 0 || height == 0)
        {
            return;
        }

        // resize your UI
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        this.holder = holder;

        if (drawThread != null)
        {
            Log.d(LOGTAG, "draw thread still active..");
            drawingActive = false;
            try
            {
                drawThread.join();
            } catch (InterruptedException e)
            { // do nothing
            }
        }

        surfaceReady = true;
        startDrawThread();
        Log.d(LOGTAG, "Created");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        // Surface is not used anymore - stop the drawing thread
        stopDrawThread();
        // and release the surface
        holder.getSurface().release();

        this.holder = null;
        surfaceReady = false;
        Log.d(LOGTAG, "Destroyed");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        // Handle touch events
        return true;
    }

    /**
     * Stops the drawing thread
     */
    public void stopDrawThread()
    {
        if (drawThread == null)
        {
            Log.d(LOGTAG, "DrawThread is null");
            return;
        }
        drawingActive = false;
        while (true)
        {
            try
            {
                Log.d(LOGTAG, "Request last frame");
                drawThread.join(3000);
                Intent intent = new Intent(context,EndActivity.class);
                intent.putExtra("tech_obj",tech);
                intent.putExtra("idx",String.valueOf(idx));
                intent.putStringArrayListExtra("subn",sn);
                intent.putStringArrayListExtra("subc",sc);
                intent.putStringArrayListExtra("subi",si);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            } catch (Exception e)
            {
                //Log.e(LOGTAG, "Could not join with draw thread");
                System.out.println(e);
                Intent intent = new Intent(context,EndActivity.class);
                intent.putExtra("tech_obj",tech);
                intent.putExtra("idx",String.valueOf(idx));
                intent.putStringArrayListExtra("subn",sn);
                intent.putStringArrayListExtra("subc",sc);
                intent.putStringArrayListExtra("subi",si);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            }
        }
        drawThread = null;
    }

    /**
     * Creates a new draw thread and starts it.
     */
    public void startDrawThread()
    {
        if (surfaceReady && drawThread == null)
        {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(displayMetrics);
            float height = displayMetrics.heightPixels - 30;
            float width = displayMetrics.widthPixels - 20;
            float gap_y = height/3,gap_x = width/3;
            float x = gap_x/2,y = gap_y/2;
            for(int i=0;i<9;i++){
                dots[i][0] = x;dots[i][1] = y;
                if(i == 2 || i == 5) {
                    x = gap_x/2;
                    y += gap_y;
                }
                else{
                    x += gap_x;
                }
            }
            drawThread = new Thread(this, "Draw thread");
            drawingActive = true;
            drawThread.start();
        }
    }

    @Override
    public void run()
    {
        Canvas canvas = holder.lockCanvas();
        try{
            canvas.drawARGB(255, 255, 255, 255);
            for(int i=0;i<9;i++){
                canvas.drawCircle(dots[i][0],dots[i][1],15,samplePaint);
            }
            holder.unlockCanvasAndPost(canvas);
        }catch (Exception e){
            System.out.println("Error!");
        }
        indx++;
        try {
            while (drawingActive)
            {
                if(indx == pattern.length()){
                    stopDrawThread();
                    return;
                }
                try {
                    Thread.sleep(1500);
                    canvas = holder.lockCanvas();
                    init(canvas);
                    indx++;
                } catch (InterruptedException ignored) { }
            }
        } catch (Exception e)
        {
            Log.w(LOGTAG, "Exception while locking/unlocking");
        }
    }

    public void init(Canvas canvas){
        float start_x,start_y,end_x,end_y;
        canvas.drawARGB(255, 255, 255, 255);
        for(int i=0;i<9;i++){
            canvas.drawCircle(dots[i][0],dots[i][1],15,samplePaint);
        }
        String n = ""+pattern.charAt(0);
        int temp = Integer.parseInt(n);
        start_x = dots[temp][0];
        start_y = dots[temp][1];
        for(int i=1;i<=indx;i++){
            n = ""+pattern.charAt(i);
            temp = Integer.parseInt(n);
            end_x = dots[temp][0];end_y = dots[temp][1];
            System.out.println(start_x+" "+start_y+" "+end_x+" "+end_y);
            canvas.drawLine(start_x,start_y,end_x,end_y,samplePaint);
            start_x = end_x;start_y = end_y;
        }
        holder.unlockCanvasAndPost(canvas);
    }
}
