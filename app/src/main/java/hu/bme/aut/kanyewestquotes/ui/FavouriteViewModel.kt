package hu.bme.aut.kanyewestquotes.ui

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.kanyewestquotes.data.Quote
import hu.bme.aut.kanyewestquotes.model.FavouriteQuote
import hu.bme.aut.kanyewestquotes.model.RandomQuote
import hu.bme.aut.kanyewestquotes.repository.MainRepository
import hu.bme.aut.kanyewestquotes.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class FavouriteViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<List<FavouriteQuote>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<FavouriteQuote>>>
        get() = _dataState

    fun setStateEvent(favouriteStateEvent: FavouriteStateEvent) {
        viewModelScope.launch {
            when (favouriteStateEvent) {
                is FavouriteStateEvent.GetFavouriteQuotes -> {
                    mainRepository.getFavouriteQuotes()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is FavouriteStateEvent.DeleteFavouriteQuote -> {
                    mainRepository.deleteFavouriteQuote(
                        Quote(
                            favouriteStateEvent.favouriteQuote.id,
                            favouriteStateEvent.favouriteQuote.quote
                        )
                    )
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                FavouriteStateEvent.None -> {
                }
            }
        }
    }
}


sealed class FavouriteStateEvent {
    object GetFavouriteQuotes : FavouriteStateEvent()
    object None : FavouriteStateEvent()
    data class DeleteFavouriteQuote(val favouriteQuote: FavouriteQuote) : FavouriteStateEvent()
}
