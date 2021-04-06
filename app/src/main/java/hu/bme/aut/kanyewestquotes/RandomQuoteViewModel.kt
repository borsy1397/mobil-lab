package hu.bme.aut.kanyewestquotes

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.kanyewestquotes.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class RandomQuoteViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

}


sealed class RandomQuoteStateEvent {

}
