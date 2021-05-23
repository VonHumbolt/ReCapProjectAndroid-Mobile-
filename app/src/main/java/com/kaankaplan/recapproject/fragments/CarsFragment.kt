package com.kaankaplan.recapproject.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaankaplan.recapproject.CarsActivity
import com.kaankaplan.recapproject.viewModels.CarsViewModel
import com.kaankaplan.recapproject.R
import com.kaankaplan.recapproject.adapters.ExpandableListAdapter
import com.kaankaplan.recapproject.adapters.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_cars.*

class CarsFragment : Fragment(R.layout.fragment_cars) {

    lateinit var viewModel : CarsViewModel
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    lateinit var listGroup : ArrayList<String>
    var listChild: HashMap<String, ArrayList<String>> = HashMap()
    val brandsList : ArrayList<String> = ArrayList()
    val colorsList: ArrayList<String> = ArrayList()

    lateinit var expandableListAdapter: ExpandableListAdapter

    lateinit var toggle: ActionBarDrawerToggle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as CarsActivity).viewModel

        toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        (activity as CarsActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listGroup = ArrayList()
        listGroup.add("Brands")
        listGroup.add("Colors")

        initAdapter()

        recyclerViewAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("carDto", it)
            }
            findNavController().navigate(R.id.action_carsFragment_to_detailFragment, bundle)
        }

        viewModel.carsDto.observe(viewLifecycleOwner, Observer { carsDtoResponse ->
            carsDtoResponse?.let {
                recyclerViewAdapter.differ.submitList(it.data.toList())
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        setExpandableListWithBrandsAndColors()
        setHasOptionsMenu(true)
    }

    private fun setExpandableListWithBrandsAndColors() {
        val brandHash = HashMap<String, Int>()
        val colorHash = HashMap<String, Int>()

        viewModel.brands.observe(viewLifecycleOwner, Observer {
            it?.let {
                for (i in it.data){
                    brandHash[i.brandName] = i.brandId
                    brandsList.add(i.brandName)
                }
                listChild.put(listGroup[0], brandsList)
            }
        })

        viewModel.colors.observe(viewLifecycleOwner, Observer {listResponseModel ->
            listResponseModel?.let {
                for (i in it.data) {
                    colorHash[i.colorName] = i.colorId
                    colorsList.add(i.colorName)
                }

                listChild.put(listGroup[1], colorsList)

                expandableListAdapter = ExpandableListAdapter(listGroup, listChild)
                expandableListView.setAdapter(expandableListAdapter)


                expandableListView.setOnChildClickListener { expandableListView, view, i, i2, l ->

                    val selectedGroup = expandableListAdapter.getGroup(i)
                    val selectedChildName = expandableListAdapter.getChild(i, i2).toString()


                    when(selectedGroup) {
                        "Brands" -> {
                            val brandId = brandHash[selectedChildName]
                            viewModel.getCarsDtoByBrandId(brandId!!)
                        }
                        "Colors" -> {
                            val colorId = colorHash[selectedChildName]
                            viewModel.getCarsDtoByColorId(colorId!!)
                        }
                    }

                    return@setOnChildClickListener true
                }
            }
        })

    }

    private fun isUserLoggedIn() : Boolean{
        val token = viewModel.token.value
        if (token?.data?.token != null) {
            return true
        }
        return false
    }

    private fun initAdapter() {
        recyclerViewAdapter = RecyclerViewAdapter()

        recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.settings, menu)

        if (isUserLoggedIn()) {
            menu.findItem(R.id.loginItem).setTitle("Çıkış Yap")
            menu.findItem(R.id.registerItem).setVisible(false)
        }else{
            menu.findItem(R.id.profileItem).setVisible(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        when(item.itemId) {
            R.id.loginItem -> {
                viewModel.token.postValue(null)
                findNavController().navigate(R.id.action_carsFragment_to_loginFragment)
            }
            R.id.registerItem -> findNavController().navigate(R.id.action_carsFragment_to_registerFragment)
            R.id.profileItem -> findNavController().navigate(R.id.action_carsFragment_to_profileFragment)
        }

        return super.onOptionsItemSelected(item)
    }
}