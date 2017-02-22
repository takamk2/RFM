package takamk2.local.rfm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import takamk2.local.rfm.reward.RewardEntity;

/**
 * Created by takamk2 on 17/02/10.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class RFMDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "rfm.db";
    private static final int DB_VERSION = 4;

    public RFMDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RFMDBStore.Tasks.CREATE_TABLE);
        db.execSQL(RFMDBStore.Rewards.CREATE_TABLE);

        // Todo: This is DEBUG. I will erase this later
        RewardEntity reward = new RewardEntity();
        reward.setDummyData();
        db.insert(RFMDBStore.Rewards.TABLE_NAME, null, reward.getValuesForInsert());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RFMDBStore.Tasks.DROP_TABLE);
        db.execSQL(RFMDBStore.Rewards.DROP_TABLE);
        onCreate(db);
    }
}
