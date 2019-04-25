package escapadetechnologies.com.tablayoutexample;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TabLayoutFragment extends Fragment {


    public static TabLayoutFragment newInstance() {

        /*Bundle args = new Bundle();

        MoviesNewFragment fragment = new MoviesNewFragment();
        fragment.setArguments(args);
        return fragment;*/
        return new TabLayoutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

}
