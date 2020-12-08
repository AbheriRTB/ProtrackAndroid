package abheri.co.protracklite.utils.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import abheri.co.protracklite.utils.builders.Goal;

public class GoalDataHelper {
    private SQLiteDatabase database;
    private DataHelper dbHelper;
    private String[] allColumns = {DataHelper.COLUMN_GOAL_ID,
            DataHelper.COLUMN_GOAL, DataHelper.COLUMN_GOAL_DESCRIPTION, DataHelper.COLUMN_END_DATE};

    public GoalDataHelper(Context context) {

        dbHelper = new DataHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public GoalDataHelper(Context context, SQLiteDatabase db) {
        dbHelper = new DataHelper(context);
        database = db;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        //dbHelper.close();
    }

    public Goal createGoal(String goal, String description, String end_date) {
        ContentValues values = new ContentValues();
        values.put(DataHelper.COLUMN_GOAL, goal);
        values.put(DataHelper.COLUMN_GOAL_DESCRIPTION, description);
        values.put(DataHelper.COLUMN_END_DATE, end_date);

        long insertId = database.insert(DataHelper.TABLE_GOAL, null, values);
        //values.put(DataHelper.COLUMN_GID, insertId);
        Cursor cursor = database.query(DataHelper.TABLE_GOAL,
                allColumns, DataHelper.COLUMN_GOAL_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Goal newGoal = cursorToGoal(cursor);
        cursor.close();
        return newGoal;
    }

    public void deleteGoal(Goal goal) {
        long id = goal.getId();
        System.out.println("Sentence deleted with id: " + id);
        database.delete(DataHelper.TABLE_GOAL, DataHelper.COLUMN_GOAL_ID
                + " = " + id, null);
    }

    public void deleteAllGoals() {

        int nrows = database.delete(DataHelper.TABLE_GOAL, "1", null);
        System.out.println(nrows + " Goals deleted");
    }

    public List<Goal> getAllGoals() {
        List<Goal> goals = new ArrayList<Goal>();


        Cursor cursor = database.query(DataHelper.TABLE_GOAL,
                allColumns, null, null, null, null, DataHelper.COLUMN_END_DATE);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Goal goal = cursorToGoal(cursor);
            goals.add(goal);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return goals;
    }

    public List<Goal> getAllGoals(long goal_id) {
        List<Goal> goals = new ArrayList<Goal>();

        String query = "SELECT * FROM " + DataHelper.TABLE_GOAL + " WHERE " + DataHelper.COLUMN_GOAL_ID + "=" + goal_id + ";";

        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Goal goal = cursorToGoal(cursor);
            goals.add(goal);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return goals;
    }

    private Goal cursorToGoal(Cursor cursor) {
        Goal goal = new Goal();
        goal.setId(cursor.getLong(0));
        goal.setName(cursor.getString(1));
        goal.setDescription(cursor.getString(2));
        goal.setDate(cursor.getString(3));
        return goal;
    }
}
