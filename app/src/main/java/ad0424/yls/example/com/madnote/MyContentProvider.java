//package ad0424.yls.example.com.madnote;
//
//import android.content.ContentProvider;
//import android.content.ContentValues;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.support.annotation.Nullable;
//
///**
// * Created by yhdj on 2017/5/5.
// */
//
//public class MyContentProvider extends ContentProvider {
//    private MyOpenHelper helper;
//    private static String authorities = "com.example.madnote";
//    public static Uri sutUri = Uri.parse("content://com.example.madnote/note");
//    static UriMatcher sMatch;
//    static {
//        sMatch = new UriMatcher(UriMatcher.NO_MATCH);
//        sMatch.addURI(authorities, "note", 1);
//    }
//    @Override
//    public boolean onCreate() {
//        helper = new MyOpenHelper(getContext(), "note.db", null, 1);
//        return false;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        SQLiteDatabase db = helper.getReadableDatabase();
//
//        switch (sMatch.match(uri)) {
//            case 1:
//                return db.query("note", null, null, null, null, null, null);
//
//
//            default:
//                break;
//        }
//        db.close();
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public String getType(Uri uri) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        SQLiteDatabase db = helper.getWritableDatabase();
//        switch (sMatch.match(uri)) {
//            case 1:
//                db.insert("note", null, values);
//                break;
//
//            default:
//                break;
//        }
//        db.close();
//        return null;
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        SQLiteDatabase db = helper.getWritableDatabase();
//        switch (sMatch.match(uri)) {
//            case 1:
//                db.delete("student", selection, null);
//                break;
//
//            default:
//                break;
//        }
//
//        return 0;
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        SQLiteDatabase db = helper.getWritableDatabase();
//        switch (sMatch.match(uri)) {
//            case 1:
//                db.update("note", values, selection, null);
//                break;
//
//            default:
//                break;
//        }
//        return 0;
//    }
//}
