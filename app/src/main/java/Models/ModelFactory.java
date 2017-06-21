package Models;

import android.content.Context;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class ModelFactory {

    public enum ModelType {
        TRIANGLE, SQUARE;
    }

    public static Model getModel(Context context, ModelType modelType) {
        Model model = null;
        switch (modelType) {
            case TRIANGLE:
                model = new TriangleModel(context);
                break;
            case SQUARE:
                model = new SquareModel(context);
                break;
        }

        return model;
    }
}
