package pl.edu.pb.cinemaapp.converters;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class CalendarConverter {
    @TypeConverter
    public static Calendar toCalendar(Long value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    @TypeConverter
    public static Long fromCalendar(Calendar calendar){
        return calendar == null ? null : calendar.getTime().getTime();
    }
}
