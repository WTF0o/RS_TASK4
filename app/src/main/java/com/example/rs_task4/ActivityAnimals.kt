package com.example.rs_task4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rs_task4.databinding.ActivityAddAnimalsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import kotlin.Exception

class ActivityAnimals : AppCompatActivity() {
    private lateinit var binding: ActivityAddAnimalsBinding
    private lateinit var sqlHelper: SQLiteHelper
    private var useRoom = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAnimalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqlHelper = SQLiteHelper(this)

        useRoom = intent.getBooleanExtra(MainActivity.FLAG_USE_ROOM, false)

        binding.btnSaveAnimal.setOnClickListener {
                if (useRoom) addAnimalRoom()
                else addAnimal()
        }

    }

    private fun addAnimal() {
        val age = binding.etAgeAnimal.text.toString().toInt()
        val name = binding.etNameAnimal.text.toString()
        val breed = binding.etBreedAnimal.text.toString()

        val animal = AnimalModel(null, name = name, age = age, breed = breed)
        val status = sqlHelper.insertAnimal(animal)

        if (status > -1) {
            Toast.makeText(this, "Животное добавлено", Toast.LENGTH_SHORT)
            clearEditText()
        } else {
            Toast.makeText(this, "Не удалось добавить животное", Toast.LENGTH_SHORT)
        }
    }

    private fun addAnimalRoom() {

        val repo = AnimalRepository(this)

        val age = binding.etAgeAnimal.text.toString().toInt()
        val name = binding.etNameAnimal.text.toString()
        val breed = binding.etBreedAnimal.text.toString()

        val animal = AnimalModel(null, name = name, age = age, breed = breed)

        MainScope().launch {
            withContext(Dispatchers.Default) {
                try {
                    repo.addAnimal(animal)
                    Log.e("TASK4", "Added animal")
                }catch (e: Exception) {
                    Log.e("TASK4", "ERROR")
                }

            }
            clearEditText()
        }

    }

    private fun clearEditText() {
        binding.etAgeAnimal.setText("")
        binding.etBreedAnimal.setText("")
        binding.etNameAnimal.setText("")
    }
}