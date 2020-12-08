package abheri.co.protracklite.utils.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    String SubjectName = "Subject";
    String TopicName = "Topic";

    public static final String TABLE_SUBJECT = "subjects";
    public static final String COLUMN_SUBJECT_ID = "_id";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_SUBJECT_DESCRIPTION = "subject_description";


    public static final String TABLE_TOPIC = "topics";
    public static final String COLUMN_TOPIC_ID = "_id";
    public static final String COLUMN_TOPIC = "topic";
    public static final String SUBJECT_ID = "subject_id";
    public static final String COLUMN_TOPIC_DESCRIPTION = "topic_description";


    public static final String TABLE_GOAL = "goals";
    public static final String COLUMN_GOAL_ID = "_id";
    public static final String COLUMN_GOAL = "goal";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_GOAL_DESCRIPTION = "description";

    public static final String TABLE_TOPIC_DATA_MAP = "topic_data_maps";
    public static final String COLUMN_MAP_ID = "topic_map_id";
    public static final String TOPIC_ID = "topic_id";
    public static final String GOAL_ID = "goal_id";


    public static final String TABLE_PROGRESS = "progresses";
    public static final String COLUMN_PROGRESS = "progress";
    public static final String COLUMN_PROGRESS_ID = "_id";
    public static final String MAP_ID = "map_id";
    public static final String IS_LATEST = "is_latest";
    public static final String COLUMN_DATE = "date";


    private static final String DATABASE_NAME = "protrack.db";

    private static final int DATABASE_VERSION = 1;


    private static final String subject_table = "create table "
            + TABLE_SUBJECT + "(" + COLUMN_SUBJECT_ID
            + " integer primary key, "
            + COLUMN_SUBJECT
            + " text not null,"
            + COLUMN_SUBJECT_DESCRIPTION
            + " text not null);";


    private static final String goal_table = "create table "
            + TABLE_GOAL + "(" + COLUMN_GOAL_ID
            + " integer primary key AUTOINCREMENT, "
            + COLUMN_GOAL
            + " text not null,"
            + COLUMN_END_DATE
            + " date not null,"
            + COLUMN_GOAL_DESCRIPTION
            + " text not null);";

    private static final String topic_table = "create table "
            + TABLE_TOPIC + "(" + COLUMN_TOPIC_ID
            + " integer primary key AUTOINCREMENT, "
            + COLUMN_TOPIC
            + " text not null,"
            + COLUMN_TOPIC_DESCRIPTION
            + " text not null,"
            + SUBJECT_ID
            + " integer,"
            + " foreign key( "
            + SUBJECT_ID + ") references "
            + TABLE_SUBJECT
            + "(" + COLUMN_SUBJECT_ID + "));";

    private static final String progress_table = "create table "
            + TABLE_PROGRESS + "(" + COLUMN_PROGRESS_ID
            + " integer primary key AUTOINCREMENT, "
            + COLUMN_PROGRESS
            + " integer not null,"
            + COLUMN_DATE
            + " date not null,"
            + MAP_ID
            + " integer not null,"
            + IS_LATEST
            + " integer not null,"
            + " foreign key( "
            + MAP_ID + ") references "
            + TABLE_TOPIC_DATA_MAP
            + "(" + COLUMN_MAP_ID + "));";

    private static final String topic_data_map_table = "create table "
            + TABLE_TOPIC_DATA_MAP + "("
            + COLUMN_MAP_ID
            + " integer primary key AUTOINCREMENT, "
            + GOAL_ID
            + " integer not null,"
            + TOPIC_ID
            + " integer not null,"
            + " foreign key( "
            + GOAL_ID + ") references "
            + TABLE_GOAL
            + "(" + COLUMN_GOAL_ID + "),"
            + " foreign key( "
            + TOPIC_ID + ") references "
            + TABLE_TOPIC
            + "(" + COLUMN_TOPIC_ID + "));";


    private Context dbContext;


    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(subject_table);
        db.execSQL(topic_table);
        db.execSQL(goal_table);
        db.execSQL(topic_data_map_table);
        db.execSQL(progress_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void DeleteSubjectTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT + ";");
    }

    public void DeleteTopicTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPIC + ";");
    }

    public void DeleteGoalTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOAL + ";");
    }

    public void DeleteTopicDataMapTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPIC_DATA_MAP + ";");
    }


}
