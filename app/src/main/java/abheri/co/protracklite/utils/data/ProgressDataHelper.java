package abheri.co.protracklite.utils.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import abheri.co.protracklite.utils.builders.Progress;
import abheri.co.protracklite.utils.builders.ProgressDetails;
import abheri.co.protracklite.utils.builders.Topic;

public class ProgressDataHelper {

    private SQLiteDatabase database;
    private DataHelper dbHelper;
    private String[] allColumns = {DataHelper.COLUMN_PID,
            DataHelper.COLUMN_PROGRESS, DataHelper.COLUMN_DATE, DataHelper.TOPICDATA_ID};

    public ProgressDataHelper(Context context) {
        dbHelper = new DataHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public ProgressDataHelper(Context context, SQLiteDatabase db) {
        dbHelper = new DataHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        //dbHelper.close();
    }

    public Progress createProgress(int progress, String progressDate, long tdataID) {
        ContentValues values = new ContentValues();
        //values.put(DataHelper.COLUMN_PID, tid);
        values.put(DataHelper.COLUMN_PROGRESS, progress);
        values.put(DataHelper.COLUMN_DATE, progressDate);
        values.put(DataHelper.TOPICDATA_ID, tdataID);
        long insertId = database.insert(DataHelper.TABLE_PROGRESS, null, values);
        Cursor cursor = database.query(DataHelper.TABLE_PROGRESS,
                allColumns, DataHelper.COLUMN_PID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Progress newProgress = cursorToProgress(cursor);
        cursor.close();
        return newProgress;
    }

    public void deleteProgress(Progress topic) {
        long id = topic.getProgress_id();
        System.out.println("Progress deleted with id: " + id);
        database.delete(DataHelper.TABLE_PROGRESS, DataHelper.COLUMN_PID
                + " = " + id, null);
    }

    public void deleteAllProgresses() {
        int nrows = database.delete(DataHelper.TABLE_PROGRESS, "1", null);
        System.out.println(nrows + " Progresses deleted");
    }

    public List<Progress> getAllProgresses() {
        List<Progress> progresses = new ArrayList<Progress>();

        Cursor cursor = database.query(DataHelper.TABLE_PROGRESS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Progress progress = cursorToProgress(cursor);
            progresses.add(progress);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return progresses;
    }

    public List<Progress> getProgressesByMAP(long tdata_id) {
        List<Progress> progresses = new ArrayList<Progress>();

        String query = "SELECT * FROM " + DataHelper.TABLE_PROGRESS + " WHERE " + DataHelper.COLUMN_TDMID + "=" + tdata_id + ";";

        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Progress progress = cursorToProgress(cursor);
            progresses.add(progress);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return progresses;
    }

    public List<ProgressDetails> getProgressesByTopicID(long topic_ID) {
        List<ProgressDetails> progressDetails = new ArrayList<ProgressDetails>();

        String query = "SELECT " +  DataHelper.COLUMN_PROGRESS + "," + DataHelper.TOPICDATA_ID + "," +
                DataHelper.COLUMN_DATE +
                " FROM " + DataHelper.TABLE_PROGRESS  +
                " WHERE " + DataHelper.TOPICDATA_ID + " in ( " +
                "SELECT " +  DataHelper.COLUMN_TDMID + " FROM " + DataHelper.TABLE_TOPIC_DATA_MAP  +
                " WHERE " + DataHelper.TOPIC_ID + "=" + topic_ID +
                ");";
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProgressDetails progressDetail = cursorToTopicId(cursor, topic_ID);
            progressDetails.add(progressDetail);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return progressDetails;
    }

    public List<ProgressDetails> getProgressesByTopicID2(long topic_ID) {
        List<ProgressDetails> progressDetails = new ArrayList<ProgressDetails>();

        String query = "SELECT " + DataHelper.TOPIC_ID + "," + DataHelper.COLUMN_PROGRESS + "," + DataHelper.COLUMN_TDMID + "," +
                DataHelper.COLUMN_DATE +
                " FROM " + DataHelper.TABLE_PROGRESS + " a " +
                " INNER JOIN " + DataHelper.TABLE_TOPIC_DATA_MAP + " b on " +
                "a." + DataHelper.TOPICDATA_ID + "=" +
                "b." + DataHelper.COLUMN_TDMID +
                " WHERE b." + DataHelper.TOPIC_ID + "=" + topic_ID +
                ";";
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProgressDetails progressDetail = cursorToTopicId(cursor, topic_ID);
            progressDetails.add(progressDetail);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return progressDetails;
    }

    private Progress cursorToProgress(Cursor cursor) {
        Progress progress = new Progress();
        progress.setProgress_id(cursor.getLong(0));
        progress.setProgress(cursor.getInt(1));
        progress.setDate(cursor.getString(2));
        progress.setTopicdata_id(cursor.getLong(3));
        return progress;
    }

    private ProgressDetails cursorToTopicId(Cursor cursor, long topicID) {
        ProgressDetails progressDetails = new ProgressDetails();
        progressDetails.setId(topicID);
        progressDetails.setProgress(cursor.getInt(0));
        progressDetails.setMap_id(cursor.getLong(1));
        progressDetails.setDate(cursor.getString(2));
        return progressDetails;
    }
}

//SELECT DataHelper.TOPIC_ID, DataHelper.COLUMN_PROGRESS, DataHelper.COLUMN_TDMID, DataHelper.COLUMN_DATE FROM DataHelper.TABLE_PROGRESS a INNER JOIN DataHelper.TABLE_TOPIC_DATA_MAP b on a. DataHelper.TOPICDATA_ID = b.  DataHelper.COLUMN_TDMID WHERE a. DataHelper.TOPIC_ID = topic_ID;

