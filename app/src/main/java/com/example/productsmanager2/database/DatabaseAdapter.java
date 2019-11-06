package com.example.productsmanager2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.example.productsmanager2.CrudOperations;
import com.example.productsmanager2.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Korzhov
 * @project ProductsManager2
 * @createdAt 05-Nov-19
 */
public class DatabaseAdapter implements CrudOperations<Product> {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private Cursor getAllEntries() {
        String[] columns = new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_COUNT};
        return database.query(DatabaseHelper.TABLE, columns,
                null, null, null, null, null);
    }

    public long getCount() {
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    @Override
    public List<Product> listAll() {
        ArrayList<Product> products = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                int price = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
                int count = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNT));
//                boolean bought = cursor.getColumnIndex("bought") == 1;
                products.add(new Product(id, name, price, count));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    @Override
    public Product getOne(final Long id) {
        Product product = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            int price = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
            int count = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNT));
//            boolean bought = cursor.getColumnIndex("bought") == 1;
            product = new Product(id, name, price, count);
        }
        cursor.close();
        return product;
    }

    public long insert(Product product) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, product.getName());
        cv.put(DatabaseHelper.COLUMN_PRICE, product.getPrice());
        cv.put(DatabaseHelper.COLUMN_COUNT, product.getCount());
//        cv.put(DatabaseHelper.COLUMN_BOUGHT, product.isBought());
        return database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long update(Product product) {
        String whereClause = DatabaseHelper.COLUMN_ID + "=" + product.getId();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, product.getName());
        cv.put(DatabaseHelper.COLUMN_PRICE, product.getPrice());
        cv.put(DatabaseHelper.COLUMN_COUNT, product.getCount());
//        cv.put(DatabaseHelper.COLUMN_BOUGHT, product.isBought());
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }

    @Override
    public long delete(final Long objId) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(objId)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }
}

