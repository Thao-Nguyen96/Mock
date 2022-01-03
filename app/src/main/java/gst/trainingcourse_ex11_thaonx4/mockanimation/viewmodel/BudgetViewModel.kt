package gst.trainingcourse_ex11_thaonx4.mockanimation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import gst.trainingcourse_ex11_thaonx4.mockanimation.model.Item
import gst.trainingcourse_ex11_thaonx4.mockanimation.repository.BudgetRepository
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : ViewModel() {

    private var repository: BudgetRepository = BudgetRepository(application)

    fun insertBudget(item: Item) = viewModelScope.launch {
            repository.insertBudget(item)
    }

    fun updateBudget(item: Item) = viewModelScope.launch {
        repository.updateBudget(item)
    }

    fun getAll(): LiveData<List<Item>> = repository.getAllBudget()

    class BudgetViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BudgetViewModel::class.java)) {
                return BudgetViewModel(application) as T
            }
            throw  IllegalArgumentException("unable construct viewModel")
        }

    }
}