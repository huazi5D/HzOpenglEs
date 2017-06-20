package Utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017-06-20.
 */

public class AssetsUtils {

    public static String read(Context context, String fileName) {
        String result = null;
        try {
            InputStream is = context.getResources().getAssets().open("Shader/" + fileName);
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            result = new String(buffer, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
