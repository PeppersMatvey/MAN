package get.newmaps.notes;

import android.content.Context;
import android.content.res.Configuration;

import java.io.File;

public class StorageApp {
    private static File appPatch;

    public static File getAppPatch() {
        return appPatch;
    }

    public static void setAppPatch(File appPatch) {
        StorageApp.appPatch = appPatch;
    }
    public static boolean isNightMode(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }
}
