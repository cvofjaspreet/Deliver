package jaspreet.deliver.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.squareup.picasso.Picasso;

import jaspreet.deliver.R;
import jaspreet.deliver.activities.MainActivity;
import jaspreet.deliver.database.Prefrences;
import jaspreet.deliver.utils.Config;
import jaspreet.deliver.utils.PhotoUtil;

/**
 * Created by jaspret on 12/10/15.
 */
public class SetProfileFragment extends Fragment {
    private Activity activity;
    private FloatingActionButton fab;
    private AlertDialog alertDialog;
    private String mediaPath;
    private static int INTENT_REQUEST_GET_IMAGES = 0;
    private int chooserType;
    private ImageView profilePic;
    private Prefrences prefrences;
    private TextView nickName;
    private Button confirm;

    public Prefrences getPrefrences() {
        return prefrences;
    }

    public void setPrefrences(Prefrences prefrences) {
        this.prefrences = prefrences;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_profile, null);
        activity = getActivity();
        setPrefrences(Prefrences.getInstance(activity));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nickName=(TextView)view.findViewById(R.id.nickName);
        confirm=(Button)view.findViewById(R.id.confirm);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        profilePic = (ImageView) view.findViewById(R.id.profilePic);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder.setMessage("Select Image option :");
                alertDialogBuilder.setTitle("Menu");
                alertDialogBuilder.setPositiveButton("Gallery",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        });
                alertDialogBuilder.setNegativeButton("Camera",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        if(getPrefrences().isFbConnected()){
            Profile profile=Profile.getCurrentProfile();
                    Uri uri=profile.getProfilePictureUri(profilePic.getLayoutParams().width, profilePic.getLayoutParams().height);
                    String path=uri.toString();
                    Picasso.with(activity)
                    .load(path)
                    .into(profilePic);
            getPrefrences().setUserPicUrl(path);
            nickName.append(profile.getName());
        }

        onConfirmClick();
    }

    public void onConfirmClick(){

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrefrences().setProfile(true);
                getPrefrences().setNickName(nickName.getText().toString().trim());
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });

    }

}
