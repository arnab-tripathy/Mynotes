package com.igit.mynotes;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter
{
@TypeConverter
        public static Long ToTimeLong(Date date){

        return date==null?null:date.getTime();

}
@TypeConverter
    public static  Date toDate(Long time){
    return time==null?null:new Date(time);

}
}
