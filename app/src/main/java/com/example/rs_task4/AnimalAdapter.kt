package com.example.rs_task4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rs_task4.databinding.CardItemsAnimalBinding

class AnimalAdapter : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    private var animalList: MutableList<AnimalModel> = mutableListOf()

    fun addItems(items: List<AnimalModel>) {
        animalList = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AnimalViewHolder(
        CardItemsAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animalList[position]
        holder.bindView(animal)
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    fun setData(animal: AnimalModel){
        this.animalList.add(animal)
        notifyDataSetChanged()
    }


    class AnimalViewHolder(private val binding: CardItemsAnimalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(animal: AnimalModel) {
            binding.tvAge.text = animal.age.toString()
            binding.tvBreed.text = animal.breed
            binding.tvName.text = animal.name
        }

    }

}