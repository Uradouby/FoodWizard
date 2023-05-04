package com.example.foodwizard.DB

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class users(val username: String? = null, val password: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.

}