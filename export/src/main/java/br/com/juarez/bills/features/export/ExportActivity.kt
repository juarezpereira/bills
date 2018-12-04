package br.com.juarez.bills.features.export

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.juarez.bills.data.BillRepository
import br.com.mobapps.bills.features.export.R
import kotlinx.android.synthetic.main.activity_export.*
import java.text.NumberFormat

class ExportActivity : AppCompatActivity() {

    private var numberFormat: NumberFormat = NumberFormat.getCurrencyInstance()
    private var repository: BillRepository = BillRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export)

        val totalValue = repository.readAll().fold(0.0) { acc, bill -> acc.plus(bill.value) }
            tvTotalValue.text = numberFormat.format(totalValue)
    }

}