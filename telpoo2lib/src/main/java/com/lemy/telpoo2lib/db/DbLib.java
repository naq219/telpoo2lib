package com.lemy.telpoo2lib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;


import com.lemy.telpoo2lib.model.BObject;
import com.lemy.telpoo2lib.utils.Mlog;

import java.util.ArrayList;

public class DbLib extends SQLiteOpenHelper {
    private static String TAG = "BaseDBSupport";

    private static String dbName = "BaseDBSupport.db";
    private static int dbVersion = 1;
    private static boolean tableCreated = false;
    private static DbLib  instance = null;
    private static Context _context = null;

    protected static SQLiteDatabase mSqliteDatabase = null;

    public static String[] tables = null;
    private static String[][] keys = null;

    protected DbLib(Context context) {
        super(context, dbName, null, dbVersion);
        mSqliteDatabase = this.getWritableDatabase();

    }

    public SQLiteDatabase getSQLiteDatabase() {
        return mSqliteDatabase;
    }

    public static boolean init(String[] tables1, String[][] keys1, Context context, String db_Name, Integer db_Version) {
        tables = tables1;
        keys = keys1;
        dbName = db_Name;
        dbVersion = db_Version;
        _context = context;

        if (context == null)
            return false;
        if (instance == null) {
            instance = new DbLib(context.getApplicationContext());
        }

        if (!isTableExist())
            return createTable();

        return true;

    }

    public static DbLib getInstance(Context context) {
        if (context == null)
            return null;
        if (instance == null) {
            instance = new DbLib(context.getApplicationContext());
        }
        return instance;
    }

    public static void openDB() {
        mSqliteDatabase = instance.getWritableDatabase();
    }

    public static void openDBRead() {
        mSqliteDatabase = instance.getReadableDatabase();
    }

    public static void closeDB() {
        mSqliteDatabase.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private static Boolean isTableExist() {
        return tableCreated;
    }

    private static boolean createTable() {
        try {

            openDB();
            mSqliteDatabase.execSQL("PRAGMA encoding = 'UTF-8'");
            String query = "";
            for (int i = 0; i < tables.length; i++) {

                query = "CREATE TABLE IF NOT EXISTS " + tables[i] + "(";

                // ArrayList<String> listPrimaryKey=new ArrayList<String>();

                String prikey = "";
                for (String key : keys[i]) {
                    if (key.indexOf("primarykey_") != -1) {
                        key = key.substring(11);

                        prikey = prikey + " " + key + ",";
                    }
                    query += key + " TEXT" + ",";

                }

                if (prikey.length() > 0) {
                    prikey = "PRIMARY KEY (" + prikey.substring(0, prikey.length() - 1) + ")";
                    query = query + prikey + ")";
                }
                query = query.substring(0, query.length() - 1) + ")";
                mSqliteDatabase.execSQL(query);
            }

            Mlog.D("createTable - query = " + query);
            closeDB();
            tableCreated = true;
        } catch (Exception ex) {
            Mlog.E("-createTable=542734=" + ex);
            return false;
        }
        return true;
    }

    public void upgradeDB() {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            for (String tableName : tables) {
                db.execSQL("DROP TABLE IF EXISTS " + tableName);
            }
            db.close();
            createTable();
        } catch (Exception ex) {
            Mlog.E("-upgradeDB=57452=" + ex);
        }
    }

    // success
    protected synchronized static Cursor getAll(String tableName, String keyid, String id) {
        try {
            openDB();
            String query = "SELECT rowid,* ";
            /*
             * for (String key : keys) { query += "," + key; }
             */
            query += " FROM " + tableName;
            if (keyid != null) {
                query += " WHERE " + keyid + "='" + id + "'";
            }
            Mlog.D("getAll - query=" + query);
            Cursor cur = mSqliteDatabase.rawQuery(query, new String[] {});
            Mlog.E("getAll - cur.getCount()=" + cur.getCount());
            if (cur.getCount() == 0) {
                cur.close();
                mSqliteDatabase.close();
                return null;
            }
            closeDB();
            return cur;
        } catch (Exception ex) {
            // ex.printStackTrace();
            Mlog.E("getAll =9769785=" + ex.toString());
        }
        return null;
    }

    // success
    /*
     * @
     */
    // public synchronized static boolean addList(ArrayList<BObject> pl,
    // String tableName, String idKey) {
    // if (pl == null)
    // return false;
    // try {
    //
    // openDB();
    // BObject v1 = null;
    //
    // String[] columns = { idKey };
    // String selection = idKey + " = ?";
    //
    // for (int i = 0; i < pl.size(); i++) {
    // v1 = (BObject) pl.get(i);
    // int cou = 0;
    // Cursor cur = null;
    // if (idKey != null) {
    //
    // String[] selectionArgs = { v1.get(idKey) };
    // cur = query(tableName, selection, selectionArgs, columns, null);
    // cou = cur.getCount();
    // }
    // if (cou == 0) {
    // long resultInsertDb = mSqliteDatabase.insert(tableName, null,
    // v1.getData());
    // Mlog.D("addList - resultInsertDb:" + resultInsertDb);
    // if (resultInsertDb == -1) {
    // return false;
    // }
    //
    // } else {
    //
    // long resultupdateDb = mSqliteDatabase.update(tableName, v1.getData(),
    // idKey + "=?", new String[] { String.valueOf(v1.get(idKey)) });
    // Mlog.D("addList - resultupdateDb:" + resultupdateDb);
    // }
    // cur.close();
    // }
    // mSqliteDatabase.close();
    // } catch (Exception ex) {
    // Mlog.E(TAG + "-addList- =87908989=" + ex);
    // return false;
    // }
    // return true;
    // }

    // success
    public synchronized static boolean addToTable(ArrayList<BObject> pl, String tableName) {
        if (pl == null)
            return false;
        try {

            openDB();
            BObject v1 = null;

            for (int i = 0; i < pl.size(); i++) {
                v1 = (BObject) pl.get(i);

                long resultInsertDb = mSqliteDatabase.insert(tableName, null, v1.getDataContentValue());
                Mlog.D("DBSuppost - resultInsertDb:" + resultInsertDb);
                if (resultInsertDb == -1) {
                    return false;
                }

            }
            closeDB();
        } catch (Exception ex) {
            Mlog.E(TAG + "-addListNoCheck- 44676634==" + ex);
            return false;
        }
        return true;
    }

    protected static Cursor query(String tableName, String selection, String[] selectionArgs, String[] columns, String sortOrder) {


        try {
            openDB();
            SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
            builder.setTables(tableName);
            Cursor cursor = builder.query(mSqliteDatabase, columns, selection, selectionArgs, null, null, sortOrder);
            if (cursor == null) {
                return null;
            }
            closeDB();
            return cursor;
        } catch (SQLiteException ex) {
            Mlog.E(" query - query - SQLiteException = " + ex.getMessage());
        }

        return null;
    }

    protected static int delete(String tableName, String selection, String[] selectionArgs) {
        String log = "delete db: tableName=" + tableName + "selection=";
        if (selection != null)
            log = log + selection;

        if (selection != null)
            log = log + selection;
        try {
            openDB();
            int deletedRows;
            if (selection != null)
                deletedRows = mSqliteDatabase.delete(tableName, selection + " IN (?)", selectionArgs);
            else
                deletedRows = mSqliteDatabase.delete(tableName, null, null);
            closeDB();
            return deletedRows;

        } catch (SQLiteException ex) {
            Mlog.E("delete" + " - query - SQLiteException = " + ex.getMessage());
        }
        return 0;
    }

    public static synchronized int count(String tableName, String selection, String[] selectionArgs) {
        try {
            openDB();
            String sql = "SELECT count(*) FROM " + tableName;
            Cursor mcursor = mSqliteDatabase.rawQuery(sql, selectionArgs);
            mcursor.moveToFirst();
            int icount = mcursor.getInt(0);
            mcursor.close();
            closeDB();
            return icount;
        } catch (SQLiteException ex) {
            Mlog.E(TAG + " - count - SQLiteException =9080238234= " + ex.getMessage());
        }
        return 0;
    }

    public static synchronized Cursor get(String query) {
        Mlog.D(TAG + " -get:query=" + query);

        openDB();
        Cursor cur = mSqliteDatabase.rawQuery(query, new String[] {});

        return cur;

    }

    public static synchronized ArrayList<BObject> rawQuery(String query) {
        return cursorToOj(get(query));

    }

    protected synchronized static ArrayList<BObject> cursorToOj(Cursor cur) {

        ArrayList<BObject> params = new ArrayList<BObject>();
        try {

            if (cur != null) {

                while (cur.moveToNext()) {

                    ContentValues vl = new ContentValues();
                    DatabaseUtils.cursorRowToContentValues(cur, vl);

                    BObject oj = new BObject();
                    oj.putData(vl);
                    params.add(oj);

                }

                cur.close();

                return params;
            }
        } catch (Exception ex) {
            Mlog.E(TAG + " cursorToOj:" + ex);
        }
        return params;
    }

    public static synchronized Boolean update(BObject v1, String tableName, String idKey) {
        int count = 0;
        openDB();
        count = mSqliteDatabase.update(tableName, v1.getDataContentValue(), idKey + "=?", new String[] { String.valueOf(v1.get(idKey)) });
        closeDB();

        if (count == 0)
            return false;
        return true;

    }

    // success
    @Deprecated
    /**
     * Không cần keys
     * @param TableName
     * @param keys
     * @param keyid
     * @param id
     * @return
     */
    protected synchronized static ArrayList<ContentValues> getAllInTableToCV(String TableName, String[] keys, String keyid, String id) {
        ArrayList<ContentValues> params = new ArrayList<ContentValues>();
        try {

            Cursor cur = getAll(TableName, keyid, id);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ContentValues param = new ContentValues();
                        for (String key : keys) {
                            param.put(key, cur.getString(cur.getColumnIndex(key)));
                        }
                        params.add(param);

                    } while (cur.moveToNext());
                }
                cur.close();

                return params;
            }
        } catch (Exception ex) {

        }
        return params;
    }

    protected synchronized static ArrayList<ContentValues> getAllInTableToCV(String TableName, String keyid, String id) {
        ArrayList<ContentValues> params = new ArrayList<ContentValues>();
        try {

            Cursor cur = getAll(TableName, keyid, id);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ContentValues param = new ContentValues();
                        DatabaseUtils.cursorRowToContentValues(cur, param);
                        params.add(param);
                    } while (cur.moveToNext());
                }
                cur.close();

                return params;
            }
        } catch (Exception ex) {

        }
        return params;
    }



    @Deprecated
    /**
     * Khong can String[] keys, thay the bang
     * @param tableName
     * @param keys
     * @return
     */

    public synchronized static ArrayList<BObject> getAllOfTable(String tableName, String[] keys) {

        ArrayList<ContentValues> params = getAllInTableToCV(tableName, keys, null, null);
        ArrayList<BObject> baseOjs = new ArrayList<BObject>();
        for (int i = 0; i < params.size(); i++) {
            BObject baseOj = new BObject();
            baseOj.putData(params.get(i));
            baseOjs.add(baseOj);
        }
        return baseOjs;
    }

    public synchronized static ArrayList<BObject> getAllOfTable(String tableName) {

        ArrayList<ContentValues> params = getAllInTableToCV(tableName, null, null);
        ArrayList<BObject> baseOjs = new ArrayList<BObject>();
        for (int i = 0; i < params.size(); i++) {
            BObject baseOj = new BObject();
            baseOj.putData(params.get(i));
            baseOjs.add(baseOj);
        }
        return baseOjs;
    }



    /*
     *
     */
    // public synchronized static boolean addToTable(ArrayList<BObject> pl,
    // String tableName, boolean isCheck, String ID) {
    // if (isCheck)
    // return addList(pl, tableName, ID);
    // return addListNoCheck(pl, tableName);
    //
    // }

    public synchronized static int removeAllInTable(String tableName) {
        return delete(tableName, null, null);
    }

    public synchronized static boolean deleteRowInTable(String tableName, String keyID, String valueID) {
        return delete(tableName, keyID, new String[] { valueID }) > 0;
    }

    public synchronized static ArrayList<BObject> selectRow(String table, String key, String value) {
        String query = "select * from " + table + " where " + key + "='" + value + "'";
        return cursorToOj(get(query));
    }



}
