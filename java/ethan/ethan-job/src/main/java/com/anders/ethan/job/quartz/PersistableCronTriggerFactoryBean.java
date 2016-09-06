package com.anders.ethan.job.quartz;

import org.springframework.expression.ParseException;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

public class PersistableCronTriggerFactoryBean extends CronTriggerFactoryBean
{

    @Override
    public void afterPropertiesSet() throws ParseException
    {
        super.afterPropertiesSet();
        getJobDataMap().remove(getObject().getJobKey().getName());
    }
}
