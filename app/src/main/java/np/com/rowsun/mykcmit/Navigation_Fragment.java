package np.com.rowsun.mykcmit;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Navigation_Fragment extends Fragment {

    public static final String PREF_FILE_NAME="testpref";
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;
    private RecyclerView recyclerView;
    private viewAdapter adapter;
    public Navigation_Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer=Boolean.getBoolean(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null)
        {
            mFromSavedInstanceState=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_navigation, container, false);
        recyclerView= (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter=new viewAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;

    }
    public static List<navigationClass> getData()
    {
        List<navigationClass> list= new ArrayList<>();
        int icons=R.drawable.ic_launcher;
        String[] title={"Home","Notice Board","Downloads","Contact Us","Map","Setting"};
        for(int i=0;i<title.length;i++)
        {
            navigationClass inf=new navigationClass();
            inf.itemId=icons;
            inf.itemName=title[i];
           list.add(inf);
        }
        return list;

    }

    public void setUp(int fragement_id,DrawerLayout drawerlayout, final Toolbar toolbar) {

        containerView=getActivity().findViewById(fragement_id);
        mDrawerLayout=drawerlayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),drawerlayout,toolbar,R.string.drawerOpen,R.string.drawerClose){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer)
                {
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();


            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(slideOffset!=0) {
                    if(slideOffset<0.6)
                        toolbar.setBackgroundColor((int) (Color.parseColor("#3F51B5") - slideOffset * 100));
                }
                else
                    toolbar.setBackgroundColor(Color.parseColor("#3F51B5"));
            }
        };
        if(!mUserLearnedDrawer&&mFromSavedInstanceState)
        {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
    public static void saveToPreferences(Context context,String preferenceName,String preferenceValue)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(PREF_FILE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();
    }
    public static String readFromPreferences(Context context,String preferenceName,String defaultValue)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME,context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);
    }
}
