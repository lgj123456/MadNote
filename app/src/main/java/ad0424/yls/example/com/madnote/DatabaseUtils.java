package ad0424.yls.example.com.madnote;

import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhdj on 2017/5/5.
 */

public class DatabaseUtils {


    private static List<Note> noteArrayList = new ArrayList<>();

    public static boolean insert(String title, String content, String createTime, String imgPath, String modifyTime, int noteType) {

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setCreateTime(createTime);
        note.setModifyTime(modifyTime);
        note.setImgPath(imgPath);
        note.setNoteType(noteType);
        note.save();
        return true;
    }

    public static boolean update(int id,String title,String content,String modifyTime) {

Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setModifyTime(modifyTime);
        note.updateAll("id = ?", "" + id);

        return true;
    }

    public static boolean del(int id) {

        Note note = new Note();
        note.setIsDel(1);
        note.updateAll("id = ?", "" + id);

        return true;
    }


    public static List<Note> query() {

////        Cursor cur = context.getContentResolver().query(MyContentProvider.sutUri, null, null, null, null);
//
//        if (cur == null) {
//            return null;
//        }
//        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//            int isDel = cur.getInt(cur.getColumnIndex(TableConst.COLUMN_IS_DEL));
//            Log.i("cccccccc", "query: " + isDel + "  " + cur.getString(cur.getColumnIndex(TableConst.COLUMN_TITLE)) + "id" +cur.getInt(cur.getColumnIndex(TableConst.COLUMN_ID)));
//
//            if (isDel == 1) {
//                cur.moveToNext();
//            } else {
//                int id = cur.getInt(cur.getColumnIndex(TableConst.COLUMN_ID));
//                String title = cur.getString(cur.getColumnIndex(TableConst.COLUMN_TITLE));
//                String content = cur.getString(cur.getColumnIndex(TableConst.COLUMN_CONTENT));
//                String imgPath = cur.getString(cur.getColumnIndex(TableConst.COLUMN_IMG_PATH));
//                String modifyTime = cur.getString(cur.getColumnIndex(TableConst.COLUMN_MODIFY_TIME));
//                int noteType = cur.getInt(cur.getColumnIndex(TableConst.COLUMN_NOTE_TYPE));
//                if (modifyTime == null) {
//                    modifyTime = cur.getString(cur.getColumnIndex(TableConst.COLUMN_CREATE_TIME));
//                }
//                Note note = new Note();
//                note.setId(id);
//                note.setTitle(title);
//                note.setContent(content);
//                note.setImgPath(imgPath);
//                note.setModifyTime(modifyTime);
//                note.setNoteType(noteType);
//                noteArrayList.add(note);
//            }
//
//        }
//        return noteArrayList;
        noteArrayList = DataSupport.where("isDel = ?",0+"").find(Note.class);
        for (int i = 0; i < noteArrayList.size(); i++) {
            Log.i("cccccccc", "query: " + "id=" + noteArrayList.get(i).getId() + "  title=" + noteArrayList.get(i).getTitle() + "  isdel=" + noteArrayList.get(i).getIsDel());

        }
        return noteArrayList;
    }
}
