package my.awesome.silento;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FourthFragment extends Fragment {

 //   ImageView img_frag4;

    public FourthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);

        // carview_fragment1 = (CardView) view.findViewById(R.id.carview_fragment1);
        /*img_frag4 = (ImageView) view.findViewById(R.id.img_frag4);

        final Animation animRotate = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
        img_frag4.startAnimation(animRotate);*/



        return  view;
    }

}
