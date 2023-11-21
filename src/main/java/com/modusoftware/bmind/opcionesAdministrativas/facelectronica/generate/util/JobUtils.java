package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.util;

import org.springframework.batch.core.JobExecution;

public class JobUtils {
	
	public static String getJobExecutionSummary(JobExecution je) {
        StringBuilder sb = new StringBuilder()
                .append(" JobConfigurationName: ").append(je.getJobConfigurationName())
                //.append(" CreateTime: ").append(je.getCreateTime())
                //.append(" EndTime: ").append(je.getEndTime())
                .append(" Id: ").append(je.getId())
                .append(" JobId: ").append(je.getJobId())
                .append(" JobName: ").append(je.getJobInstance().getJobName())
                //.append(" StartTime: ").append(je.getStartTime())
                .append(" Status: ").append(je.getStatus().name());
        
        return sb.toString();
    }

}
