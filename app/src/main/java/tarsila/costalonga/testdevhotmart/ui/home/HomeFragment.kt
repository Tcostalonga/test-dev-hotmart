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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        Log.i("HomeFragment", "Chamou on create view ")

        setRecyclerView()
        controlItemsViewVisibitily()


        viewModel.locations.observe(viewLifecycleOwner, Observer {
            adapter.data = it.listLocations
            adapter.notifyDataSetChanged()
            Log.i("HomeFragment", "${it.listLocations[0].review}")
        })


        return binding.root
    }


    private fun controlItemsViewVisibitily() {

        viewModel.statusRequest.observe(viewLifecycleOwner, Observer {
            when (it) {
                Status.ERROR -> {
                    binding.pgBar.visibility = View.GONE
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

                    binding.pgBar.visibility = View.GONE
                    binding.imgError.visibility = View.GONE
                    binding.txtError.visibility = View.GONE
                }
                else -> binding.pgBar.visibility = View.VISIBLE


            }
        })
    }

    private fun setRecyclerView() {
        adapter = LocationsAdapter()
        binding.recView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recView.adapter = adapter
    }


    override fun onResume() {
        super.onResume()
        viewModel.requestLocationsAPI()
        Log.i("HomeFragment", "onResumeCalled")


    }

}
