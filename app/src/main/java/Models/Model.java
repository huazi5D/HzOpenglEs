package Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import Utils.ShaderUtils;

/**
 * Created by Administrator on 2017-06-20.
 */

public abstract class Model {

    protected Context mContext;
    protected int mProgram;

    public abstract void draw(float[] matrix);

    public abstract void setup();

    protected FloatBuffer convertToFloatBuffer(float[] buffer) {
        FloatBuffer fb = ByteBuffer.allocateDirect(buffer.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        fb.put(buffer);
        fb.position(0);
        return fb;
    }

    protected ShortBuffer convertToShortBuffer(short[] buffer) {
        ShortBuffer fb = ByteBuffer.allocateDirect(buffer.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer();
        fb.put(buffer);
        fb.position(0);
        return fb;
    }

    protected int getProgram(String file1, String file2) {
        return ShaderUtils.createProgram(mContext, file1, file2);
    }

    protected int createTexture(Bitmap bitmap){
        return createTexture(bitmap, 0);
    }

    protected int createTexture(Bitmap bitmap, int texture_i){
        int[] texture=new int[1];
        if(bitmap!=null&&!bitmap.isRecycled()){
            GLES20.glActiveTexture(texture_i);
            //生成纹理
            GLES20.glGenTextures(1,texture,0);
            //生成纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture[0]);
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();
            return texture[0];
        }
        return 0;
    }
}
