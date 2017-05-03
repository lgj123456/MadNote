package ad0424.yls.example.com.madnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yhdj on 2017/4/28.
 */

public class MyAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<Note> mNoteArrayList = null;

    public MyAdapter(Context mContext, ArrayList<Note> mNoteArrayList) {
        this.mContext = mContext;
        this.mNoteArrayList = mNoteArrayList;
    }

    @Override
    public int getCount() {
        return mNoteArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.list_item, null);

            viewHolder.mTv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mTv_content = (TextView) convertView
                    .findViewById(R.id.tv_content);
            viewHolder.mIv_clock = (ImageView) convertView.findViewById(R.id.iv_clock);
            viewHolder.mIv_star = (ImageView) convertView.findViewById(R.id.iv_star);
            viewHolder.MyImage = (ImageView) convertView.findViewById(R.id.MyImage);
            viewHolder.mModifyTime = (TextView) convertView.findViewById(R.id.modifyTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Note note = mNoteArrayList.get(position);
        viewHolder.mTv_content.setText(note.getContent());
        viewHolder.mTv_title.setText(note.getTitle());
        viewHolder.mModifyTime.setText(note.getModifyTime());
        return convertView;
    }

    private static class ViewHolder {
        private ImageView MyImage;
        private ImageView mIv_clock;
        private ImageView mIv_star;
        private TextView mTv_title;
        private TextView mTv_content;
        private TextView mModifyTime;
    }
}
