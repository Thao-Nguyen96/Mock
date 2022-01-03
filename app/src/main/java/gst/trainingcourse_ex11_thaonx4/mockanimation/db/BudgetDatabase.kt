package gst.trainingcourse_ex11_thaonx4.mockanimation.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gst.trainingcourse_ex11_thaonx4.mockanimation.model.Item
import gst.trainingcourse_ex11_thaonx4.mockanimation.utils.Constants

@Database(entities = [Item::class], version = 1)
abstract class BudgetDatabase : RoomDatabase() {

    abstract fun getBudgetDao(): BudgetDao

    companion object {
        @Volatile
        private var INSTANCE: BudgetDatabase? = null

        fun getInstance(context: Context): BudgetDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                BudgetDatabase::class.java,
                Constants.DATABASE_NAME
            ).build()
                .also {
                    INSTANCE = it
                }
        }
    }
}