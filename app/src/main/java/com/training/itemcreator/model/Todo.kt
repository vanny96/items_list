package com.training.itemcreator.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.training.itemcreator.util.enums.Priority

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    var name: String,
    var description: String?,
    var priority: Priority
)