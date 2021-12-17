package com.junaid.groceryappassignment.model.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.junaid.groceryappassignment.utils.general.AppConstants.GROCERY_STATUS.PENDING_STATUS
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import javax.annotation.Nullable

@Entity(tableName = "grocery_list_item")
data class GroceryListItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long = 0,

    @Nullable
    @ColumnInfo(name = "date")
    @TypeConverters(TimestampConverter::class)
    var date: Date? = null,

    @ColumnInfo(name = "status")
    var groceryStatus:String = PENDING_STATUS

) : Serializable {

    fun getFormattedDate():String{
        val simpleDateFormat=SimpleDateFormat("dd MMM yyyy hh:mm aa",Locale.getDefault())
        return simpleDateFormat.format(date!!)
    }
    fun getDuplicatedItem():GroceryListItem{
        val newGroceryItem=GroceryListItem()
        newGroceryItem.date= Date()
        return newGroceryItem
    }
}