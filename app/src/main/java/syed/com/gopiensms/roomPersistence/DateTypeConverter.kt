package syed.com.gopiensms.roomPersistence

import androidx.room.TypeConverter
import java.util.*


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class DateTypeConverter {

    @TypeConverter
    fun toDate(value: Long) = Date(value)

    @TypeConverter
    fun fromDate(date: Date) = date.time
}