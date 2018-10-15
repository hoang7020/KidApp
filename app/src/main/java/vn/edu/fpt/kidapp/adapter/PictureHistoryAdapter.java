package vn.edu.fpt.kidapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.edu.fpt.kidapp.Model.CapturePicture;
import vn.edu.fpt.kidapp.R;
import vn.edu.fpt.kidapp.utils.FileUtil;

public class PictureHistoryAdapter extends ArrayAdapter<CapturePicture> {

    private Context context;
    private int resource;
    private List<CapturePicture> data;

    public PictureHistoryAdapter(Context context, int resource, List<CapturePicture> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.picture_history_item_layout, parent, false);
            viewHolder.imvPicture = convertView.findViewById(R.id.imvPicture);
            viewHolder.txtEng1 = convertView.findViewById(R.id.txtEng1);
            viewHolder.txtEng2 = convertView.findViewById(R.id.txtEng2);
            viewHolder.txtEng3 = convertView.findViewById(R.id.txtEng3);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CapturePicture pic = getItem(position);
        Bitmap picture = FileUtil.readFileFromSdCard(pic.getName());
        viewHolder.imvPicture.setImageBitmap(picture);
        viewHolder.txtEng1.setText(pic.getEng1());
        viewHolder.txtEng2.setText(pic.getEng2());
        viewHolder.txtEng3.setText(pic.getEng3());
        return convertView;
    }

    @Override
    public CapturePicture getItem(int position) {
        return data.get(position);
    }

    public class ViewHolder {
        ImageView imvPicture;
        TextView txtEng1, txtEng2, txtEng3;
    }
}
