package br.com.juarez.bills.data

import br.com.juarez.bills.data.model.Bill
import br.com.juarez.bills.data.model.Category

class BillRepository {

    fun readAll(): List<Bill> {
        return MutableList(20) {
            val category = when (it) {
                0, 1, 2, 14 -> Category.Food
                3, 4, 5, 12, 13 -> Category.Health
                6, 7, 8, 9, 10, 11-> Category.Clothing
                else -> Category.Recreation
            }

            return@MutableList Bill(description = "Description $it", value = it * 10.0, category = category)
        }
    }

}