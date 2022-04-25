package dt.prod.patternvm.cinemaView.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dt.prod.patternvm.cinemaView.domain.CinemaViewRepository
import kotlinx.coroutines.launch
import dt.prod.patternvm.core.model.Event
import javax.inject.Inject

@HiltViewModel
class CinemaViewViewModel @Inject constructor(private val Repository: CinemaViewRepository) :
    ViewModel() {

    //var cinemaViewRequest = CinemaViewRequest()
    val cinemaViewResponse: MutableLiveData<Event<List<CinemaOffsetModel>>> = MutableLiveData()
    fun createEvent(offset: String) {
        cinemaViewResponse.value = Event.loading()
        viewModelScope.launch {
            cinemaViewResponse.value = Repository.createEvent(offset)
        }
    }
}