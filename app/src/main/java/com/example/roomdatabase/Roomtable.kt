package com.example.roomdatabase
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "my_todo_list")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    var itemId: Int = 0,

    @ColumnInfo(name = "item_name")
    val itemName: String,

    @ColumnInfo(name = "item_desk")
    val itemdesk: String,

    @ColumnInfo(name = "is_completed")
    var isDone: Boolean = false
)

@Entity(tableName = "blood_group")
data class BloodGroup(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "person_name")
    val personName: String,

    @ColumnInfo(name = "blood_group")
    val bloodGroup: String
)
