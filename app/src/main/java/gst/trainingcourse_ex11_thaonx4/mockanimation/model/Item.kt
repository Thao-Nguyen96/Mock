package gst.trainingcourse_ex11_thaonx4.mockanimation.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import gst.trainingcourse_ex11_thaonx4.mockanimation.R
import gst.trainingcourse_ex11_thaonx4.mockanimation.utils.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class Item(
    @ColumnInfo(name = Constants.ID_COLUMN)
    @PrimaryKey
    var id: Int = 0,
    @ColumnInfo(name = Constants.IMAGE_COLUMN)
    var image: Int,
    @ColumnInfo(name = Constants.NAME_COLUMN)
    var name: String,
    @ColumnInfo(name = Constants.PRICE_COLUMN)
    var price: String,
)

object DataProvider {
    fun getData(): List<Item> {

        return arrayListOf(
            Item(0, R.drawable.ic_taxi, "Taxi", "400"),
            Item(0, R.drawable.ic_house, "House", "600"),
            Item(0, R.drawable.ic_cafe, "Cafe", "200"),
            Item(0, R.drawable.ic_gym, "Gym", "300"),
        )
    }
}