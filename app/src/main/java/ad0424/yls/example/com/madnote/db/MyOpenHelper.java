//package ad0424.yls.example.com.madnote;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
///**
// * Created by yhdj on 2017/5/5.
// */
//
//public class MyOpenHelper extends SQLiteOpenHelper {
//    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String createSql = "create table  if not exists note (id integer  not null primary key AUTOINCREMENT ," +
//                "title varchar(30) not null," +
//                " content text not null," +
//                " createTime varchar(30) not null ," +
//                "modifyTime varchar(30) default null," +
//                "imgPath varchar(30),isDel integer default 0,noteType integer )";
//        db.execSQL(createSql);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//}
