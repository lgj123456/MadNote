package ad0424.yls.example.com.madnote.utils;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ad0424.yls.example.com.madnote.model.NoteUser;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by yhdj on 2017/5/6.
 */

public class BmobUtil {
    private static String url = "";
    private static boolean isInsert = false;
    private static boolean isUpdate = false;
    private static boolean isDel = false;

    public static boolean insert(String title, String content, String createTime, String modifyTime, int noteType, String phoneNum,String imgUrl,String audioUrl,String videoUrl) {
        isInsert = false;
        NoteUser noteUser = new NoteUser();
        noteUser.setTitle(title);
        noteUser.setContent(content);
        noteUser.setCreateTime(createTime);
        noteUser.setModifyTime(modifyTime);
        noteUser.setNoteType(noteType);
        noteUser.setPhoneNum(phoneNum);
        noteUser.setVideoPath(videoUrl);
        noteUser.setImgPath(imgUrl);
        noteUser.setAudioPath(audioUrl);
        noteUser.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    isInsert = true;
                } else {
                    Log.i("AAAA", "done: " + s);
                }
            }
        });
        return isInsert;
    }

    public static boolean update(int id, String title, String content, String modifyTime) {
        isUpdate = false;
        NoteUser noteUser = new NoteUser();
        noteUser.setTitle(title);
        noteUser.setContent(content);
        noteUser.setModifyTime(modifyTime);
        noteUser.update(String.valueOf(id), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    isUpdate = true;
                }
            }
        });

        return isUpdate;
    }

    public static boolean del(int id) {
        isDel = false;
        NoteUser noteUser = new NoteUser();
        noteUser.setIsDel(1);
        noteUser.update(String.valueOf(id), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    isDel = true;
                }
            }
        });
        return isDel;
    }


    public static List<NoteUser> query() {
        final List<NoteUser> noteUsers = new ArrayList<>();
        BmobQuery<NoteUser> query = new BmobQuery<NoteUser>();
//查询playerName叫“比目”的数据

//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
//执行查询方法
        query.findObjects(new FindListener<NoteUser>() {
            @Override
            public void done(List<NoteUser> object, BmobException e) {
                if (e == null) {

                    for (NoteUser user : object) {
                        //获得playerName的信息
                        NoteUser noteUser = new NoteUser();
                        noteUser.setTitle(user.getTitle());
                        noteUser.setContent(user.getContent());
                        noteUser.setPhoneNum(user.getPhoneNum());
                        noteUser.setIsDel(user.getIsDel());
                        noteUser.setId(user.getId());
                        noteUser.setImgPath(user.getImgPath());
                        noteUser.setAudioPath(user.getAudioPath());
                        noteUser.setVideoPath(user.getVideoPath());
                        noteUser.setModifyTime(user.getModifyTime());
                        noteUsers.add(noteUser);
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

        return noteUsers;
    }

    public static String uploadFile(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final BmobFile bmobFile = new BmobFile(new File(path));
                bmobFile.uploadblock(new UploadFileListener() {

                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            //bmobFile.getFileUrl()--返回的上传文件的完整地址
                            url = bmobFile.getFileUrl();
                        } else {
                        }
                    }

                    @Override
                    public void onProgress(Integer value) {
                        // 返回的上传进度（百分比）
                    }
                });
            }
        }).start();

        return url;
    }
}
