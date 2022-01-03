package gst.trainingcourse_ex11_thaonx4.mockanimation.db

import androidx.lifecycle.LiveData
import androidx.room.*
import gst.trainingcourse_ex11_thaonx4.mockanimation.model.Item
import gst.trainingcourse_ex11_thaonx4.mockanimation.utils.Constants


@Dao
interface BudgetDao {

    @Query("SELECT * FROM ${Constants.TABLE_NAME}")
    fun getAllBudget(): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(item: Item)

    @Update
    suspend fun updateBudget(item: Item)
}