package com.piquerez.adrien.glview;

import android.opengl.GLES20;

/**
 * Created by Adrien on 25/04/2015.
 */
public class GLShader
{
    private final int glProgram;

    public GLShader(String vertexShaderCode, String fragmentShaderCode)
    {
        int vertexShader = GLShader.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = GLShader.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        glProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(glProgram, vertexShader);
        GLES20.glAttachShader(glProgram, fragmentShader);
        GLES20.glLinkProgram(glProgram);
    }

    public void draw(GLPrimitive object, float[] color, float[] modelViewProjectionMatrix)
    {
        GLES20.glUseProgram(glProgram);

        int positionHandle = GLES20.glGetAttribLocation(glProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, GLPrimitive.COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                GLPrimitive.COORDS_PER_VERTEX * GLPrimitive.BYTES_PER_COORD,
                object.vertexBuffer());

        int colorHandle = GLES20.glGetUniformLocation(glProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // Pass the projection and view transformation to the shader
        int mMVPMatrixHandle = GLES20.glGetUniformLocation(glProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, modelViewProjectionMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, object.vertexCount());

        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    private static int loadShader(int type, String shaderCode){

        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
