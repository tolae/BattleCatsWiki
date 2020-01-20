package com.spitfirex2.battlecatsmasterwiki.util;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.spitfirex2.battlecatsmasterwiki.database.unit.Unit;

import java.io.InputStream;
import java.net.URL;

import static com.spitfirex2.battlecatsmasterwiki.activities.MainActivity.TAG;

public class Utility {

    public static void loadFormImage(Unit unit_form) {
        String img_url = unit_form.img;
        Drawable fromUrl = null;
        if (!img_url.isEmpty()) {
            fromUrl = loadImageFromWebOperations(img_url);
        }
        unit_form.imgDrawable = fromUrl;
    }

    private static Drawable loadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            Log.d(TAG, Log.getStackTraceString(e));
            return null;
        }
    }
}