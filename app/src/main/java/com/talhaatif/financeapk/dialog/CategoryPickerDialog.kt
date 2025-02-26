package com.talhaatif.financeapk.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talhaatif.financeapk.R
import com.talhaatif.financeapk.adapter.CategoryAdapter
import com.talhaatif.financeapk.models.Category

class CategoryPickerDialog(
    context: Context,
    private val categories: List<Category>,
    private val onCategorySelected: (Category) -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) // Remove the default title
        setContentView(R.layout.dropdown_category)

        // Set transparent background
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setDialogMargins()

        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.categoryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CategoryAdapter(categories) { category ->
            onCategorySelected(category) // Trigger the callback
            dismiss() // Close the dialog after selecting an item
        }
    }


    private fun setDialogMargins() {
        val window = window ?: return
        val params = window.attributes

        // Set margins (in pixels)
        val marginHorizontal = 50 // Horizontal margin
        val marginTop = 100 // Top margin

        // Set the position and size of the dialog
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.x = marginHorizontal // Horizontal margin
        params.y = marginTop // Top margin

        window.attributes = params
    }
}