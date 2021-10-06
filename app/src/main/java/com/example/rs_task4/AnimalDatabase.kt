package com.example.rs_task4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [AnimalModel::class], version = 1, exportSchema = false)
abstract class AnimalDatabase: RoomDatabase() {
    abstract fun animalDao(): AnimalDao

    companion object{
        @Volatile
        private var INSTANCE: AnimalDatabase? = null

        fun getDatabase(context: Context): AnimalDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimalDatabase::class.java,
                    SQLiteHelper.DATABASE_NAME)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}