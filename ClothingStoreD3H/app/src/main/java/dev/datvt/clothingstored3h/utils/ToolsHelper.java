package dev.datvt.clothingstored3h.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by datvt on 8/1/2016.
 */

public class ToolsHelper {

    public static Bitmap convertBitmap(String path) {
        Bitmap bitmap = null;
        BitmapFactory.Options bfOptions = new BitmapFactory.Options();
        bfOptions.inDither = false;                     //Disable Dithering mode
        bfOptions.inPurgeable = true;                   //Tell to gc that whether itneeds free memory, the Bitmap can be cleared
        bfOptions.inInputShareable = true;              //Which kind of reference will be                used to recover the Bitmap data after being clear, when it will be used in the future
        bfOptions.inTempStorage = new byte[32 * 1024];
        File file = new File(path);
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (fs != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    public static Bitmap convertBitmap(Context ctx, String path, int q) {

        Bitmap bitmap = null;
        BitmapFactory.Options bfOptions = new BitmapFactory.Options();
        bfOptions.inDither = false;                     //Disable Dithering mode
        bfOptions.inPurgeable = true;                   //Tell to gc that whether itneeds free memory, the Bitmap can be cleared
        bfOptions.inInputShareable = true;              //Which kind of reference will be                used to recover the Bitmap data after being clear, when it will be used in the future
        bfOptions.inTempStorage = new byte[32 * 1024];
        File file = new File(path);
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (fs != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * (q / 100)),
                (int) (bitmap.getHeight() * (q / 100)), true);
        try {
            bitmap.recycle();
            bitmap = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap1;
    }

    public static void saveScaledBitmap(Bitmap bmp, int h, int w, String dest) {

        Bitmap bmm = getResizedBitmap(bmp, h, w);
        File de = new File(dest);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(de);
            bmp.compress(Bitmap.CompressFormat.PNG, 1, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap overlay(Bitmap b1, Bitmap b2) {
        b2 = ToolsHelper.getResizedBitmap(b2, b1.getHeight(), b1.getWidth());
        Bitmap ov = Bitmap.createBitmap(b1.getWidth(), b1.getHeight(), b1.getConfig());
        Canvas cv = new Canvas(ov);
        cv.drawBitmap(b1, new Matrix(), null);
        cv.drawBitmap(b2, new Matrix(), null);
        return ov;
    }

    public static void bitmapToFile(Bitmap bmp, String dest) {
        File f = new File(dest);
        try {
            FileOutputStream os;
            os = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
            bmp.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String resizePng(Context ctx, String src, String dest, int quality) {

        try {
            Bitmap bmp = convertBitmap(ctx, src, quality);
            if (bmp.getWidth() < 512) {
                File de = new File(dest);
                FileOutputStream out = new FileOutputStream(de);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
                return dest;
            }

            File de = new File(dest);
            FileOutputStream out = new FileOutputStream(de);
            bmp.compress(Bitmap.CompressFormat.PNG, quality, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dest;
    }

    public static String refineString(String red, String rep) {
        red = red.replaceAll("[^a-zA-Z0-9]", rep);
        return red;
    }

    public static String refine(String red, String rep) {
        red = red.replaceAll("[^a-zA-Z0-9 ]", rep);
        return red;
    }

    public static void toast(Context c, String t) {
        Toast.makeText(c, t, Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyBoard(Activity act) {
        InputMethodManager inp = (InputMethodManager) act.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inp.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), 0);
    }

    public static boolean hasConnection(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (cm != null) {
                NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (wifiNetwork != null && wifiNetwork.isConnected()) {
                    return true;
                }

                NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mobileNetwork != null && mobileNetwork.isConnected()) {
                    return true;
                }

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void log(String t) {
        Log.d("TAG_CLOUD_TRACKS", t);
    }

    public static void log(String TAG, String t) {
        Log.d(TAG, t);
    }

    public static void copyFile(File src, File dst) {
        try {
            InputStream in = new FileInputStream(src);
            OutputStream os = new FileOutputStream(dst);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                os.write(buf, 0, len);
            }
            in.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dialogShow(Context c, String title, String desc) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(desc);
        alertDialogBuilder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap
                (bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth, int i) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth);
        float scaleHeight = ((float) newHeight);
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap
                (bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        return finalTimerString;
    }

    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        return percentage.intValue();
    }

    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        return currentDuration * 1000;
    }

    public static String intToString(int like) {
        String s = String.valueOf(like);
        StringBuilder builder = new StringBuilder(s);
        builder.reverse();
        int j = 0;
        for (int i = 0; i < builder.length(); i++) {
            if (i > 0 && i % 3 == 0) {
                if (i + j < builder.length()) {
                    builder.insert(i + j, ".");
                    j++;
                }
            }
        }
        builder.reverse();
        return builder.toString();
    }

    public Bitmap circle(Bitmap bmp) {
        Bitmap out = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(out);
        int color = Color.RED;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        cv.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        cv.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        cv.drawBitmap(bmp, rect, rect, paint);
        return out;
    }
}

