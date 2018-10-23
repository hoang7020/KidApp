package vn.edu.fpt.kidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import vn.edu.fpt.kidapp.model.CapturePicture;
import vn.edu.fpt.kidapp.adapter.PictureHistoryAdapter;
import vn.edu.fpt.kidapp.database.DBManager;

public class HistoryActivity extends AppCompatActivity {

    private static final String TAG = HistoryActivity.class.getSimpleName();

    private ListView lvHistory;
    private List<CapturePicture> listPicture;
    private PictureHistoryAdapter adapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initView();

        mToolbar.setTitle("Cú Cú");
        setSupportActionBar(mToolbar);

        DBManager db = new DBManager(this);
        listPicture = db.getAllPicture();
        adapter = new PictureHistoryAdapter(this, R.layout.picture_history_item_layout, listPicture);
        lvHistory.setAdapter(adapter);

        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CapturePicture pic = (CapturePicture) parent.getItemAtPosition(position);
                Log.e(TAG, "onItemClick: " + pic.getId() + " " + pic.getEng1() + " " + pic.getVie1() + " " + pic.getName());

                Intent intent = new Intent();
                intent.putExtra("FILENAME",pic.getName());
                intent.putExtra("Eng1",pic.getEng1());
                intent.putExtra("Eng2",pic.getEng2());
                intent.putExtra("Eng3",pic.getEng3());
                intent.putExtra("Vie1",pic.getVie1());
                intent.putExtra("Vie2",pic.getVie2());
                intent.putExtra("Vie3",pic.getVie3());



                setResult(RESULT_OK,intent);
                finish();






            }
        });
    }

    private void initView() {
        lvHistory = findViewById(R.id.lvHistory);
        mToolbar = findViewById(R.id.tbHistory);
    }
}
