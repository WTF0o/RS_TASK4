package com.example.rs_task4

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
         const val DATABASE_NAME = "animals.db"
         const val DATABASE_VERSION = 1
         const val NAME = "name"
         const val AGE = "age"
         const val BREED = "breed"
         const val ID = "id"
         const val TBL_ANIMALS = "tbl_animals"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblAnimals = "CREATE TABLE " + TBL_ANIMALS + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT," +
                AGE + " INTEGER," +
                BREED + " TEXT" + ")"
        db?.execSQL(createTblAnimals)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TBL_ANIMALS")
        onCreate(db)
    }

    fun insertAnimal(animal: AnimalModel): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, animal.name)
        contentValues.put(AGE, animal.age)
        contentValues.put(BREED, animal.breed)

        val success = db.insert(TBL_ANIMALS, null, contentValues)
        db.close()
        return success
    }

    fun getAllAnimals(): ArrayList<AnimalModel>{
        val animalList: ArrayList<AnimalModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_ANIMALS"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch(e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var name: String
        var age: Int
        var breed: String

        if(cursor.moveToFirst()){
            do{
                name = cursor.getString(cursor.getColumnIndex(NAME))
                age = cursor.getInt(cursor.getColumnIndex(AGE))
                breed = cursor.getString(cursor.getColumnIndex(BREED))
                val animal = AnimalModel(null, name = name, age = age, breed = breed)
                animalList.add(animal)
            } while (cursor.moveToNext())
        }

        return animalList
    }

    fun getAllAnimalsWithFilters(columnFilter: String, valueFilter: String): ArrayList<AnimalModel> {
        val animalList: ArrayList<AnimalModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_ANIMALS" +
                " WHERE $columnFilter = '$valueFilter'"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch(e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var name: String
        var age: Int
        var breed: String

        if(cursor.moveToFirst()){
            do{
                name = cursor.getString(cursor.getColumnIndex(NAME))
                age = cursor.getInt(cursor.getColumnIndex(AGE))
                breed = cursor.getString(cursor.getColumnIndex(BREED))
                val animal = AnimalModel(null, name = name, age = age, breed = breed)
                animalList.add(animal)
            } while (cursor.moveToNext())
        }

        return animalList
    }
}