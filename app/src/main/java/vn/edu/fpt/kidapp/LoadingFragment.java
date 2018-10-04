package vn.edu.fpt.kidapp;


import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingFragment extends DialogFragment {

    TextView txtDemo;
    LottieAnimationView lavLoading;

    public LoadingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        lavLoading = view.findViewById(R.id.lavLoading);
        lavLoading.setAnimation(R.raw.loading);
        lavLoading.playAnimation();
        lavLoading.loop(true);
        return view;
    }

}
