package com.talhaatif.financeapk.firebase


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.talhaatif.financeapk.R

class Variables {
    companion object {
        // for dealing with user sessions, authentication details
        val auth = FirebaseAuth.getInstance()
        @SuppressLint("StaticFieldLeak")
        // for dealing with crud on db
        val db = FirebaseFirestore.getInstance()
        // for dealing with files on db storage like user profile image, or video images like that
        val storageRef = FirebaseStorage.getInstance().reference


        val categoryTintMap = mapOf(
            "food" to Color.GREEN,
            "transport" to Color.BLUE,
            "shopping" to Color.YELLOW,
            "entertainment" to Color.MAGENTA,
            "health" to Color.parseColor("#FF1493") // Pink color for Health
        )

        val categoryResourceMap = mapOf(
            "food" to  R.drawable.ic_food ,
            "transport" to R.drawable.ic_baseline_directions_transit_24,
            "shopping" to R.drawable.ic_baseline_shopping_cart_24,
            "entertainment" to R.drawable.ic_entertain,
            "health" to R.drawable.ic_health// Pink color for Health
        )

        fun displayErrorMessage(message: String, context: Context) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun isEmailValid(email: String): Boolean {
            val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            return emailRegex.matches(email)
        }

        fun getRandomAvatar(context: Context): Uri {
            // List of avatar resource names
            val avatars = listOf(
                "avatar1", "avatar2", "avatar3", "avatar4", "avatar5", "avatar6", "avatar7"
            )

            // Randomly select an avatar
            val randomAvatar = avatars.random()

            // Get the resource ID of the selected avatar
            val resourceId = context.resources.getIdentifier(randomAvatar, "drawable", context.packageName)

            // Convert the drawable resource ID to a URI
            return Uri.parse("android.resource://${context.packageName}/$resourceId")
        }
    }
}