package com.sila.eth.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * *  Created by Adewale Adeleye on 2019-09-21
 **/
public class BaseUtil {
    public static String getEpoch(){
        ZoneId zoneId = ZoneId.of("GMT+1");
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime,zoneId);
        Long epoch = zonedDateTime.toInstant().getEpochSecond() - 100;
        return String.valueOf(epoch);
    }

}




