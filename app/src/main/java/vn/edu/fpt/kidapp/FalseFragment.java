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

    LearnBookActivity act2;
    LearnPenActivity act3;


    public FalseFragment(String text, LearnBookActivity act2) {
        // Required empty public constructor
        this.text = text;
        this.act2 = act2;
    }

    public FalseFragment(String text, LearnPenActivity act3) {
        // Required empty public constructor
        this.text = text;
        this.act3 = act3;
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
                act2.setResult(Activity.RESULT_OK);
                act2.finish();
                act3.setResult(Activity.RESULT_OK);
                act3.finish();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LearnBookActivity.edt1.setText("");
                LearnBookActivity.edt2.setText("");
                LearnBookActivity.edt3.setText("");
                LearnBookActivity.edt4.setText("");

                LearnPenActivity.edt1.setText("");
                LearnPenActivity.edt2.setText("");
                LearnPenActivity.edt3.setText("");
            }
        });

        return view;
    }

}
