package com.example.rs_task4

import android.content.Context



class AnimalRepository(context: Context) {

   var db: AnimalDao = AnimalDatabase.getDatabase(context)?.animalDao()!!

    fun getAllAnimalsFilterAge(valueFilter: String): List<AnimalModel>{
        return db.getAllAnimalsFilterAge(valueFilter)
    }

    fun getAllAnimalsFilterName(valueFilter: String): List<AnimalModel>{
        return db.getAllAnimalsFilterName(valueFilter)
    }

    fun getAllAnimalsFilterBreed(valueFilter: String): List<AnimalModel>{
        return db.getAllAnimalsFilterBreed(valueFilter)
    }

    fun getAllAnimals(): List<AnimalModel>{
        return db.getAllAnimals()
    }

     fun addAnimal(animal: AnimalModel){
        db.addAnimal(animal)
    }
 }