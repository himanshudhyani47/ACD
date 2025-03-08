package com.services.Batch.common.Util;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class JobUtil {

    static AtomicLong batchId = new AtomicLong(System.currentTimeMillis());

    public static String getNewBatchId() {
        return String.valueOf(batchId.getAndIncrement());
    }
    public static Date getRunDate() {
        return new Date();
    }
}
