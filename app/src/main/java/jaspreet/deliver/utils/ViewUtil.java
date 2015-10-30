package jaspreet.deliver.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import jaspreet.deliver.R;
import jaspreet.deliver.views.CircularProgressBarDrawable;

/**
 * Created by jaspret on 11/10/15.
 */
public class ViewUtil {
    private static Dialog dialog;
    public static void showStackBar(View view,String message,Activity activity){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    public static void showDialog(Context context) {
        if(dialog!=null && dialog.isShowing()) {
            return;
        }
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_loading_dialog);
		ProgressBar progressBar=(ProgressBar)dialog.findViewById(R.id.progressBar);
        CircularProgressBarDrawable drawable = new CircularProgressBarDrawable();
        drawable.setColors(new int[]{0xffff0000, 0xffff00a8, 0xffb400ff, 0xff2400ff, 0xff008aff,
                0xff00ffe4, 0xff00ff60, 0xff0cff00, 0xffa8ff00, 0xffffc600, 0xffff3600, 0xffff0000});
        progressBar.setProgressDrawable(drawable);
        progressBar.setMax(100);
        progressBar.setProgress(100);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
    }


    public static void closeDialog(){
        try {
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Hide the keybord
     *
     * @param editText for control of keybord
     * @param context of activity
     */
    public static void hideKeyBord(View editText,Context context) {
        // TODO Auto-generated method stub
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(),
                0);
    }

}
