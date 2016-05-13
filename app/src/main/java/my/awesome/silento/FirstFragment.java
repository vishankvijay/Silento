package my.awesome.silento;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    /*CardView carview_fragment1;
    ImageView img_frag1;*/


    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

       /* carview_fragment1 = (CardView) view.findViewById(R.id.carview_fragment1);
        img_frag1 = (ImageView) view.findViewById(R.id.img_frag1);

        final Animation animRotate = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
        img_frag1.startAnimation(animRotate);*/



        return  view;
    }

}
