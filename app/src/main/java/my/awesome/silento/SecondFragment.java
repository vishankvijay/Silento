package my.awesome.silento;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    TextView text_second_frag;

   /* ImageView img_frag2;
    View view;*/

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);

       // carview_fragment1 = (CardView) view.findViewById(R.id.carview_fragment1);
        /*img_frag2 = (ImageView) view.findViewById(R.id.img_frag2);

        final Animation animRotate = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
        img_frag2.startAnimation(animRotate);*/


       /* text_second_frag = (TextView) view.findViewById(R.id.text_second_frag);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IndieFlower.ttf");
        text_second_frag.setTypeface(type);*/



        return  view;
    }


}
