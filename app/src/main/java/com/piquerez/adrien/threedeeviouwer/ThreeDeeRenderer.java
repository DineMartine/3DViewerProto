package com.piquerez.adrien.threedeeviouwer;

import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Pair;

import com.piquerez.adrien.glview.GLPrimitive;
import com.piquerez.adrien.glview.GLQuad;
import com.piquerez.adrien.glview.GLShader;
import com.piquerez.adrien.model.geometry._3d.Point;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Adrien on 25/04/2015.
 */
public class ThreeDeeRenderer implements ThreeDeeView.Renderer
{
    // OpenGL View
    private static final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = uMVPMatrix * vPosition;" +
            "}";

    private static final String fragmentShaderCode =
        "precision mediump float;" +
        "uniform vec4 vColor;" +
        "void main() {" +
        "  gl_FragColor = vColor;" +
        "}";

    private GLShader glShader;
    private final float[] transformationMatrix = new float[16];

    // Device
    private float ratio = 1f; // a ratio of 0f is dangerous

    // Model
    private final float[] initialCameraPosition = new float[4];
    private List<Pair<GLPrimitive, float[]>> listOfPrimitives = new ArrayList<>();

    public ThreeDeeRenderer()
    {
        initialCameraPosition[2] = 3f;
        initialCameraPosition[3] = 1f;

        // Populate the list of primitives
        Point a = new Point(0.2f,0.2f,-0.2f);
        Point b = new Point(0.2f,-0.2f,-0.2f);
        Point c = new Point(-0.2f,-0.2f,-0.2f);
        Point d = new Point(-0.2f,0.2f,-0.2f);
        Point e = new Point(0.2f,0.2f,0.2f);
        Point f = new Point(0.2f,-0.2f,0.2f);
        Point g = new Point(-0.2f,-0.2f,0.2f);
        Point h = new Point(-0.2f,0.2f,0.2f);

        listOfPrimitives.add(Pair.create((GLPrimitive) new GLQuad(a,b,c,d),
                new float[]{0.63671875f, 0.7953125f, 0.22265625f, 1.0f}));
        listOfPrimitives.add(Pair.create((GLPrimitive) new GLQuad(a,b,f,e),
                new float[]{0.63671875f, 0.7953125f, 0.22265625f, 1.0f}));
        listOfPrimitives.add(Pair.create((GLPrimitive) new GLQuad(b,c,g,f),
                new float[]{0.63671875f, 0.7953125f, 0.22265625f, 1.0f}));
        listOfPrimitives.add(Pair.create((GLPrimitive) new GLQuad(c,d,h,g),
                new float[]{0.63671875f, 0.7953125f, 0.22265625f, 1.0f}));
        listOfPrimitives.add(Pair.create((GLPrimitive) new GLQuad(d,a,e,h),
                new float[]{0.63671875f, 0.7953125f, 0.22265625f, 1.0f}));
        listOfPrimitives.add(Pair.create((GLPrimitive) new GLQuad(e,f,g,h),
                new float[]{0.53671875f, 0.6953125f, 0.12265625f, 1.0f}));
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        // Enable depth-buffer
        GLES20.glEnable(GLES20.GL_DEPTH_TEST );
        GLES20.glDepthFunc( GLES20.GL_LEQUAL );
        GLES20.glDepthMask( true );

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Create the shader
        glShader = new GLShader(vertexShaderCode, fragmentShaderCode);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);

        ratio = (float) width / height;
    }

    private final float[] localTranslationMatrix = new float[]
    {
        1f, 0f, 0f, 0f,
        0f, 1f, 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, -0.9f, 1f
    };
    private final float[] localRotationMatrix = new float[16];
    private final float[] localTransformationMatrix = new float[16];
    private final float[] finalTransformationMatrix = new float[16];
    @Override
    public void onDrawFrame(GL10 gl)
    {
        long time = SystemClock.uptimeMillis() % 8000L;
        float angle = 0.045f * ((int) time);
        Matrix.setRotateM(localRotationMatrix, 0, angle, 0, 1.0f, -1.0f);
        Matrix.multiplyMM(localTransformationMatrix, 0, localTranslationMatrix, 0, localRotationMatrix, 0);
        Matrix.multiplyMM(finalTransformationMatrix, 0, transformationMatrix, 0, localTransformationMatrix, 0);

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Draw all primitives
        for(Pair<GLPrimitive, float[]> pair : listOfPrimitives)
        {
            glShader.draw(pair.first, pair.second, finalTransformationMatrix);
        }
    }

    // Local Arrays : No dynamic allocation is wanted
    private float[] projectionMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] modelMatrix = new float[16];
    private final float[] cameraPosition = new float[4];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    private final float[] translationVector = new float[4];
    private final float[] translationMatrix = new float[]
    {
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
    };

    public void onRotationVectorChanged(float x, float y, float z)
    {
        SensorManager.getRotationMatrixFromVector(modelMatrix, new float[]{x, y, z});
        Matrix.multiplyMV(cameraPosition, 0, modelMatrix, 0, initialCameraPosition, 0);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0,
                cameraPosition[0], cameraPosition[1], cameraPosition[2],
                cameraPosition[0], cameraPosition[1], 0f,
                0f, 1f, 0f);

        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, Math.abs(cameraPosition[2]), 2f + Math.abs(cameraPosition[2]));

        // Calculate the projection and view transformation
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        // Combine the model matrix with the projection and camera view
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);

        cameraPosition[0] = cameraPosition[0]/cameraPosition[2];
        cameraPosition[1] = cameraPosition[1]/cameraPosition[2];
        cameraPosition[2] = 0f;
        cameraPosition[3] = 0f;
        Matrix.multiplyMV(translationVector, 0, viewProjectionMatrix, 0, cameraPosition, 0);
        translationMatrix[12] = translationVector[0];
        translationMatrix[13] = translationVector[1];
        Matrix.multiplyMM(transformationMatrix, 0, translationMatrix, 0, modelViewProjectionMatrix, 0);
    }
}
