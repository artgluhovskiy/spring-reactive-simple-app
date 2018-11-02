package org.art.web.rss;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws ParseException {

//        SimpleDateFormat dateTimeRFC822Formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
//        String dateString = "Thu, 01 Nov 2018 14:07 EDT";
//        String dateString = "Sat, 13 Mar 2010 11:29:05 -0800";
//        Date parsedDate = dateTimeRFC822Formatter.parse(dateString);
//        System.out.println(parsedDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d[d] MMM yyyy HH:mm[:ss] z", Locale.US);
//        String dateString = "Thu, 01 Nov 2018 14:07:34 EDT";
        String dateString = "Fri, 2 Nov 2018 11:25:58 GMT";
        LocalDateTime formatDateTime = LocalDateTime.parse(dateString, formatter);
        System.out.println(formatDateTime);

    }
}
