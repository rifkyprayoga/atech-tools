package com.atech.db.hibernate.tool.data.dto;

import com.atech.db.hibernate.tool.data.defs.DbInitTaskType;

/**
 * Created by andy on 29/11/16.
 */
public class DbInitTaskDto
{

    private String taskName;
    private DbInitTaskType taskType;
    private Integer maxCount;


    public DbInitTaskDto(String taskName, DbInitTaskType taskType)
    {
        this(taskName, taskType, null);
    }


    public DbInitTaskDto(String taskName, DbInitTaskType taskType, Integer maxCount)
    {
        this.taskName = taskName;
        this.taskType = taskType;
        this.maxCount = maxCount;
    }


    public String getTaskName()
    {
        return taskName;
    }


    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }


    public DbInitTaskType getTaskType()
    {
        return taskType;
    }


    public void setTaskType(DbInitTaskType taskType)
    {
        this.taskType = taskType;
    }


    public Integer getMaxCount()
    {
        return maxCount;
    }


    public void setMaxCount(Integer maxCount)
    {
        this.maxCount = maxCount;
    }
}
