package com.example.panaderosvm._view_ui.panaderias

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.panaderosvm.R
import com.example.panaderosvm._view_ui.Base.BaseFragment
import com.example.panaderosvm._view_ui.Base.BasicMethods
import com.example.panaderosvm._view_ui.DetallePanaderiaViewModel
import com.example.panaderosvm.model.local.panaderias.PanaderiasEntity
import com.example.panaderosvm.model.local.pueblos.PueblosEntity
import kotlinx.android.synthetic.main.fragment_panaderias.*
import kotlinx.android.synthetic.main.fragment_pueblos.*
import java.io.Serializable
import java.util.*

class PanaderiasFragment : BaseFragment(), BasicMethods, PanaderiasRecyclerAdapter.ClickPanaderia {

    private lateinit var panaderiasViewModel: PanaderiasViewModel
    private var idPueblo: Int? = null

    var listPanaderias: List<PanaderiasEntity>? = null
    var adapterPanaderia: PanaderiasRecyclerAdapter = PanaderiasRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        panaderiasViewModel = ViewModelProvider(this).get(PanaderiasViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_panaderias, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_panaderia)
        panaderiasViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        if (bundle != null) {
            try {
                idPueblo = bundle.getInt("pueblo")
            } catch (e: Exception) {
                Log.e("errorBundle", e.message.toString())
                Toast.makeText(context, "Error al captar el pueblo seleccionado", Toast.LENGTH_SHORT).show()
            }
        }

        initObservables()
        init()
        initListeners()
    }

    override fun initObservables() {

        panaderiasViewModel?.panaderiasList?.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { list ->
                list.let {
                    setUpRecyclerView(it)
                } ?: run {
                    print("lista nula")
                }
            }
        )

        panaderiasViewModel?.panaderiasPerPuebloList?.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { list ->
                list.let {
                    setUpRecyclerView(it)
                } ?: run {
                    print("lista nula")
                }
            })

    }

    private fun setUpRecyclerView(list: List<PanaderiasEntity>?) {
        val ctx = getActivity()?.applicationContext
        listPanaderias = list

        for (l in listPanaderias!!) {
            Log.e("panaderia", l.nombre.toString())
        }

        listaPanaderias_recyclerView_panaderiasFragment?.setHasFixedSize(true)
        listaPanaderias_recyclerView_panaderiasFragment.removeAllViewsInLayout()
        listaPanaderias_recyclerView_panaderiasFragment?.layoutManager = LinearLayoutManager(ctx)
        listaPanaderias_recyclerView_panaderiasFragment?.layoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        ctx?.let {
            adapterPanaderia.PanaderiasRecyclerAdapter(
                listPanaderias as ArrayList<PanaderiasEntity>,
                this
            )
        }
        listaPanaderias_recyclerView_panaderiasFragment?.adapter = adapterPanaderia
    }

    override fun init() {
        if (idPueblo == null) {
            panaderiasViewModel.getPanaderias()
        } else {
            panaderiasViewModel.getPanaderiasPerPueblo(idPueblo!!)
        }
    }

    override fun initListeners() {

    }

    override fun onClickPanaderia(position: Int) {
        Toast.makeText(context, listPanaderias?.get(position)?.nombre, Toast.LENGTH_SHORT).show()
    }
}