package com.training.itemcreator.util

import com.training.itemcreator.model.Todo

object TodoSorts {
    val naturalOrder: (Todo) -> Int = {
        it.id!!
    }

    val priorityOrder: (Todo) -> Int = {
        -it.priority.ordinal
    }
}