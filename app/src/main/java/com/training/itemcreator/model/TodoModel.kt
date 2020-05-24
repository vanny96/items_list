package com.training.itemcreator.model

class TodoModel() {
    var id: Int? = null
    var name: String? = null
    var description: String? = null

    constructor(id: Int, name: String, description: String?) : this() {
        this.id = id
        this.name = name
        this.description = description
    }
}