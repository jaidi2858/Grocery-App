package com.junaid.groceryappassignment.model.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "grocery_item")
data class GroceryItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "groceryId")
    var groceryId: Long = 0,

    @ColumnInfo(name = "groceryText")
    var groceryText: String = "",

    @ColumnInfo(name = "groceryPrice")
    var groceryPrice: String = "",

    @ColumnInfo(name = "groceryStatus")
    var groceryStatus: String = ""

) : Serializable