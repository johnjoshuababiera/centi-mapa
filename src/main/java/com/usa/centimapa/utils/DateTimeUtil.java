/*
 * Copyright (c) 2018 Total SoftTech Solutions, Inc. All rights reserved. All other trademarks and copyrights referred to herein are the property of their respective holders. No part of this code may be reproduced in any form or by any means or used to take any derivative work, without written permission from Total SoftTech Solutions, Inc.
 */

package com.usa.centimapa.utils;

import java.util.Calendar;

public class DateTimeUtil {

    public static Long getEndOfDay(Long millis){
        if(millis==null){
            return null;
        }
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(millis);
        date.set(Calendar.HOUR_OF_DAY, date.getActualMaximum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMaximum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMaximum(Calendar.SECOND));
        long endOfDay = date.getTimeInMillis();
        return endOfDay;
    }

    public static Long getStartOfDAy(Long millis){
        if(millis==null){
            return null;
        }
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(millis);
        date.set(Calendar.HOUR_OF_DAY, date.getActualMinimum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMinimum(Calendar.SECOND));
        long startOfDay = date.getTimeInMillis();
        return startOfDay;
    }

}
