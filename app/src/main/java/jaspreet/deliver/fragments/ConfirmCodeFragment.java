package jaspreet.deliver.fragments;

/**
@auther Jaspreet Singh Chhabra

Senior software Developer 

Wegile, Mohali Branch
**/


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import jaspreet.deliver.R;
import jaspreet.deliver.database.Prefrences;
import jaspreet.deliver.http.AsyncTaskListener;
import jaspreet.deliver.utils.Config;
import jaspreet.deliver.utils.Util;
import jaspreet.deliver.utils.ViewUtil;


public class ConfirmCodeFragment extends Fragment implements AsyncTaskListener,TextWatcher {
	private static final int REGISTER_PAGEID = 0;
	private String TAG;
	private EditText confirm_code_code;
	private Prefrences prefrences;
	private TextView confirm_code_text1;
	private RelativeLayout validationBar;
	private LinearLayout confirm_layout;
	private ProgressBar wait_progressbar;
	private int progress = 0;
	private final int pBarMax = 60;

	
	private TextView confirm_code_text2;
	private TextView confirm_code_text3;
	private TextView confirm_code_text4,confirm_code_text5;
	private Animation animBounce;
	private Activity activity;

	private BroadcastReceiver actionVerification = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			confirm_code_code.setText(prefrences.getPassword());

//				onConfirmClick(new View(arg0));
		}
	};




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_confirm_code, container, false);
		activity=getActivity();
		TAG = this.getClass().getName();
		prefrences=Prefrences.getInstance(getActivity());
		confirm_layout=(LinearLayout)rootView.findViewById(R.id.confirm_layout);

		wait_progressbar=(ProgressBar)rootView.findViewById(R.id.wait_progressbar);
		wait_progressbar.setMax(pBarMax);
		prefrences.setVerifing(true);

        confirm_code_code = (EditText) rootView.findViewById(R.id.confirm_code_code);
		if(Util.hasLolipop())
		confirm_code_code.setLetterSpacing(1.2f);
		else{
			confirm_code_code.setHint(" -  -  -  -  -");
		}
		confirm_code_text1=(TextView)rootView.findViewById(R.id.confirm_code_text1);
        confirm_code_text2=(TextView)rootView.findViewById(R.id.confirm_code_text2);
        confirm_code_text3=(TextView)rootView.findViewById(R.id.confirm_code_text3);
        confirm_code_text4=(TextView)rootView.findViewById(R.id.confirm_code_text4);
		confirm_code_text5=(TextView)rootView.findViewById(R.id.confirm_code_text5);
        validationBar=(RelativeLayout)rootView.findViewById(R.id.validationBar);

		if(!prefrences.isVerifing()){
			confirm_layout.setVisibility(View.VISIBLE);
			confirm_code_text1.setTextColor(activity.getColor(R.color.white));
			confirm_code_text2.setTextColor(getResources().getColor(R.color.white));
			confirm_code_text3.setTextColor(getResources().getColor(R.color.white));
		final Thread pBarThread = new Thread() {
		    @Override
		    public void run() {
		        try {
		            while(progress<=pBarMax) {
		            	wait_progressbar.setProgress(progress);
		                sleep(1000);
		                ++progress; 
		            }
					if(getActivity()!=null) {
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {

								confirm_layout.setVisibility(View.GONE);
								confirm_code_text1.setTextColor(activity.getColor(R.color.colorPrimary));
								confirm_code_text2.setTextColor(activity.getColor(R.color.colorAccent));
								confirm_code_text3.setTextColor(activity.getColor(R.color.colorPrimary));
								prefrences.setVerifing(true);
//								getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//								confirm_code_code.requestFocus();
							}
						});
					}
		            
		        }
		        catch(InterruptedException e) {
		        }
		    }
		};
		pBarThread.start();
		}else{
			confirm_layout.setVisibility(View.GONE);
			prefrences.setVerifing(true);
//            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//            confirm_code_code.requestFocus();
		}

		confirm_code_code.addTextChangedListener(this);
		
		registerReceivers();
	return rootView;
	}

	private void registerReceivers() {

		
		IntentFilter intentFilter_actionVerification = new IntentFilter(
				Config.Action.ACTION_VERIFICATION);
		getActivity().registerReceiver(actionVerification, intentFilter_actionVerification);
		
	}

	/**
	 * to add dots between the text
	 */


	public void onConfirmClick(View view) {
		Log.i(TAG, "onConfirmClick");
		if (validate()) {

			/**
			 * TODO 
			 * 1. Get all phone contacts from local db
			 * 2. register user on server and find the roasters
			 */
//			DataBase base=new DataBase(getActivity());
//			JSONArray searchUser=base.selectAllContactNumbers(prefrences.getMobileNumber());
//			JSONObject request=new JSONObject();
//			try {
//				request.put("isd_code",prefrences.getCountryCode());
//				request.put("phone",prefrences.getMobileNumber());
//				request.put("password", ""+prefrences.getPassword());
//				request.put("device_id",prefrences.getDeviceToken());
//				TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
//				String imei = tm.getDeviceId();
//				request.put("imei", imei);
//				request.put("device_used","Android");
//				ViewUtil.showDialog(getActivity());
////				PostCall call=new PostCall(Urls.REGISTER_USER,request.toString(), null, ConfirmCodeFragment.this, REGISTER_PAGEID);
////				call.execute();
//
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
			
			
		}
	}



	private boolean validate() {

		
		if(!Util.haveNetworkConnection(getActivity())){
			ViewUtil.showStackBar(confirm_code_code, getString(R.string.no_internet_connectiion),getActivity());
			confirm_code_code.setText("");

			// load the animation
			animBounce = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
					R.anim.bounce);


			// button click event
			
			validationBar.startAnimation(animBounce);
			
			return false;
		}else if (!String.valueOf(prefrences.getPassword())
				.equals(confirm_code_code.getText().toString().trim())) {
			ViewUtil.showStackBar(confirm_code_code, "Enter a valid code on the sms",getActivity());
			confirm_code_code.setText("");

//			confirm_code_code.requestFocus();
			animBounce = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
			R.anim.bounce);
			// button click event
			validationBar.startAnimation(animBounce);
			return false;
		}
		return true;
	}
	


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(actionVerification);
    }

    public void onHttpResponse(String callResponse, int pageId) {
//		if(callResponse!=null && pageId==REGISTER_PAGEID){
//
////			{"status":"success","data":"1","message":"New User"}
//
//			System.out.println("isInvited response="+callResponse);
//			try {
//				JSONObject jsonObject=new JSONObject(callResponse);
//				String status=jsonObject.getString("status");
//				String authToken=jsonObject.getString("auth_token");
////				String encriptedPassword=jsonObject.getString("encriptedPassword");
////				prefrences.setEncriptedPassword(encriptedPassword);
//				prefrences.setAuthToken(authToken);
//				if(status.equals("success")) {
//
////				{"status":"success","data":[{"id":1,"password":"33f47089b66a531666429eef5fb07fab"}],"message":"New User"}
//					JSONObject data=jsonObject.getJSONObject("data");
//					prefrences.setMyId(data.getString("id"));
//
//					prefrences.setEncriptedPassword(data.getString("password"));
//					Intent registerBroadCast = new Intent();
//					registerBroadCast.setAction(Constants.ACTION_USER_REGISTERED_ACTIVITY);
//					getActivity().sendBroadcast(registerBroadCast);
//					ViewUtil.closeDialog();
//
//
//
//					Intent intent=new Intent(getActivity(), MessageReceiver.class);
//					getActivity().startService(intent);
//					prefrences.setRegistered(true);
//					FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
//					fragmentManager.popBackStack();
//					Fragment register = new SetProfileFragment();
//					FragmentTransaction ft = fragmentManager.beginTransaction();
//					ft.replace(R.id.container, register, "setProfile");
//					ft.commit();
//					Localytics.tagEvent(getString(R.string.localytics_create_account));
//
//					loadContacts();
//				}
////				finish();
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
	}
	
	/**
	 * Load phone contacts in local db
	 */
//	private void loadContacts() {
//		LoadPhoneContacts contacts=new LoadPhoneContacts(getActivity());
//		WorkQueue queue=WorkQueue.getInstance();
//		queue.execute(contacts);
//	}

	public void onError(String error, int pageId) {
		// TODO Auto-generated method stub
//		ViewUtil.closeDialog();
//		ViewUtil.callSnackbar(getActivity(), "Server time out plese try again");
	}

 int beforelength;
 int afterlength;
	@Override
	public void afterTextChanged(Editable a) {
		afterlength=a.length();
                    if(afterlength==6)
                        onConfirmClick(confirm_code_code);



	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		beforelength=s.length();
	}


	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	
}
