package com.waqasyounis.mvvm.shopping.list.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class ShoppingItem(
    val name: String,
    var noOfItems: Int,
    val priority: Priority
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

enum class Priority {
    HIGH,
    LOW,
    MEDIUM
}