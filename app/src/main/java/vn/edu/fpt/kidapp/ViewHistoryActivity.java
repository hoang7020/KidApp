package vn.edu.fpt.kidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;

import java.util.List;

import vn.edu.fpt.kidapp.model.CapturePicture;
import vn.edu.fpt.kidapp.adapter.PictureHistoryAdapter;
import vn.edu.fpt.kidapp.database.DBManager;

public class ViewHistoryActivity extends AppCompatActivity {
    private GridView grvHistory;
    private List<CapturePicture> listPicture;
    private PictureHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        grvHistory = findViewById(R.id.grvHistory);

        DBManager db = new DBManager(this);
        listPicture = db.getAllPicture();
        adapter = new PictureHistoryAdapter(this, R.layout.picture_history_item_layout, listPicture);
        grvHistory.setAdapter(adapter);


        grvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CapturePicture pic = (CapturePicture) parent.getItemAtPosition(position);


                Intent intent = new Intent();
                intent.putExtra("PICTURE", pic);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        grvHistory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                showPopup(view);



                return true;
            }
        });


    }


    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.menu_history);
        popupMenu.show();



    }



}
