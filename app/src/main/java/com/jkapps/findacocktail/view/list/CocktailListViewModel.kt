package com.jkapps.findacocktail.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkapps.findacocktail.data.Repository
import com.jkapps.findacocktail.data.Result
import com.jkapps.findacocktail.model.Cocktail
import com.jkapps.findacocktail.model.Entity
import com.jkapps.findacocktail.utils.Event
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CocktailListViewModel(private val repository: Repository) : ViewModel() {
    private val cocktailsLiveDate: MutableLiveData<List<Cocktail>> = MutableLiveData()
    private val showErrorLiveData: MutableLiveData<Event<Unit>> = MutableLiveData()

    val cocktails : LiveData<List<Cocktail>>
        get() = cocktailsLiveDate
    val showError : LiveData<Event<Unit>>
        get() = showErrorLiveData

    fun getCocktails(entity : Entity) {
        if (cocktails.value != null) return
        viewModelScope.launch {
            val result = repository.getCocktails(entity)
            when (result) {
                is Result.Success -> cocktailsLiveDate.postValue(result.data)
                is Result.Error -> showErrorLiveData.postValue(Event(Unit))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}