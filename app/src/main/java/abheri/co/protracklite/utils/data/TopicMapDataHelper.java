package abheri.co.protracklite.utils.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import abheri.co.protracklite.utils.builders.Goal;
import abheri.co.protracklite.utils.builders.TopicDataMap;

public class TopicMapDataHelper {
    private SQLiteDatabase database;
    private DataHelper dbHelper;
    private String[] allColumns = {DataHelper.COLUMN_TDMID, DataHelper.TOPIC_ID, DataHelper.GOAL_ID};

    public TopicMapDataHelper(Context context) {

        dbHelper = new DataHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public TopicMapDataHelper(Context context, SQLiteDatabase db) {
        dbHelper = new DataHelper(context);
        database = db;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        //dbHelper.close();
    }

    public TopicDataMap createTopicDataMap(int topic_id, long goal_id) {
        ContentValues values = new ContentValues();
        values.put(DataHelper.GOAL_ID, goal_id);
        values.put(DataHelper.TOPIC_ID, topic_id);

        long insertId = database.insert(DataHelper.TABLE_TOPIC_DATA_MAP, null, values);
        values.put(DataHelper.COLUMN_TDMID, insertId);
        Cursor cursor = database.query(DataHelper.TABLE_TOPIC_DATA_MAP,
                allColumns, DataHelper.COLUMN_TDMID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        TopicDataMap newTopicDataMap = cursorToTopicDataMap(cursor);
        cursor.close();
        return newTopicDataMap;
    }

    public void deleteTopicDataMap(Goal goal) {
        long id = goal.getId();
        System.out.println("Sentence deleted with id: " + id);
        database.delete(DataHelper.TABLE_TOPIC_DATA_MAP, DataHelper.GOAL_ID
                + " = " + id, null);
    }

    public void deleteAllTopicDataMaps() {

        int nrows = database.delete(DataHelper.TABLE_TOPIC_DATA_MAP, "1", null);
        System.out.println(nrows + " TopicDataMaps deleted");
    }

    public List<TopicDataMap> getAllTopicDataMaps() {
        List<TopicDataMap> topicDataMaps = new ArrayList<TopicDataMap>();


        Cursor cursor = database.query(DataHelper.TABLE_TOPIC_DATA_MAP,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TopicDataMap topicDataMap = cursorToTopicDataMap(cursor);
            topicDataMaps.add(topicDataMap);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return topicDataMaps;
    }

    public List<TopicDataMap> getTopicDataMaps(long goal_id) {
        List<TopicDataMap> topicDataMaps = new ArrayList<TopicDataMap>();

        String query = "SELECT * FROM " + DataHelper.TABLE_TOPIC_DATA_MAP + " WHERE " + DataHelper.COLUMN_GID + "=" + goal_id + ";";

        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TopicDataMap topicDataMap = cursorToTopicDataMap(cursor);
            topicDataMaps.add(topicDataMap);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return topicDataMaps;
    }

    public List<GoalDetails> getTopicsForGoal(long goal_id) {
        List<GoalDetails> goalDetails = new ArrayList<GoalDetails>();

        String query = "SELECT " + DataHelper.TOPIC_ID + "," + DataHelper.GOAL_ID + "," + DataHelper.COLUMN_TOPIC +
                         " FROM " + DataHelper.TABLE_TOPIC_DATA_MAP + " a " +
                         " INNER JOIN " + DataHelper.TABLE_TOPIC + " b on " +
                         "a." + DataHelper.TOPIC_ID + "=" +
                         "b." + DataHelper.COLUMN_TID +
                         " WHERE a." + DataHelper.GOAL_ID + "=" + goal_id +
                         ";";

        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            GoalDetails goalDetail = cursorToTopicMap(cursor);
            goalDetails.add(goalDetail);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return goalDetails;
    }

    private TopicDataMap cursorToTopicDataMap(Cursor cursor) {
        TopicDataMap topicDataMap = new TopicDataMap();
        topicDataMap.setTdm_id(cursor.getInt(0));
        topicDataMap.setGoal_id(cursor.getInt(1));
        topicDataMap.setTopic_id(cursor.getInt(2));
        return topicDataMap;
    }

    private GoalDetails cursorToTopicMap(Cursor cursor) {
        GoalDetails goalDetails = new GoalDetails();
        goalDetails.setTopic_id(cursor.getInt(0));
        goalDetails.setTopic_name(cursor.getString(2));

        return goalDetails;
    }

}
