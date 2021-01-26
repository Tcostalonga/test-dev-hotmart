package tarsila.costalonga.testdevhotmart.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.testdevhotmart.MainActivity
import tarsila.costalonga.testdevhotmart.R
import tarsila.costalonga.testdevhotmart.databinding.FragmentHomeBinding
import tarsila.costalonga.testdevhotmart.utils.EMPTY_BODY_REQUEST
import tarsila.costalonga.testdevhotmart.utils.NOT_CONNECTED_REQUEST
import tarsila.costalonga.testdevhotmart.utils.NOT_FOUND_REQUEST
import tarsila.costalonga.testdevhotmart.utils.Status

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    lateinit var adapter: LocationsAdapter

    var qntItensLocations: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        Log.i("HomeFragment", "Chamou on create view ")

        (requireActivity() as MainActivity).supportActionBar?.show()

        setRecyclerView()
        controlItemsViewVisibility()

        viewModel.locations.observe(viewLifecycleOwner, Observer {
            adapter.data = it.listLocations

            //Pegar num de itens da lista de locais e pessquisar a msm qnt em imagens
            qntItensLocations = it.listLocations.size

            if (qntItensLocations > 3) {
                viewModel.requestImages(qntItensLocations)
            }

         /*   it.listLocations.forEachIndexed { index, locations ->

                locations.img = viewModel.images.hits[index].imgURL
            }*/






            Log.i("HomeFragment", "${it.listLocations.size}")
        })






        viewModel.requestLocations()
        return binding.root
    }


    private fun controlItemsViewVisibility() {

        viewModel.statusRequest.observe(viewLifecycleOwner, Observer {
            when (it) {
                Status.ERROR -> {
                    binding.pgBarHome.visibility = View.GONE
                    binding.recView.visibility = View.GONE
                    binding.imgError.visibility = View.VISIBLE
                    binding.txtError.visibility = View.VISIBLE

                    viewModel.msg?.let { msg ->
                        binding.txtError.text = msg

                        when (viewModel.msg) {
                            EMPTY_BODY_REQUEST -> binding.imgError.setImageResource(R.drawable.ic_lupa_quebrada)
                            NOT_FOUND_REQUEST -> binding.imgError.setImageResource(R.drawable.ic_lupa_quebrada)
                            NOT_CONNECTED_REQUEST -> binding.imgError.setImageResource(R.drawable.ic_wifi_off)
                        }
                    }
                }
                Status.SUCCESS -> {
                    binding.recView.visibility = View.VISIBLE

                    binding.pgBarHome.visibility = View.GONE
                    binding.imgError.visibility = View.GONE
                    binding.txtError.visibility = View.GONE
                }
                else -> binding.pgBarHome.visibility = View.VISIBLE
            }
        })
    }

    private fun setRecyclerView() {
        adapter = LocationsAdapter()
        binding.recView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recView.adapter = adapter

        adapter.clicksAcao = object : ClicksAcao {
            override fun onClick(id: Int) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id))
            }
        }
    }


}
