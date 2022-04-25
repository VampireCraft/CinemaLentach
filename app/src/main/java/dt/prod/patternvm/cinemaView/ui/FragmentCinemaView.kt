 package dt.prod.patternvm.cinemaView.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dt.prod.patternvm.core.ui.BaseFragment
import dt.prod.patternvm.cinemaView.models.CinemaViewViewModel
import dt.prod.patternvm.core.model.Status
import dt.prod.patternvm.databinding.FragmentCinemaViewBinding

class FragmentCinemaView : BaseFragment() {
    private lateinit var binding: FragmentCinemaViewBinding
    private lateinit var viewModel: CinemaViewViewModel

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCinemaViewBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[CinemaViewViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureView()
    }

    private fun observeOnMyEvents() {
        viewModel.cinemaViewResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    Log.d("myEvents", "LOADING")
                }

                Status.SUCCESS -> {

                    Log.d("myEvents", "SUCCESS" + it.data)
//                    it.data?.let { events ->
//                        configureMyEvents(events)
//                    }
                }
                Status.ERROR -> {

                    Log.d("myEvents", "ERROR" + it.error)
                }
            }
        }
    }

    private fun configureView() {
        observeOnMyEvents()
        viewModel.createEvent("1")
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }
}