package com.example.panaderosvm._view_ui.panaderias

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.panaderosvm.R
import com.example.panaderosvm._view_ui.DetallePanaderiaViewModel
import com.example.panaderosvm._view_ui.logout.LogoutViewModel


class DetallePanaderiaFragment : Fragment() {

    private lateinit var detalleViewModel: DetallePanaderiaViewModel

    companion object {
        fun newInstance() =
            DetallePanaderiaFragment()
    }

    private lateinit var viewModel: DetallePanaderiaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detalle_panaderia, container, false)

        /*val textView: TextView = root.findViewById(R.id.text_panaderia)
        detalleViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetallePanaderiaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
