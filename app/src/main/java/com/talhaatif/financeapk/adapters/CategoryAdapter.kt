package com.talhaatif.financeapk.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talhaatif.financeapk.databinding.ItemCategoryBinding
import com.talhaatif.financeapk.firebase.Variables
import com.talhaatif.financeapk.models.Category

class CategoryAdapter(private var categories: List<Category>, private val onCategorySelected: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val items = mutableListOf<Any>()



    init {
        updateItems()
    }


    fun updateData(newData: List<Category>) {
        categories = newData
        notifyDataSetChanged() // Notify adapter of data changes
    }

    private fun updateItems() {
        items.clear()
        for ( category in categories) {
            items.add(category) // add category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding, onCategorySelected)

    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int = items.size

    class CategoryViewHolder(private val binding: ItemCategoryBinding, private val onCategorySelected: (Category) -> Unit) // Callback for category selection) :
        :  RecyclerView.ViewHolder(binding.root) {



        fun bind(category : Category) {
            binding.categoryImage.setImageResource(category.imageResId)
            binding.categoryName.text = category.name

            // Set tint color based on category
            val tintColor = Variables.categoryTintMap[category.name] ?: Color.BLACK // Default to black if no color is found
            binding.categoryImage.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
            // Handle item click
            itemView.setOnClickListener {
                onCategorySelected(category) // Trigger the callback
            }
        }
    }

}
