package com.example.silento;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment {

   // ImageView img_frag3;

    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        // carview_fragment1 = (CardView) view.findViewById(R.id.carview_fragment1);
        /*img_frag3 = (ImageView) view.findViewById(R.id.img_frag3);

        final Animation animRotate = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
        img_frag3.startAnimation(animRotate);*/



        return  view;
    }

}
