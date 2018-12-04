package br.com.juarez.bills.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.juarez.bills.R
import br.com.juarez.bills.data.BillRepository
import br.com.juarez.bills.presentation.adapter.BillsAdapter
import kotlinx.android.synthetic.main.fragment_bills.*

class BillsFragment: Fragment() {

    private var billRepository: BillRepository = BillRepository()
    private var billsAdapter: BillsAdapter = BillsAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_bills, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        billsAdapter.data = billRepository.readAll().toMutableList()

        rvBills.layoutManager = LinearLayoutManager(requireContext())
        rvBills.adapter = billsAdapter
        rvBills.setHasFixedSize(true)
    }

}