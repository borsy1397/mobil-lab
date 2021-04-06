package hu.bme.aut.kanyewestquotes

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.kanyewestquotes.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class FavouriteViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

}


sealed class FavouriteStateEvent {

}
