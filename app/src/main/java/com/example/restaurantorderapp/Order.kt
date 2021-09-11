package com.example.restaurantorderapp

class Order{
    var name = ""
    var contact = ""
    var totalPrice = 0
    var OrderItems = ArrayList<OrderItems>()

    constructor(name: String, contact: String, price: Int, OrderItems: ArrayList<OrderItems>) {
        this.name = name
        this.contact = contact
        this.totalPrice = price
        this.OrderItems = OrderItems
    }
    constructor()
}