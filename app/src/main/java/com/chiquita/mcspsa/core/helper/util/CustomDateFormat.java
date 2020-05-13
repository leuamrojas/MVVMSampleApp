/*
 * Copyright (c) 2015. Property of Rafael Ambruster
 */

package com.chiquita.mcspsa.core.helper.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CustomDateFormat {

    public static String getTunelDateTime(Date paramDate) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        return sdfDate.format(paramDate);
    }

    public static String getTunelShortDateTime(Date paramDate) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yy");
        return sdfDate.format(paramDate);
    }

    public static String getDate(String dateString) {

        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            Date date = format1.parse(dateString);
            DateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
            return sdf.format(date);
        } catch (ParseException e) {
            return "XX";
        }
    }

    public static String getDate(Date paramDate) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        String strDate = sdfDate.format(paramDate);
        return strDate;
    }

    public static String getTime(String dateString) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            Date date = format1.parse(dateString);
            DateFormat sdf = new SimpleDateFormat("h:mm a");
            Date netDate = (date);
            return sdf.format(netDate);
        } catch (ParseException e) {
            return "XX";
        }
    }

    public static String getTime(Date paramDate) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        String strDate = sdfDate.format(paramDate);
        return strDate;
    }

    private static final String TIME_ZONE = "UTC";

    public static String completeFormat(Date paramDate) {
        String str = "";
        if (paramDate != null)
            str = format(paramDate, "EEEE, d/MMMMMM/yyyy, h:mm aa").replaceAll("/", " de ");
        return str;
    }

    public static String format(Date paramDate, String paramString) {
        String str = "";
        if (paramDate != null) {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString, new Locale("EN"));
            localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            str = localSimpleDateFormat.format(paramDate);
        }
        return str;
    }

    public static String formatDateOnly(Date paramDate) {
        String str = "";
        if (paramDate != null)
            str = format(paramDate, "EEEE, d/MMMMMM/yyyy").replaceAll("/", " de ");
        return str;
    }

    public static String getCurrentTime(Date paramDate) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdfDate.format(paramDate);
        return strDate;
    }

    public static String getCurrentTimeWOhour(Date paramDate) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdfDate.format(paramDate);
        return strDate;
    }

    public static String getCurrentTimeYMD(Date paramDate) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yy");
        String strDate = sdfDate.format(paramDate);
        return strDate;
    }

    public static Date getDateTime(String paramDate) {
        try {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            return sdfDate.parse(paramDate
                    .toString().trim());
        } catch (Exception ex) {
        }
        return new Date();
    }

    public static Date getCurrentTime(String paramDate) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdfDate.parse(paramDate
                    .toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date getCurrentDate(String paramDate) throws ParseException {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        return sdfDate.parse(paramDate
                .toString().trim());
    }

    public static Date getCurrentDateAUX(String paramDate) throws ParseException {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
        return sdfDate.parse(paramDate
                .toString().trim());
    }


    public static String formatDateOnlyRelativeToCurrentDate(Date paramDate) {
        String str = "";
        if (paramDate != null) {
            try {
                SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                switch ((int) (1L + (localSimpleDateFormat.parse(localSimpleDateFormat.format(new Date())).getTime() - paramDate.getTime()) / 86400000L)) {
                    default:
                        str = format(paramDate, "EEEE, d/MMMMMM/yyyy");
                        return str.replaceAll("/", " of ");
                    case 1:
                        return "Yesterday";
                    case 0:
                }
            } catch (Exception localException) {
                localException.printStackTrace();
            }
            return str;
        } else
            return "Today";
    }

    public static String formatDefaultTimeZone(Date paramDate, String paramString) {
        String str = "";
        if (paramDate != null) {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString, Locale.getDefault());
            localSimpleDateFormat.setTimeZone(TimeZone.getDefault());
            str = localSimpleDateFormat.format(paramDate);
        }
        return str;
    }

    public static String formatWithDefaultTimeZone(Date paramDate, String paramString) {
        String str = "";
        if (paramDate != null) {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString, new Locale("ES"));
            localSimpleDateFormat.setTimeZone(TimeZone.getDefault());
            str = localSimpleDateFormat.format(paramDate);
        }
        return str;
    }

    public static String formateCurrentDate(){
        return new SimpleDateFormat("dd MMM yy", Locale.getDefault()).format(new Date());
    }
}
