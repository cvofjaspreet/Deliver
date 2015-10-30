package jaspreet.deliver.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import jaspreet.deliver.R;
import jaspreet.deliver.activities.RegisterActivity;
import jaspreet.deliver.database.Prefrences;
import jaspreet.deliver.lists.CountryCodeList;
import jaspreet.deliver.models.CountyCodeModel;

/**
 * Created by Dell on 3/29/2015.
 */
public class CountryCodeFragment extends Fragment {


    private ListView county_code_list;
    private View mView;
    public static String COUNTRY_NAME = "country_name", COUNTRY_CODE = "country_code";
    private String TAG = this.getClass().getName(), mCountryCode, mCountryName;
    private Prefrences prefrences = Prefrences.getInstance(getActivity());

    private Context mContext;
    // private View mView;
    private InputStream inputStream;
    private ArrayList<CountyCodeModel> mCountyCodesModels;
    private LayoutInflater inflater;
    HashMap<String, Integer> alphaIndexer;
    String[] sections;
    private CountryCodeListAdapter adapter;
    private Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_counry_code_list, container, false);
        try{

            county_code_list = (ListView)rootView.findViewById(R.id.county_code_list);
            mCountyCodesModels = CountryCodeList.getInstance();
            adapter=new CountryCodeListAdapter();
            county_code_list.setAdapter(adapter);
            activity=getActivity();
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                county_code_list.setFastScrollAlwaysVisible(true);
            }
            county_code_list.setFastScrollEnabled(true);
            prefrences.setSelectedCountry(-1);
            county_code_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    /**
                     *
                     * If already any country is selected then clear the selection and mark selected country as selected
                     */
                    RelativeLayout codeModel = (RelativeLayout) arg0
                            .getChildAt(prefrences.getSelectedCountry());
                    if (codeModel != null) {
                        LinearLayout layout5 = (LinearLayout) codeModel.getChildAt(2);
                        ImageView imageView1 = (ImageView) layout5.getChildAt(1);
                        imageView1.setVisibility(View.GONE);
                    }
                    if (mView != null) {
                        RelativeLayout layout = (RelativeLayout) mView;
                        LinearLayout layout2 = (LinearLayout) layout.getChildAt(2);
                        ImageView imageView = (ImageView) layout2.getChildAt(1);
                        imageView.setVisibility(View.GONE);
                    }
                    RelativeLayout layout3 = (RelativeLayout) arg1;
                    LinearLayout layout4 = (LinearLayout) layout3.getChildAt(2);
                    ImageView imageView3 = (ImageView) layout4.getChildAt(1);
                    imageView3.setVisibility(View.GONE);
                    TextView country_code = (TextView) layout4.getChildAt(0);
                    mCountryCode = country_code.getText().toString();
                    TextView country_name = (TextView) layout3.getChildAt(1);
                    mCountryName = country_name.getText().toString();
                    mView = arg1;
                    adapter.notifyDataSetChanged();
                    prefrences.setSelectedCountry(arg2);
                    onDoneClick(arg1);
                }

            });
        }catch (Exception e) {
            e.printStackTrace();
        }

        return  rootView;
    }

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.country_code, menu);
		return true;
	}*/


    public void onDoneClick(View view){



        try{
            Log.i(TAG, "onDoneClick");
            Intent intent = new Intent();

            int item_selected = prefrences.getSelectedCountry();
            if (item_selected != -1) {
                ArrayList<CountyCodeModel> codeList = CountryCodeList.getInstance();
                CountyCodeModel countyCodeModel = codeList.get(item_selected);
                RegisterActivity registerActivity=(RegisterActivity)getActivity();
                if (mCountryName != null) {
                    registerActivity.countryName=mCountryName;
                } else {
                    registerActivity.countryName= countyCodeModel.getCountryName();
                }
                if (mCountryCode != null) {
                    registerActivity.countryCode=mCountryCode;
                } else {
                    registerActivity.countryCode=countyCodeModel.getCountryCode();
                }
            }
            getActivity().onBackPressed();
        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {}
        return super.onOptionsItemSelected(item);
    }
    */
    public class CountryCodeListAdapter extends BaseAdapter implements
            SectionIndexer {



        public CountryCodeListAdapter() {
            try {
                inflater = LayoutInflater.from(getActivity());
                alphaIndexer = new HashMap<String, Integer>();
                int size = mCountyCodesModels.size();

                for (int x = 0; x < size; x++) {
                    String s = mCountyCodesModels.get(x).getCountryName();
                    // get the first letter of the store

                    System.out.println(s);
                    String ch = s.substring(0, 1);
                    // convert to uppercase otherwise lowercase a -z will be sorted
                    // after upper A-Z
                    ch = ch.toUpperCase();
                    // put only if the key does not exist
                    if (!alphaIndexer.containsKey(ch))
                        alphaIndexer.put(ch, x);
                }

                Set<String> sectionLetters = alphaIndexer.keySet();
                // create a list from the set to sort
                ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
                Collections.sort(sectionList);
                sections = new String[sectionList.size()];
                sections = sectionList.toArray(sections);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return mCountyCodesModels.size();
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return mCountyCodesModels.get(arg0);
        }

        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        public View getView(int arg0, View arg1, ViewGroup arg2) {
            try {
                ViewHolder viewHolder = null;
                CountyCodeModel codeModel = mCountyCodesModels.get(arg0);
                if (arg1 == null) {
                    viewHolder = new ViewHolder();
                    arg1 = inflater.inflate(R.layout.item_county_code_list, null);
                    viewHolder.county_code_list_item_country = (TextView) arg1
                            .findViewById(R.id.county_code_list_item_country);
                    viewHolder.county_code_list_item_country_code = (TextView) arg1
                            .findViewById(R.id.county_code_list_item_country_code);
                    viewHolder.county_code_list_item_map = (ImageView) arg1
                            .findViewById(R.id.county_code_list_item_map);
                    viewHolder.county_code_list_item_country_check = (ImageView) arg1
                            .findViewById(R.id.county_code_list_item_country_check);
                    arg1.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) arg1.getTag();
                }
                viewHolder.county_code_list_item_country.setText(codeModel
                        .getCountryName());
                viewHolder.county_code_list_item_country_code.setText(codeModel
                        .getCountryCode());
                viewHolder.county_code_list_item_country_check.setVisibility(View.GONE);

                int selected = prefrences.getSelectedCountry();

                if (selected == arg0) {
                    viewHolder.county_code_list_item_country_check
                            .setVisibility(View.GONE);
                    viewHolder.county_code_list_item_country.setTextColor(activity.getColor(R.color.colorPrimary));
                    viewHolder.county_code_list_item_country_code.setTextColor(activity.getColor(R.color.colorPrimary));
                }else{
                    viewHolder.county_code_list_item_country.setTextColor(activity.getColor(R.color.colorAccent));
                    viewHolder.county_code_list_item_country_code.setTextColor(activity.getColor(R.color.colorAccent));
                }
//		try {
//			inputStream = mContext.getAssets().open(
//					"flags/" + codeModel.getFlagImageName() + ".png");
//			viewHolder.county_code_list_item_map.setImageBitmap(BitmapFactory
//					.decodeStream(inputStream));
//			inputStream.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
                // arg1.setOnClickListener(this);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return arg1;
        }



        public int getPositionForSection(int arg0) {
            return alphaIndexer.get(sections[arg0]);
        }

        public int getSectionForPosition(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        public Object[] getSections() {
            // TODO Auto-generated method stub
            return sections;
        }
    }
    private static class ViewHolder {
        public TextView county_code_list_item_country;
        public TextView county_code_list_item_country_code;
        public ImageView county_code_list_item_map,
                county_code_list_item_country_check;
    }
}