package jaspreet.deliver.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by jaspret on 11/10/15.
 */
public class ViewUtil {

    public static void showStackBar(View view,String message,Activity activity){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

}
