package com.elise.elisefinal.database;

public class SQLCommands {
    final public static String DATABASE_NAME      = "meme_database";

    final public static String SCHEMA_VERSION     = "1";

    final public static String TABLE_NAME         = "meme";

    final public static String COLUMN_ID          = "_id";

    final public static String COLUMN_NAME        = "name";

    final public static String COLUMN_URL         = "url";

    final public static String COLUMN_WIDTH       = "width";

    final public static String COLUMN_HEIGHT      = "height";

    final public static String COLUMN_BOX_COUNT   = "box_count";

    final public static String COLUMN_CAPTIONS    = "captions";

    final public static String[] TABLE_COLUMNS     = {"_id", "name", "url", "width", "height", "box_count", "captions"};

    final public static String CREATE_TABLE        =
            "CREATE TABLE meme(_id INTEGER PRIMARY KEY , name TEXT , url TEXT , width INTEGER , height INTEGER , box_count INTEGER , captions INTEGER );";

    final public static String DROP_TABLE           = "DROP TABLE meme";
}
