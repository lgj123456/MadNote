package ad0424.yls.example.com.madnote;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by yhdj on 2017/4/28.
 */

public class MyAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<Note> mNoteArrayList = null;


    public MyAdapter(Context mContext, List<Note> mNoteArrayList) {
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


        int noteType = note.getNoteType();
        switch (noteType) {
            case NoteType.GAME:
                viewHolder.mTv_content.setText("游戏");
                break;
            case NoteType.LIFE:
                viewHolder.mTv_content.setText("生活");
                break;
            case NoteType.LOVE:
                viewHolder.mTv_content.setText("爱情");
                break;
            case NoteType.SPORT:
                viewHolder.mTv_content.setText("运动");
                break;
            case NoteType.STUDY:
                viewHolder.mTv_content.setText("学习");
                break;
            case NoteType.NEWS:
                viewHolder.mTv_content.setText("新闻");
                break;
        }

        viewHolder.mTv_title.setText(note.getTitle());
        viewHolder.mModifyTime.setText(note.getModifyTime());

        if (note.getImgPath() != null) {
            Glide.with(convertView.getContext()).load(note.getImgPath()).into(viewHolder.MyImage);
        } else {
            viewHolder.MyImage.setImageResource(R.drawable.a1);
        }

        if (note.getAudioPath() != null) {
            Log.i("aaaaaaaaaaa", "getView: a" + note.getAudioPath());
        }
        if (note.getVideoPath() != null) {
            Log.i("aaaaaaaaaa", "getView: b" + "mmmmmmmmmm"+note.getVideoPath());
        }

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
