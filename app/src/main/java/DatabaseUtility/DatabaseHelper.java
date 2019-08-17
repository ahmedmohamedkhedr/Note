package DatabaseUtility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.note.Container;
import com.example.note.DataModel;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    long newRowId;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Contract.FeedEntry.TABLE_NAME + " (" +
                    Contract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    Contract.FeedEntry.COLUMN_NAME_LABEL + " TEXT," +
                    Contract.FeedEntry.COLUMN_NAME_SUBJECT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract.FeedEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void putData(String label, String subject) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.FeedEntry.COLUMN_NAME_LABEL, label);
        values.put(Contract.FeedEntry.COLUMN_NAME_SUBJECT, subject);
        newRowId = db.insert(Contract.FeedEntry.TABLE_NAME, null, values);

    }

    public List getData() {
        List<DataModel> list = new ArrayList<>();


        SQLiteDatabase db = getReadableDatabase();
        /*
        String[] projection = {
                BaseColumns._ID,
                Contract.FeedEntry.COLUMN_NAME_LABEL,
                Contract.FeedEntry.COLUMN_NAME_SUBJECT
        };
        */
        Cursor cursor = db.query(Contract.FeedEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            String label = cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.COLUMN_NAME_LABEL));
            String subject = cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.COLUMN_NAME_SUBJECT));

            DataModel dataModel = new DataModel();
            dataModel.setLabel(label);
            dataModel.setSubject(subject);
            list.add(dataModel);
        }

        cursor.close();
        Container.list = list;
        return list;
    }

    public void updateData(String label, String Subject, int key) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.FeedEntry.COLUMN_NAME_LABEL, label);
        values.put(Contract.FeedEntry.COLUMN_NAME_SUBJECT, Subject);
        String selection = Contract.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(key)};

        int rowUpdated = db.update(Contract.FeedEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public void deleteData(String l, String s) {
        SQLiteDatabase db = getReadableDatabase();
        String selectionL = Contract.FeedEntry.COLUMN_NAME_LABEL + " LIKE ?";
        String selectionS = Contract.FeedEntry.COLUMN_NAME_SUBJECT + " LIKE ?";

        String[] selectionArgs_l = {l};
        String[] selectionArgs_s = {s};

        db.delete(Contract.FeedEntry.TABLE_NAME, selectionL, selectionArgs_l);
        db.delete(Contract.FeedEntry.TABLE_NAME, selectionS, selectionArgs_s);

        db.close();


    }

    public boolean search(String l, String s) {
        boolean isFound = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Contract.FeedEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            String label = cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.COLUMN_NAME_LABEL));
            String subject = cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.COLUMN_NAME_SUBJECT));
            if (l.equals(label) || s.equals(subject)) {
                isFound = true;
                break;
            } else {
                isFound = false;
            }
        }


        return isFound;
    }
}