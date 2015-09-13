package com.piquerez.adrien.threedeeviouwer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;

/**
 * Created by Adrien on 25/04/2015.
 */
public class ThreeDeeView extends GLSurfaceView implements SensorEventListener
{
    private final ThreeDeeRenderer threeDeeRenderer;

    private final SensorManager sensorManager;
    private final Sensor rotationVectorSensor;

    public ThreeDeeView(Context context, SensorManager sensorManager)
    {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        threeDeeRenderer = new ThreeDeeRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(threeDeeRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        this.sensorManager = sensorManager;
        this.rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

    }

    @Override
    public void onResume()
    {
        sensorManager.registerListener(this, rotationVectorSensor, 10000);
        super.onResume();
    }

    @Override
    public void onPause()
    {
        sensorManager.unregisterListener(this, rotationVectorSensor);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        threeDeeRenderer.onRotationVectorChanged(event.values[0], event.values[1], event.values[2]);
        this.requestRender();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
