package vn.edu.fpt.kidapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import vn.edu.fpt.kidapp.R;
import vn.edu.fpt.kidapp.database.DBManagerAPI;
import vn.edu.fpt.kidapp.model.APIObjectJSON;
import vn.edu.fpt.kidapp.model.CapturePicture;
import vn.edu.fpt.kidapp.adapter.PictureHistoryAdapter;

public class ViewHistoryActivity extends AppCompatActivity {

    public static final String TAG = ViewHistoryActivity.class.getSimpleName();

    private GridView grvHistory;
    private List<CapturePicture> listPicture;
    private PictureHistoryAdapter adapter;
    private DBManagerAPI dbManagerAPI;
    private List<APIObjectJSON.Picture> listPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        grvHistory = findViewById(R.id.grvHistory);
        
        Intent intent = getIntent();
        String result = intent.getStringExtra("LIST_PICTURE");
        Log.e(TAG, "onCreate: " + result);
        Gson gson = new Gson();
        APIObjectJSON resultJSON = gson.fromJson(result, new TypeToken<APIObjectJSON>(){}.getType());
        listPictures = resultJSON.getData().getPictures();
        for (APIObjectJSON.Picture p: listPictures) {
            Log.e(TAG, "onCreate: " + p.getImageName());
        }
        adapter = new PictureHistoryAdapter(this, R.layout.picture_history_item_layout, listPictures);
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
