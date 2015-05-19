package edu.fst.m2.ipii.outonight.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Dimitri on 19/05/2015.
 */
public class PerformanceUtils {

    public static final int LOW_PERFORMANCE_DEVICE = 0;
    public static final int MEDIUM_PERFORMANCE_DEVICE = 1;
    public static final int HIGH_PERFORMANCE_DEVICE = 2;

    /**
     * Essaie de déterminer le niveau de performance en fonction de la résolution d'écran
     * @param context Le contexte de navigation
     * @return le niveau de performance
     */
    public static int getPerformanceLevel(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        // 1080p et plus
        if ((width * height) > 1800000) {
            return HIGH_PERFORMANCE_DEVICE;
        }
        // 720p et plus
        else if (width * height > 790000) {
            return MEDIUM_PERFORMANCE_DEVICE;
        }

        return LOW_PERFORMANCE_DEVICE;
    }
}
