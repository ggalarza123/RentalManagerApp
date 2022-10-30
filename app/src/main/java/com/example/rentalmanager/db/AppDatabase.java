package com.example.rentalmanager.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//verision 1 since this is the first version
//Transactions.class since that is the INSTANCEs that we will be creating
// @Database key class
@Database(entities = {Transactions.class, TransactionCategories.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // this creates a single instance for our database
    private static AppDatabase INSTANCE;

    public abstract TransactionDao getDao();

    //    this returns the instance of AppDatabase class
    // Context is the object parameter it needs to take
    // this initializes the context, if it is null, nothing, then we initialize it, otherwise we return it as it is
//    public static AppDatabase getDBinstance(Context context) {
//        if(INSTANCE == null) {
//            //databaseBuilder initializes or Instance, it needs context as the 1st parameter, our class name or context,
//            // and then the finally the name of our database - we can give anyname
//            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "AppDB")
//                    .allowMainThreadQueries().build();
//        }
//        return INSTANCE;
//    }

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
        synchronized (AppDatabase.class) {
        INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "Database").allowMainThreadQueries().build();
            }
        }
        return INSTANCE;
    }
}
