package br.com.juarez.bills.data.model

sealed class Category {
    object Food: Category()
    object Clothing: Category()
    object Recreation: Category()
    object Health: Category()
}