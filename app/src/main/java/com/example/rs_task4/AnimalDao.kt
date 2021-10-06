package com.example.rs_task4


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAnimal(animalModel: AnimalModel)

    @Query(value = "SELECT * FROM ${SQLiteHelper.TBL_ANIMALS}")
    fun getAllAnimals(): List<AnimalModel>

    @Query(value = "SELECT * FROM ${SQLiteHelper.TBL_ANIMALS} WHERE age = :valueFilter")
    fun getAllAnimalsFilterAge(valueFilter: String): List<AnimalModel>

    @Query(value = "SELECT * FROM ${SQLiteHelper.TBL_ANIMALS} WHERE name = :valueFilter")
    fun getAllAnimalsFilterName(valueFilter: String): List<AnimalModel>

    @Query(value = "SELECT * FROM ${SQLiteHelper.TBL_ANIMALS} WHERE breed = :valueFilter")
    fun getAllAnimalsFilterBreed(valueFilter: String): List<AnimalModel>
}