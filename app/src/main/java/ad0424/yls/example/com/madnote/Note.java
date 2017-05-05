package ad0424.yls.example.com.madnote;

import org.litepal.crud.DataSupport;

/**
 * Created by yhdj on 2017/4/28.
 */

public class Note extends DataSupport {



    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean star) {
        isStar = star;
    }

    public boolean isColock() {
        return isColock;
    }

    public void setColock(boolean colock) {
        isColock = colock;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private String content;
    private String imgPath;
    private String audioPath;
    private String videoPath;
    private boolean isColock;
    private boolean isStar;

    public int getNoteType() {
        return NoteType;
    }

    public void setNoteType(int noteType) {
        NoteType = noteType;
    }

    private int NoteType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    private String modifyTime;
    private String createTime;
    private int isDel;

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
