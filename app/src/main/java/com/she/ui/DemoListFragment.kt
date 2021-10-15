package com.she.ui

import Adapter.DemoListAdapter
import Utils.FragmentNavigation
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.she.ui.databinding.FragmentDemoListBinding

class DemoListFragment : Fragment(), FragmentNavigation {

    lateinit var demoListAdapter: DemoListAdapter

    private var _binding: FragmentDemoListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDemoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setView() {
        demoListAdapter = DemoListAdapter(
            arrayListOf(SEHATQ_BUTTON)
        ) { demoName ->
            when (demoName) {
                SEHATQ_BUTTON -> navigateToFragment(ButtonDemoFragment(), true, this.requireActivity(), R.id.main_container)
                else -> println("ELSE")
            }
        }

        with(binding.rvDemoList) {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = demoListAdapter
        }

    }

    private companion object {
        const val SEHATQ_BUTTON = "SehatQ Button"
    }
}