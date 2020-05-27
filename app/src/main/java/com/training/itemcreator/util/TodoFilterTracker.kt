package com.training.itemcreator.util

data class TodoFilterTracker(
    var lowAllowed: Boolean,
    var midAllowed: Boolean,
    var highAllowed: Boolean
){
    constructor() : this(true, true, true) {
    }
}