package tarsila.costalonga.testdevhotmart.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.testdevhotmart.R
import tarsila.costalonga.testdevhotmart.databinding.FragmentDetailsBinding
import tarsila.costalonga.testdevhotmart.utils.Status

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var binding: FragmentDetailsBinding

    var idDetail = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        idDetail = DetailsFragmentArgs.fromBundle(requireArguments()).id
        Log.i("HomeFragment", "$idDetail")

        controlItemsViewVisibilityDetail()
        setItemsOnUI()

        binding.backArrowDetails.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToHomeFragment())
        }

        binding.shareDetails.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.share_text),
                Toast.LENGTH_SHORT
            ).show()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestDetails(idDetail)

    }

    private fun setItemsOnUI() {

        viewModel.detailLocation.observe(viewLifecycleOwner, Observer {
            binding.nameDetails.text = it.name
            binding.reviewDetails.text = it.review.toString()
            binding.aboutDetails.text = it.about
            binding.phoneDetails.text = it.phone
            binding.addressDetails.text = it.address
            setStarsColors(it.review)


        })
    }

    private fun setStarsColors(review: Float) {
        when (review) {

            in 0.1f..1.9f -> {
                binding.star1Details.setImageResource(R.drawable.ic_s_on)
            }
            in 2.0f..2.9f -> {
                binding.star1Details.setImageResource(R.drawable.ic_s_on)
                binding.star2Details.setImageResource(R.drawable.ic_s_on)
            }
            in 3.0f..3.9f -> {
                binding.star1Details.setImageResource(R.drawable.ic_s_on)
                binding.star2Details.setImageResource(R.drawable.ic_s_on)
                binding.star3Details.setImageResource(R.drawable.ic_s_on)
            }
            in 4.0f..4.9f -> {
                binding.star1Details.setImageResource(R.drawable.ic_s_on)
                binding.star2Details.setImageResource(R.drawable.ic_s_on)
                binding.star3Details.setImageResource(R.drawable.ic_s_on)
                binding.star4Details.setImageResource(R.drawable.ic_s_on)
            }
            5.0f -> {
                binding.star1Details.setImageResource(R.drawable.ic_s_on)
                binding.star2Details.setImageResource(R.drawable.ic_s_on)
                binding.star3Details.setImageResource(R.drawable.ic_s_on)
                binding.star4Details.setImageResource(R.drawable.ic_s_on)
                binding.star5Details.setImageResource(R.drawable.ic_s_on)
            }
        }
    }


private fun controlItemsViewVisibilityDetail() {

    viewModel.statusRequestDetail.observe(viewLifecycleOwner, Observer {
        when (it) {
            Status.SUCCESS -> {
                binding.nestedDetails.visibility = View.VISIBLE
                binding.pgBarDetails.visibility = View.GONE
            }
            Status.ERROR -> {

                binding.pgBarDetails.visibility = View.GONE
                binding.nestedDetails.visibility = View.GONE
                binding.txtErrorDetails.visibility = View.VISIBLE

                viewModel.msgDetail?.let { msg ->
                    binding.txtErrorDetails.text = msg
                }
            }
            else -> binding.pgBarDetails.visibility = View.VISIBLE
        }
    })
}
}





