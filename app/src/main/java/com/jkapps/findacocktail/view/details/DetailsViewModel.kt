package com.jkapps.findacocktail.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkapps.findacocktail.data.Repository
import com.jkapps.findacocktail.data.Result
import com.jkapps.findacocktail.model.CocktailDetails
import com.jkapps.findacocktail.utils.Event
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: Repository) : ViewModel() {
    private val cocktailLiveData : MutableLiveData<CocktailDetails> = MutableLiveData()
    private val showErrorLiveData: MutableLiveData<Event<Unit>> = MutableLiveData()

    val cocktail : LiveData<CocktailDetails>
        get() = cocktailLiveData
    val showError : LiveData<Event<Unit>>
        get() = showErrorLiveData

    fun getCocktail(id : Int) {
        if (cocktailLiveData.value != null) return
        viewModelScope.launch {
            val result = repository.getCocktailById(id)
            when (result) {
                is Result.Success -> cocktailLiveData.postValue(result.data)
                is Result.Error -> showErrorLiveData.postValue(Event(Unit))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}