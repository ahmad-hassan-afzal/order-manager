package com.example.restaurantorderapp

class Product{

    var id: Int = 0
    var name: String
    var short_desc: String
    var full_desc: String
    var category: String
    var price: Int = 0
    var image: ByteArray?

    constructor(id: Int, name: String, short_desc: String, full_desc: String, category: String, price: Int, image: ByteArray?){
        this.id = id
        this.name = name
        this.short_desc = short_desc
        this.full_desc = full_desc
        this.category = category
        this.price = price
        this.image = image
    }
}