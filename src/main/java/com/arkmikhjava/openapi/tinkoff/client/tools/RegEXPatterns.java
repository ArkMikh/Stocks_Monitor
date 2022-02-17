package com.arkmikhjava.openapi.tinkoff.client.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegEXPatterns {

    CLOSED_PRICE(Pattern.compile(".*\"c\":([0-9.]+)")),     //Цена
    DATE_YEAR(Pattern.compile(".*\"year\":([0-9]+)")),      //Год
    DATE_MONTH(Pattern.compile(".*\"month\":([0-9]+)")),    //Месяц
    DATE_DAY(Pattern.compile(".*\"day\":([0-9]+)")),        //День
    TIME_HOUR(Pattern.compile(".*\"hour\":([0-9]+)")),      //Час
    TIME_MINUTE(Pattern.compile(".*\"minute\":([0-9]+)"));  //Минута

    private Pattern pattern;

    RegEXPatterns(Pattern pattern) {
        this.pattern = pattern;
    }

    public Matcher matches(String text){
        return pattern.matcher(text);
    }


}
