package edu.fst.m2.ipii.outonight.utils;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;

/**
 * Created by Dimitri on 17/05/2015.
 */
public class FeaturesUtils {

    public final static boolean isFeatureAvailable(Context context, String feature) {
        final PackageManager packageManager = context.getPackageManager();
        final FeatureInfo[] featuresList = packageManager.getSystemAvailableFeatures();
        for (FeatureInfo f : featuresList) {
            if (f.name != null && f.name.equals(feature)) {
                return true;
            }
        }

        return false;
    }
}
