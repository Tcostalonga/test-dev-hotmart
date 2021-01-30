package tarsila.costalonga.testdevhotmart.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.schedules_dialog.view.*
import tarsila.costalonga.testdevhotmart.R
import tarsila.costalonga.testdevhotmart.databinding.FragmentDetailsBinding
import tarsila.costalonga.testdevhotmart.model.Images
import tarsila.costalonga.testdevhotmart.utils.Status
import tarsila.costalonga.testdevhotmart.utils.getRandomColor

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var binding: FragmentDetailsBinding

    var idDetail = 0

    var arrayOfImgsDetails = Images()

    var oneImg: String? = null

    private var adapter: ImagesAdapter = ImagesAdapter()

    private lateinit var layoutDialog: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setItemsOnUI()
        controlItemsViewVisibilityDetail()
        Log.i("DetailsFragment", "Chamou on create")

        val args = DetailsFragmentArgs.fromBundle(requireArguments())
        idDetail = args.id
        arrayOfImgsDetails = args.imgDetails
        oneImg = args.oneImg
        adapter.imgsArray = arrayOfImgsDetails

        viewModel.requestDetails(idDetail)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        setRecyclerView()
        showScheduleDialog()
        toolbarButtonsSetup()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutDialog =
            LayoutInflater.from(requireContext()).inflate(R.layout.schedules_dialog, null)
    }

    private fun toolbarButtonsSetup() {
        binding.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.share -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.share_text),
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                else -> false
            }
        }

    }

    private fun showScheduleDialog() {

        binding.bodyLytDetails.schedulesDetails.setOnClickListener {

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

    private fun setItemsOnUI() {

        viewModel.detailLocation.observe(this, Observer {

            binding.titleBarLytDetails.nameDetails.text = it.name
            binding.titleBarLytDetails.reviewDetails.text = it.review.toString()
            binding.bodyLytDetails.phoneDetails.text = it.phone
            binding.bodyLytDetails.aboutDetails.text = it.about
            binding.bodyLytDetails.addressDetails.text = it.address
            setImage()
            setStarsColors(it.review)
        })
    }

    private fun setImage() {
        oneImg?.let {
            val imgUri = (oneImg ?: "").toUri().buildUpon().scheme("https").build()
            Picasso.get()
                .load(imgUri)
                .error(getRandomColor())
                .into(binding.detailImage)
        }
    }

    private fun setStarsColors(review: Float) {
        when (review) {

            in 0.1f..1.9f -> {
                binding.titleBarLytDetails.star1Details.setImageResource(R.drawable.ic_s_on)
            }
            in 2.0f..2.9f -> {
                binding.titleBarLytDetails.star1Details.setImageResource(R.drawable.ic_s_on)
                binding.titleBarLytDetails.star2Details.setImageResource(R.drawable.ic_s_on)
            }
            in 3.0f..3.9f -> {
                binding.titleBarLytDetails.star1Details.setImageResource(R.drawable.ic_s_on)
                binding.titleBarLytDetails.star2Details.setImageResource(R.drawable.ic_s_on)
                binding.titleBarLytDetails.star3Details.setImageResource(R.drawable.ic_s_on)
            }
            in 4.0f..4.9f -> {
                binding.titleBarLytDetails.star1Details.setImageResource(R.drawable.ic_s_on)
                binding.titleBarLytDetails.star2Details.setImageResource(R.drawable.ic_s_on)
                binding.titleBarLytDetails.star3Details.setImageResource(R.drawable.ic_s_on)
                binding.titleBarLytDetails.star4Details.setImageResource(R.drawable.ic_s_on)
            }
            5.0f -> {
                binding.titleBarLytDetails.star1Details.setImageResource(R.drawable.ic_s_on)
                binding.titleBarLytDetails.star2Details.setImageResource(R.drawable.ic_s_on)
                binding.titleBarLytDetails.star3Details.setImageResource(R.drawable.ic_s_on)
                binding.titleBarLytDetails.star4Details.setImageResource(R.drawable.ic_s_on)
                binding.titleBarLytDetails.star5Details.setImageResource(R.drawable.ic_s_on)
            }
        }
    }


    private fun controlItemsViewVisibilityDetail() {

        viewModel.statusRequestDetail.observe(this, Observer {
            when (it) {
                Status.SUCCESS -> {
                    binding.nestedDetails.visibility = View.VISIBLE
                    binding.errorLytDetails.pgBar.visibility = View.GONE
                    binding.errorLytDetails.imgError.visibility = View.GONE
                    binding.errorLytDetails.txtError.visibility = View.GONE

                }
                Status.ERROR -> {

                    binding.errorLytDetails.pgBar.visibility = View.GONE
                    binding.nestedDetails.visibility = View.GONE
                    binding.errorLytDetails.txtError.visibility = View.VISIBLE

                    binding.errorLytDetails.txtError.visibility = View.VISIBLE

                    viewModel.msgDetail?.let { msg ->
                        binding.errorLytDetails.txtError.text = msg

                        when (viewModel.msgDetail) {
                            getString(R.string.EMPTY_INVALID_REQUEST) -> binding.errorLytDetails.imgError.setImageResource(
                                R.drawable.ic_lupa_quebrada
                            )
                            getString(R.string.NOT_FOUND_REQUEST) -> binding.errorLytDetails.imgError.setImageResource(
                                R.drawable.ic_lupa_quebrada
                            )
                            getString(R.string.NOT_CONNECTED_REQUEST) -> binding.errorLytDetails.imgError.setImageResource(
                                R.drawable.ic_wifi_off
                            )
                        }
                    }
                }
                else -> binding.errorLytDetails.pgBar.visibility = View.VISIBLE
            }
        })
    }

    private fun setRecyclerView() {
        binding.bodyLytDetails.rcviewHorizontal.layoutManager =
            GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        binding.bodyLytDetails.rcviewHorizontal.adapter = adapter


    }
}





