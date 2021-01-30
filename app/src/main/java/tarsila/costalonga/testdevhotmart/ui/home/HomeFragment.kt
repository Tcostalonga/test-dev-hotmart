package tarsila.costalonga.testdevhotmart.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.testdevhotmart.R
import tarsila.costalonga.testdevhotmart.databinding.FragmentHomeBinding
import tarsila.costalonga.testdevhotmart.model.Images
import tarsila.costalonga.testdevhotmart.utils.Status

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    private var adapter: LocationsAdapter = LocationsAdapter()


    var qntItensLocations: Int = 0
    var arrayOfImgs = Images()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("HomeFragment", "Chamou on create")

        viewModel.locations.observe(this, Observer {
            adapter.data = it.listLocations

            //Pegar num de itens da lista de locais e pesquisar a msm qnt em imagens
            qntItensLocations = it.listLocations.size

            if (qntItensLocations > 3) {
                viewModel.requestImages(qntItensLocations)
            }
        })

        viewModel.images.observe(this, Observer {
            arrayOfImgs = it
            adapter.data.forEachIndexed { index, locations ->
                locations.img = it.hits[index].imgURL
            }
            adapter.notifyDataSetChanged()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        Log.i("HomeFragment", "Chamou on create view ")

        setRecyclerView()
        controlItemsViewVisibility()

        return binding.root
    }


    private fun controlItemsViewVisibility() {

        viewModel.statusRequest.observe(viewLifecycleOwner, Observer {
            when (it) {
                Status.ERROR -> {
                    binding.errorLytHome.pgBar.visibility = View.GONE
                    binding.recView.visibility = View.GONE
                    binding.errorLytHome.imgError.visibility = View.VISIBLE
                    binding.errorLytHome.txtError.visibility = View.VISIBLE

                    viewModel.msgHome?.let { msg ->
                        binding.errorLytHome.txtError.text = msg

                        when (viewModel.msgHome) {
                            getString(R.string.EMPTY_INVALID_REQUEST) -> binding.errorLytHome.imgError.setImageResource(
                                R.drawable.ic_lupa_quebrada
                            )
                            getString(R.string.NOT_FOUND_REQUEST) -> binding.errorLytHome.imgError.setImageResource(
                                R.drawable.ic_lupa_quebrada
                            )
                            getString(R.string.NOT_CONNECTED_REQUEST) -> binding.errorLytHome.imgError.setImageResource(
                                R.drawable.ic_wifi_off
                            )
                        }
                    }
                }
                Status.SUCCESS -> {
                    binding.recView.visibility = View.VISIBLE

                    binding.errorLytHome.pgBar.visibility = View.GONE
                    binding.errorLytHome.imgError.visibility = View.GONE
                    binding.errorLytHome.txtError.visibility = View.GONE
                }
                else -> binding.errorLytHome.pgBar.visibility = View.VISIBLE
            }
        })
    }

    private fun setRecyclerView() {
        binding.recView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recView.adapter = adapter

        adapter.clicksAcao = object : ClicksAcao {
            override fun onClick(id: Int, oneImg: String?) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                        id, arrayOfImgs, oneImg
                    )
                )
            }
        }
    }


}
