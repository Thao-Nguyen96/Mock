package gst.trainingcourse_ex11_thaonx4.mockanimation.repository

import android.app.Application
import androidx.lifecycle.LiveData
import gst.trainingcourse_ex11_thaonx4.mockanimation.db.BudgetDatabase
import gst.trainingcourse_ex11_thaonx4.mockanimation.model.Item

class BudgetRepository(application: Application) {

    private val budgetDatabase = BudgetDatabase.getInstance(application)

    suspend fun insertBudget(item: Item) = budgetDatabase.getBudgetDao().insertBudget(item)

    suspend fun updateBudget(item: Item) = budgetDatabase.getBudgetDao().updateBudget(item)

    fun getAllBudget(): LiveData<List<Item>> = budgetDatabase.getBudgetDao().getAllBudget()
}