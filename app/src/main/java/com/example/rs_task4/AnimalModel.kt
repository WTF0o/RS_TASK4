package com.example.rs_task4

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SQLiteHelper.TBL_ANIMALS)
data class AnimalModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var name: String?,
    var age: Int?,
    var breed: String?
){

}