package vn.edu.fpt.kidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LearnActivity extends AppCompatActivity {
    static EditText edt1,edt2,edt3,edt4,edt5,edt6;
    //Button btnO,btnK,btnC,btnR,btnN,btnM,btnT,btnY,btnE,btnX;

    static TrueFragment trueFragment;
    static FalseFragment falseFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        edt1= findViewById(R.id.editText1);
        edt2= findViewById(R.id.editText2);
        edt3= findViewById(R.id.editText3);
        edt4= findViewById(R.id.editText4);
        edt5= findViewById(R.id.editText5);
        edt6= findViewById(R.id.editText6);
        trueFragment = new TrueFragment("Đúng Rồi!!!", this);
        trueFragment.setCancelable(false);
        falseFragment = new FalseFragment("Sai Rồi!!!", this);
        falseFragment.setCancelable(false);

//        btnO=findViewById(R.id.charO);
//        btnK=findViewById(R.id.charK);
//        btnC=findViewById(R.id.charc);
//        btnR=findViewById(R.id.charO);
//        btnN=findViewById(R.id.charO);
//        btnM=findViewById(R.id.charO);
//        btnT=findViewById(R.id.charO);
//        btnY=findViewById(R.id.charO);
//        btnE=findViewById(R.id.charO);
//        btnX=findViewById(R.id.charO);





    }
    public boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public void showToast(){
        if(isTrue()){

//            AlertDialog.Builder alert = new AlertDialog.Builder(LearnActivity.this);
//            alert.setTitle("Đúng rồi!!! :> ");
//
//            alert.setIcon(R.drawable.flashon);
//            alert.setPositiveButton("Tiếp Tục", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    setResult(RESULT_OK);
//
//                    finish();
//
//                }
//            });
//
//            alert.show();
            trueFragment.show(getFragmentManager(), "True");


        }else{

//            AlertDialog.Builder alert = new AlertDialog.Builder(LearnActivity.this);
//            alert.setTitle("Sai Rồi!!! :'< ");
//            alert.setMessage("Bạn có muốn thử lại không?");
//            alert.setIcon(R.drawable.flashon);
//            alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    edt1.setText("");
//                    edt2.setText("");
//                    edt3.setText("");
//                    edt4.setText("");
//                    edt5.setText("");
//                    edt6.setText("");
//
//
//
//
//                }
//            });
//            alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    setResult(RESULT_OK);
//
//                    finish();
//
//                }
//            });
//            alert.show();
            falseFragment.show(getFragmentManager(), "False");

        }
    }

    public void clickToAddCharO(View view) {
        if(isEmpty(edt1)){
            edt1.setText("O");

            return;
        }
        if(isEmpty(edt2)){
            edt2.setText("O");
            return;
        }
        if(isEmpty(edt3)){
            edt3.setText("O");
            return;
        }
        if(isEmpty(edt4)){
            edt4.setText("O");
            return;
        }
        if(isEmpty(edt5)){
            edt5.setText("O");
            return;
        }
        if(isEmpty(edt6)){
            edt6.setText("O");
            showToast();
            return;
        }






    }

    public void clickToAddCharM(View view) {
        if(isEmpty(edt1)){
            edt1.setText("M");
            return;
        }
        if(isEmpty(edt2)){
            edt2.setText("M");
            return;
        }
        if(isEmpty(edt3)){
            edt3.setText("M");
            return;
        }
        if(isEmpty(edt4)){
            edt4.setText("M");
            return;
        }
        if(isEmpty(edt5)){
            edt5.setText("M");
            return;
        }
        if(isEmpty(edt6)){
            edt6.setText("M");
            showToast();
            return;
        }
    }

    public void clickToAddCharT(View view) {
        if(isEmpty(edt1)){
            edt1.setText("T");
            return;
        }
        if(isEmpty(edt2)){
            edt2.setText("T");
            return;
        }
        if(isEmpty(edt3)){
            edt3.setText("T");
            return;
        }
        if(isEmpty(edt4)){
            edt4.setText("T");
            return;
        }
        if(isEmpty(edt5)){
            edt5.setText("T");
            return;
        }
        if(isEmpty(edt6)){
            edt6.setText("T");
            showToast();
            return;
        }
    }

    public void clickToAddCharY(View view) {
        if(isEmpty(edt1)){
            edt1.setText("Y");
            return;
        }
        if(isEmpty(edt2)){
            edt2.setText("Y");
            return;
        }
        if(isEmpty(edt3)){
            edt3.setText("Y");
            return;
        }
        if(isEmpty(edt4)){
            edt4.setText("Y");
            return;
        }
        if(isEmpty(edt5)){
            edt5.setText("Y");
            return;
        }
        if(isEmpty(edt6)){
            edt6.setText("Y");
            showToast();
            return;
        }
    }

    public void clickToAddCharE(View view) {
        if(isEmpty(edt1)){
            edt1.setText("E");
            return;
        }
        if(isEmpty(edt2)){
            edt2.setText("E");
            return;
        }
        if(isEmpty(edt3)){
            edt3.setText("E");
            return;
        }
        if(isEmpty(edt4)){
            edt4.setText("E");
            return;
        }
        if(isEmpty(edt5)){
            edt5.setText("E");
            return;
        }
        if(isEmpty(edt6)){
            edt6.setText("E");
            showToast();
            return;
        }
    }

    public void clickToAddCharX(View view) {
        if(isEmpty(edt1)){
            edt1.setText("X");
            return;
        }
        if(isEmpty(edt2)){
            edt2.setText("X");
            return;
        }
        if(isEmpty(edt3)){
            edt3.setText("X");
            return;
        }
        if(isEmpty(edt4)){
            edt4.setText("X");
            return;
        }
        if(isEmpty(edt5)){
            edt5.setText("X");
            return;
        }
        if(isEmpty(edt6)){
            edt6.setText("X");
            showToast();
            return;
        }
    }

    public void clickToAddCharN(View view) {
        if(isEmpty(edt1)){
            edt1.setText("N");
            return;
        }
        if(isEmpty(edt2)){
            edt2.setText("N");
            return;
        }
        if(isEmpty(edt3)){
            edt3.setText("N");
            return;
        }
        if(isEmpty(edt4)){
            edt4.setText("N");
            return;
        }
        if(isEmpty(edt5)){
            edt5.setText("N");
            return;
        }
        if(isEmpty(edt6)){
            edt6.setText("N");
            showToast();
            return;
        }
    }

    public void clickToAddCharR(View view) {
        if(isEmpty(edt1)){
            edt1.setText("R");
            return;
        }
        if(isEmpty(edt2)){
            edt2.setText("R");
            return;
        }
        if(isEmpty(edt3)){
            edt3.setText("R");
            return;
        }
        if(isEmpty(edt4)){
            edt4.setText("R");
            return;
        }
        if(isEmpty(edt5)){
            edt5.setText("R");
            return;
        }
        if(isEmpty(edt6)){
            edt6.setText("R");
            showToast();
            return;
        }
    }

    public void clickToAddCharC(View view) {
        if(isEmpty(edt1)){
            edt1.setText("C");
            return;
        }
        if(isEmpty(edt2)){
            edt2.setText("C");
            return;
        }
        if(isEmpty(edt3)){
            edt3.setText("C");
            return;
        }
        if(isEmpty(edt4)){
            edt4.setText("C");
            return;
        }
        if(isEmpty(edt5)){
            edt5.setText("C");
            return;
        }
        if(isEmpty(edt6)){
            edt6.setText("C");
            showToast();
            return;
        }
    }

    public void clickToAddCharK(View view) {
        if(isEmpty(edt1)){
            edt1.setText("K");
            return;
        }
        if(isEmpty(edt2)){
            edt2.setText("K");
            return;
        }
        if(isEmpty(edt3)){
            edt3.setText("K");
            return;
        }
        if(isEmpty(edt4)){
            edt4.setText("K");
            return;
        }
        if(isEmpty(edt5)){
            edt5.setText("K");
            return;
        }
        if(isEmpty(edt6)){
            edt6.setText("K");
            showToast();



            return;
        }
    }

    public  boolean isTrue(){
        if((edt1.getText().toString()+edt2.getText().toString()+edt3.getText().toString()+edt4.getText().toString()+edt5.getText().toString()+edt6.getText().toString()).equalsIgnoreCase("MONKEY"))
        return true;




        return false;
    }
}
