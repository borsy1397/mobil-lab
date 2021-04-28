package hu.bme.aut.kanyewestquotes.ui

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
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
class RandomQuoteViewModel
@Inject
constructor(private val mainRepository: MainRepository, private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<RandomQuote>> = MutableLiveData()

    val dataState: LiveData<DataState<RandomQuote>>
        get() = _dataState

    fun setStateEvent(randomQuoteStateEvent: RandomQuoteStateEvent){
        viewModelScope.launch {
            when(randomQuoteStateEvent){
                is RandomQuoteStateEvent.GetRandomQuote -> {
                    mainRepository.getRandomQuote()
                        .onEach {dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is RandomQuoteStateEvent.AddFavouriteQuote -> {
                    mainRepository.addQuoteToFavourites(randomQuoteStateEvent.randomQuote)
                        .onEach {dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is RandomQuoteStateEvent.ShowFavouriteQuote -> {
                    mainRepository.showFavourite(randomQuoteStateEvent.randomQuote)
                        .onEach {dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                RandomQuoteStateEvent.None -> {}
            }
        }
    }
}


sealed class RandomQuoteStateEvent {
    object GetRandomQuote: RandomQuoteStateEvent()
    object None: RandomQuoteStateEvent()
    data class AddFavouriteQuote(val randomQuote: RandomQuote): RandomQuoteStateEvent()
    data class ShowFavouriteQuote(val randomQuote: RandomQuote): RandomQuoteStateEvent()
}
