package com.example.panaderosvm._view_ui.pueblos

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.panaderosvm.R
import com.example.panaderosvm._view_ui.Base.BaseFragment
import com.example.panaderosvm._view_ui.Base.BasicMethods
import com.example.panaderosvm._view_ui.panaderias.PanaderiasFragment
import com.example.panaderosvm._view_ui.panaderias.PanaderiasViewModel
import com.example.panaderosvm.model.local.pueblos.PueblosEntity
import kotlinx.android.synthetic.main.fragment_pueblos.*
import java.io.Serializable

class PueblosFragment : BaseFragment(), BasicMethods, PueblosRecyclerAdapter.ClickPueblo {

    private lateinit var pueblosViewModel: PueblosViewModel
    var listPueblos: List<PueblosEntity>? = null
    var adapterPueblo: PueblosRecyclerAdapter = PueblosRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pueblosViewModel = ViewModelProvider(this).get(PueblosViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_pueblos, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_pueblos)
        pueblosViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        initObservables()
        initListeners()
    }

    override fun initObservables() {
        pueblosViewModel?.pueblosList?.observe(
            viewLifecycleOwner,
            Observer { list ->

                list?.let {
                    setUpRecyclerView(list)
                } ?: run {
                    print("lista nula")
                }
            })
    }

    override fun init() {
        pueblosViewModel.getPueblos()
    }

    override fun initListeners() {}

    private fun setUpRecyclerView(list: List<PueblosEntity>?) {
        val ctx = getActivity()?.applicationContext
        listPueblos = list

        for (l in listPueblos!!) {
            Log.e("pueblo", l.nombre.toString())
        }

        listaPueblos_recyclerView_pueblosFragmen?.setHasFixedSize(true)
        listaPueblos_recyclerView_pueblosFragmen.removeAllViewsInLayout()
        listaPueblos_recyclerView_pueblosFragmen?.layoutManager = LinearLayoutManager(ctx)
        listaPueblos_recyclerView_pueblosFragmen?.layoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        ctx?.let {
            adapterPueblo.PueblosRecyclerAdapter(
                listPueblos as ArrayList<PueblosEntity>,
                this
            )
        }
        listaPueblos_recyclerView_pueblosFragmen?.adapter = adapterPueblo
    }

    override fun onClickPueblos(position: Int) {
        val args = Bundle()
        listPueblos?.get(position)?.ID?.let { args.putInt("pueblo", it) }
        navigate(R.id.action_nav_pueblos_to_nav_panaderias, args, -1)

        Toast.makeText(context, listPueblos?.get(position)?.nombre, Toast.LENGTH_SHORT).show()
    }
}
