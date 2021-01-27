package tarsila.costalonga.testdevhotmart.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.error_layout.view.*
import kotlinx.android.synthetic.main.schedules_dialog.view.*
import tarsila.costalonga.testdevhotmart.MainActivity
import tarsila.costalonga.testdevhotmart.R
import tarsila.costalonga.testdevhotmart.databinding.FragmentDetailsBinding
import tarsila.costalonga.testdevhotmart.utils.EMPTY_INVALID_REQUEST
import tarsila.costalonga.testdevhotmart.utils.NOT_CONNECTED_REQUEST
import tarsila.costalonga.testdevhotmart.utils.NOT_FOUND_REQUEST
import tarsila.costalonga.testdevhotmart.utils.Status

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var binding: FragmentDetailsBinding

    var idDetail = 0

    private lateinit var layoutDialog: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar?.hide()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        // binding.lifecycleOwner = viewLifecycleOwner
        //  binding.view = viewModel

        idDetail = DetailsFragmentArgs.fromBundle(requireArguments()).id
        Log.i("HomeFragment", "$idDetail")

        controlItemsViewVisibilityDetail()
        setItemsOnUI()

        //  (requireActivity() as MainActivity).supportActionBar?.hide()

        binding.backArrowDetails.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.shareDetails.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.share_text),
                Toast.LENGTH_SHORT
            ).show()
        }

        showScheduleDialog()
        return binding.root
    }

    private fun showScheduleDialog() {

        binding.schedulesDetails.setOnClickListener {

            val layoutDialog =
                LayoutInflater.from(requireContext()).inflate(R.layout.schedules_dialog, null)

            val mBuilder = AlertDialog.Builder(requireContext())
                .setView(layoutDialog)
                .setTitle(getString(R.string.schedule_time))

            viewModel.detailLocation.observe(viewLifecycleOwner, Observer {
                layoutDialog.open_monday.text = it.schedule.monday.open
                layoutDialog.close_monday.text = it.schedule.monday.close
                layoutDialog.open_tuesday.text = it.schedule.tuesday.open
                layoutDialog.close_tuesday.text = it.schedule.tuesday.close
                layoutDialog.open_wednesday.text = it.schedule.wednesday.open
                layoutDialog.close_wednesday.text = it.schedule.wednesday.close
                layoutDialog.open_thursday.text = it.schedule.thursday.open
                layoutDialog.close_thursday.text = it.schedule.thursday.close
                layoutDialog.open_friday.text = it.schedule.friday.open
                layoutDialog.close_friday.text = it.schedule.friday.close
                layoutDialog.open_saturday.text = it.schedule.saturday.open
                layoutDialog.close_saturday.text = it.schedule.saturday.close
                layoutDialog.open_sunday.text = it.schedule.sunday.open
                layoutDialog.close_sunday.text = it.schedule.sunday.close
            })

            val mAlertDialog = mBuilder.show()
            layoutDialog.ok_button.setOnClickListener {

                mAlertDialog.dismiss()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestDetails(idDetail)
        layoutDialog =
            LayoutInflater.from(requireContext()).inflate(R.layout.schedules_dialog, null)
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
                    binding.errorLytDetails.pg_bar_home.visibility = View.GONE
                    binding.errorLytDetails.img_error.visibility = View.GONE
                    binding.errorLytDetails.txt_error.visibility = View.GONE

                }
                Status.ERROR -> {

                    binding.errorLytDetails.pg_bar_home.visibility = View.GONE
                    binding.nestedDetails.visibility = View.GONE
                    binding.errorLytDetails.txt_error.visibility = View.VISIBLE

                    binding.errorLytDetails.img_error.visibility = View.VISIBLE

                    viewModel.msgDetail?.let { msg ->
                        binding.errorLytDetails.txt_error.text = msg
                    }
                    when (viewModel.msgDetail) {
                        EMPTY_INVALID_REQUEST -> binding.errorLytDetails.img_error.setImageResource(R.drawable.ic_lupa_quebrada)
                        NOT_FOUND_REQUEST -> binding.errorLytDetails.img_error.setImageResource(R.drawable.ic_lupa_quebrada)
                        NOT_CONNECTED_REQUEST -> binding.errorLytDetails.img_error.setImageResource(R.drawable.ic_wifi_off)
                    }

                }
                else -> binding.errorLytDetails.pg_bar_home.visibility = View.VISIBLE
            }
        })
    }
}





