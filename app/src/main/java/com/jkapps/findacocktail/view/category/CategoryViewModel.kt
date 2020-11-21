package com.jkapps.findacocktail.view.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkapps.findacocktail.data.Repository
import com.jkapps.findacocktail.data.Result
import com.jkapps.findacocktail.model.Category
import com.jkapps.findacocktail.utils.Event
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: Repository) : ViewModel() {
    private val categoriesLiveDate: MutableLiveData<List<Category>> = MutableLiveData()
    private val showErrorLiveData: MutableLiveData<Event<Unit>> = MutableLiveData()

    val categories: LiveData<List<Category>>
        get() = categoriesLiveDate
    val showError: LiveData<Event<Unit>>
        get() = showErrorLiveData

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            val result = repository.getCategories()
            when (result) {
                is Result.Success -> categoriesLiveDate.postValue(result.data)
                is Result.Error -> showErrorLiveData.postValue(Event(Unit))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}