package com.training.itemcreator.util.enums

import androidx.annotation.IdRes
import androidx.room.TypeConverter
import com.training.itemcreator.R

enum class Priority(@IdRes val id: Int) {
    LOW(R.id.LowPriority),
    MEDIUM(R.id.MediumPriority),
    HIGH(R.id.HighPriority);

    companion object{
        fun fromId(@IdRes id: Int): Priority{
            for(priority in values()){
                if(priority.id == id) return priority
            }
            return LOW
        }
    }

    class TypeConverterUtil{
        @TypeConverter
        fun toName(value: Priority): Int{
            return value.ordinal
        }

        @TypeConverter
        fun fromName(ordinal: Int): Priority{
            for(priority in values()){
                if(priority.ordinal == ordinal) return priority
            }
            return LOW
        }
    }
}