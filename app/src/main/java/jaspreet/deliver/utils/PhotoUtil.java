package jaspreet.deliver.utils;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jaspret on 18/10/15.
 */
public class PhotoUtil {

    public static Uri getMyProfileThumbUri() {

        File main_directory = new File(
                Environment.getExternalStorageDirectory()
                        + Config.AppConfig.THUMB_DIRECTORY);
        if (!main_directory.exists()) {
            main_directory.mkdirs();
            File file = new File(main_directory.getAbsoluteFile() + "/.NOMEDIA");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file = new File(main_directory.getAbsoluteFile() + "/"
                + Config.AppConfig.MY_PROFILE + ".png");
        Uri imgUri = Uri.fromFile(file);

        return imgUri;

    }

    public static Uri getMyProfileThumbUriTemp() {

        File main_directory = new File(
                Environment.getExternalStorageDirectory()
                        + Config.AppConfig.THUMB_DIRECTORY);
        if (!main_directory.exists()) {
            main_directory.mkdirs();
            File file = new File(main_directory.getAbsoluteFile() + "/.NOMEDIA");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file = new File(main_directory.getAbsoluteFile() + "/"
                + Config.AppConfig.MY_PROFILE_TEMP + ".png");
        Uri imgUri = Uri.fromFile(file);

        return imgUri;

    }

    public static String getMyProfileThumbUriPath() {

        String path = Environment.getExternalStorageDirectory()
                + Config.AppConfig.THUMB_DIRECTORY + "/" + Config.AppConfig.MY_PROFILE
                + ".png";
        return path;

    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

}
