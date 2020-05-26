package com.training.itemcreator.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.training.itemcreator.util.enums.Priority

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String,
    val description: String?,
    val priority: Priority
)