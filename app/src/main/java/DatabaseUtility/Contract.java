package DatabaseUtility;

import android.provider.BaseColumns;

public final class Contract {
    private Contract() {
    }

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_LABEL = "label";
        public static final String COLUMN_NAME_SUBJECT = "subject";
    }
}
