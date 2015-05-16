package edu.fst.m2.ipii.outonight.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Dimitri on 16/05/2015.
 */
public class CroutonUtils {

    public static void displayErrorMessage(Activity activity, String message) {
        Crouton.makeText(activity, message, getCroutonErrorStyle()).show();
    }

    public static void displayErrorMessage(Activity activity, int resource) {
        Crouton.makeText(activity, resource, getCroutonErrorStyle()).show();
    }

    public static Style getCroutonErrorStyle() {
        // Define configuration options
        Configuration croutonConfiguration = new Configuration.Builder()
                .setDuration(2500).build();
        // Define custom styles for crouton
        return new Style.Builder()
                .setBackgroundColorValue(Color.parseColor("#FF0000"))
                .setGravity(Gravity.CENTER_HORIZONTAL)
                .setConfiguration(croutonConfiguration)
                .setHeight(300)
                .setPaddingInPixels(50)
                .setTextColorValue(Color.parseColor("#FFFFFF")).build();
    }
}
