package com.example.rs_task4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rs_task4.databinding.ActivityMainBinding
import com.example.rs_task4.filters.FiltersActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var adapter: AnimalAdapter? = null
    private lateinit var sqlHelper: SQLiteHelper
    private var useRoom = false
    private lateinit var repo:AnimalRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, ActivityAnimals::class.java)
            intent.putExtra(FLAG_USE_ROOM, useRoom)
            startActivity(intent)
        }

        sqlHelper = SQLiteHelper(this)
        repo = AnimalRepository(this)

        initRecyclerView()
    }

    private fun getAnimals(
        useFilters: Boolean,
        columnFilter: String = "",
        valueFilters: String = ""
    ) {

        val animalList: ArrayList<AnimalModel> =
            if (useFilters) sqlHelper.getAllAnimalsWithFilters(columnFilter, valueFilters)
            else sqlHelper.getAllAnimals()

        adapter?.addItems(animalList)
    }

    private fun getAnimalsRoom(
        useFilters: Boolean,
        columnFilter: String = "",
        valueFilters: String = ""
    ) {
        var animalList: List<AnimalModel>
        MainScope().launch {
            withContext(Dispatchers.Default) {
                animalList = if (useFilters) {
                    when(columnFilter){
                        "name" -> repo.getAllAnimalsFilterName(valueFilters)
                        "age" -> repo.getAllAnimalsFilterName(valueFilters)
                        "breed" -> repo.getAllAnimalsFilterName(valueFilters)
                        else -> repo.getAllAnimals()
                    }

                }
                else repo.getAllAnimals()
            }
            adapter?.addItems(animalList)
        }

    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AnimalAdapter()
        binding.recyclerView.adapter = adapter
    }


    override fun onResume() {
        super.onResume()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val useFilters = prefs.getBoolean("useFilter", false)
        val columnFilters = prefs.getString("listFilter", "")!!
        val valueFilters = prefs.getString("valueFilter", "")!!
        useRoom = prefs.getBoolean("useRoom", false)
        if(useRoom)  getAnimalsRoom(useFilters, columnFilters, valueFilters)
        else getAnimals(useFilters, columnFilters, valueFilters)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.filter) {
            val intent = Intent(this, FiltersActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    companion object {
        const val FLAG_USE_ROOM = "useRoom"
    }


}