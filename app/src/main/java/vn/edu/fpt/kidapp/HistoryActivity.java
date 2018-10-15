package vn.edu.fpt.kidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.List;

import vn.edu.fpt.kidapp.Model.CapturePicture;
import vn.edu.fpt.kidapp.adapter.PictureHistoryAdapter;
import vn.edu.fpt.kidapp.database.DBManager;

public class HistoryActivity extends AppCompatActivity {

    private ListView lvHistory;
    private List<CapturePicture> listPicture;
    private PictureHistoryAdapter adapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initView();

        mToolbar.setTitle("Cú Cú Thông Thái");
        setSupportActionBar(mToolbar);

        DBManager db = new DBManager(this);
        listPicture = db.getAllPicture();
        adapter = new PictureHistoryAdapter(this, R.layout.picture_history_item_layout, listPicture);
        lvHistory.setAdapter(adapter);
    }

    private void initView() {
        lvHistory = findViewById(R.id.lvHistory);
        mToolbar = findViewById(R.id.tbHistory);
    }
}
