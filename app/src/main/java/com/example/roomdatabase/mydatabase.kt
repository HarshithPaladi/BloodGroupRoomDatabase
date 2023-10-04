package com.example.roomdatabase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// MyDatabase.kt
@Database(entities = [TodoItem::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun myDao(): TodoDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "pretest.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
@Database(entities = [BloodGroup::class], version = 1)
abstract class BloodDatabase : RoomDatabase() {
    abstract fun BloodDao(): BloodDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: BloodDatabase? = null

        fun getDatabase(context: Context): BloodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BloodDatabase::class.java,
                    "blood.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}