package ad0424.yls.example.com.madnote;

/**
 * Created by yhdj on 2017/4/28.
 */

public class Note {


//    public Note(String title, String content){
//        this.title = title;
//        this.content = content;
//    }

    public NoteType getNoteType() {
        return mNoteType;
    }

    public void setNoteType(NoteType noteType) {
        mNoteType = noteType;
    }

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
    private NoteType mNoteType;

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    private String modifyTime;
}
