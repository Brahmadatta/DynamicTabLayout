package escapadetechnologies.com.tablayoutexample;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //public static final String BASE_URL = "https://actualite.cd/categories";
    public static final String BASE_URL = "https://api.github.com/repositories?since=1";
    ViewPager viewPager_movies_new;
    TabLayout tabs_movies_new;
    ArrayList<HashMap<String,String>> githuArrayList;
    Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabs_movies_new = findViewById(R.id.tabs_movies_new);
        viewPager_movies_new = findViewById(R.id.viewPager_movies_new);

        viewPager_movies_new.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs_movies_new));
        tabs_movies_new.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager_movies_new.setCurrentItem(tab.getPosition());
                Log.e("tabPosition", String.valueOf(tab.getPosition()));
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                viewPager_movies_new.setBackgroundColor(color);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setDataToFragment();
    }

    private void setDataToFragment() {

        StringRequest stringRequest = new StringRequest(BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    githuArrayList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {


                        HashMap<String, String> hashMap = new HashMap<>();
                        String name = jsonArray.getJSONObject(i).getString("name");

                        Log.e("name",name);

                        tabs_movies_new.addTab(tabs_movies_new.newTab().setText(name));


                        githuArrayList.add(hashMap);
                    }

                    DynamicFragmentAdapter dynamicFragmentAdapter = new DynamicFragmentAdapter(getSupportFragmentManager(),tabs_movies_new.getTabCount(),MainActivity.this);
                    viewPager_movies_new.setAdapter(dynamicFragmentAdapter);
                    viewPager_movies_new.setCurrentItem(0);

                    //attachAdapter(githuArrayList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);

        int socketTimeout = 120000;//120000 milli seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

        queue.add(stringRequest);

    }


    public class DynamicFragmentAdapter extends FragmentStatePagerAdapter {

        private int mNumOfTabs;
        private Context context;

        public DynamicFragmentAdapter(FragmentManager fm, int numOfTabs, Context context) {
            super(fm);
            this.mNumOfTabs = numOfTabs;
            this.context = context;
        }



        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }

        @Override
        public Fragment getItem(int i) {
            Bundle b = new Bundle();
            frag = TabLayoutFragment.newInstance();
            /*frag.setArguments(i);*/
            return frag;
        }
    }
}
