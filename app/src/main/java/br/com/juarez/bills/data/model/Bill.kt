package br.com.juarez.bills.data.model

data class Bill(
        val description: String,
        val value: Double,
        val category: Category)