package ru.loftschool.loftblogmoneytracker;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Александр on 26.08.2015.
 */
public class Expense {

    private String title;
    private int sum;
    private Date date;

    public Expense(String title, int sum, Date date){
        this.sum = sum;
        this.title = title;
        this.date = date;
    }

    public String getTitle(){
        return title;
    }
    public String getSum(){ return  Integer.toString(sum); }
    public String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM y", myDateFormatSymbols);
        return  dateFormat.format(date);
    }

    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };
}
