package vn.edu.fpt.kidapp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FalseFragment extends DialogFragment {


    private String text;
    LearnActivity act;


    public FalseFragment(String text, LearnActivity act) {
        // Required empty public constructor
        this.text = text;
        this.act = act;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.fragment_false, container, false);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnOk = view.findViewById(R.id.btnOk);
        TextView txtResult = view.findViewById(R.id.txtText);

        txtResult.setText(text);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.setResult(Activity.RESULT_OK);
                act.finish();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LearnActivity.edt1.setText("");
                LearnActivity.edt2.setText("");
                LearnActivity.edt3.setText("");
                LearnActivity.edt4.setText("");
                LearnActivity.edt5.setText("");
                LearnActivity.edt6.setText("");
                LearnActivity.falseFragment.dismiss();
            }
        });

        return view;
    }

}
