package vn.edu.fpt.kidapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import vn.edu.fpt.kidapp.R;
import vn.edu.fpt.kidapp.adapter.PictureHistoryAdapter;
import vn.edu.fpt.kidapp.database.DBManagerAPI;
import vn.edu.fpt.kidapp.model.APIObjectJSON;
import vn.edu.fpt.kidapp.model.CapturePicture;
import vn.edu.fpt.kidapp.utils.FileUtil;

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
        dbManagerAPI = new DBManagerAPI(this);
        
        Intent intent = getIntent();
        String result = intent.getStringExtra("LIST_PICTURE");
        Log.e(TAG, "onCreate: " + result);
        Gson gson = new Gson();
        APIObjectJSON resultJSON = gson.fromJson(result, new TypeToken<APIObjectJSON>(){}.getType());
        listPictures = resultJSON.getData().getPictures();
        adapter = new PictureHistoryAdapter(this, R.layout.picture_history_item_layout, listPictures);
        grvHistory.setAdapter(adapter);


        grvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CapturePicture pic = (CapturePicture) parent.getItemAtPosition(position);
                APIObjectJSON.Picture pic = (APIObjectJSON.Picture) parent.getItemAtPosition(position);
                Log.e(TAG, "onItemClick: " + pic.getImageId() + " " + pic.getImageName() + " " + pic.getEngSub().getEng1() + " " + pic.getVieSub().getVie1());
                Intent intent = new Intent();
                intent.putExtra("PICTURE", pic);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        grvHistory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopup(view, position);
                return true;
            }
        });

    }

    public void showPopup(View v, final int pos) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.menu_history);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getTitle().equals("Delete")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(ViewHistoryActivity.this);
                    alert.setTitle("Confirm");
                    alert.setMessage("Do you want to delete this photo?");
                    alert.setIcon(R.drawable.flashon);
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbManagerAPI.deletePicture(listPictures.get(pos).getImageId());
                            FileUtil.deleteFileFromSdCard(listPictures.get(pos).getImageName());
                            Toast.makeText(ViewHistoryActivity.this, "Delete " + listPictures.get(pos).getImageName() + " Success!!!", Toast.LENGTH_SHORT).show();
                            listPictures.remove(pos);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.show();

                }
                return false;
            }
        });
    }





}
