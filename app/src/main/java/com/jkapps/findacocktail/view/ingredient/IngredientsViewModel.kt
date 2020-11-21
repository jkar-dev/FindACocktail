package com.jkapps.findacocktail.view.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkapps.findacocktail.data.Repository
import com.jkapps.findacocktail.data.Result
import com.jkapps.findacocktail.model.Ingredient
import com.jkapps.findacocktail.utils.Event
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class IngredientsViewModel(private val repository: Repository) : ViewModel() {
    private val ingredientsLiveDate: MutableLiveData<List<Ingredient>> = MutableLiveData()
    private val showErrorLiveData: MutableLiveData<Event<Unit>> = MutableLiveData()

    val ingredients: LiveData<List<Ingredient>>
        get() = ingredientsLiveDate
    val showError: LiveData<Event<Unit>>
        get() = showErrorLiveData

    init {
        getIngredients()
    }

    fun getIngredients() {
        viewModelScope.launch {
            val result = repository.getIngredients()
            when (result) {
                is Result.Success -> ingredientsLiveDate.postValue(result.data)
                is Result.Error -> showErrorLiveData.postValue(Event(Unit))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}