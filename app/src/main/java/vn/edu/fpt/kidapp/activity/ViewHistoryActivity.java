package vn.edu.fpt.kidapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.List;

import vn.edu.fpt.kidapp.R;
import vn.edu.fpt.kidapp.model.CapturePicture;
import vn.edu.fpt.kidapp.adapter.PictureHistoryAdapter;
import vn.edu.fpt.kidapp.database.DBManager;
import vn.edu.fpt.kidapp.utils.FileUtil;

public class ViewHistoryActivity extends AppCompatActivity {
    private GridView grvHistory;
    private DBManager db;
    private List<CapturePicture> listPicture;
    private PictureHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        grvHistory = findViewById(R.id.grvHistory);

        db = new DBManager(this);
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

                if (item.getTitle().equals("Delete")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ViewHistoryActivity.this);
                    alert.setTitle("Confirm");
                    alert.setMessage("Do you want to delete this photo?");
                    alert.setIcon(R.drawable.flashon);
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean result = db.deletePictureByName(listPicture.get(pos).getName());
                            if (result) {
                                Toast.makeText(ViewHistoryActivity.this, "Delete " + listPicture.get(pos).getName() + " success!!!", Toast.LENGTH_SHORT).show();
                                FileUtil.deleteFileFromSdCard(listPicture.get(pos).getName());
                                listPicture.remove(pos);
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(ViewHistoryActivity.this, "Delete " + listPicture.get(pos).getName() + " fail!!!", Toast.LENGTH_SHORT).show();
                            }

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
