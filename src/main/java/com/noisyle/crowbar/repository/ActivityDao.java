package com.noisyle.crowbar.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.noisyle.crowbar.core.base.AbstractDao;
import com.noisyle.crowbar.model.Activity;

@Repository("activityDao")
public class ActivityDao extends AbstractDao<Activity, Long> {
    public List<Activity> list() {
        List<Activity> list = find("from Activity where status=1");
        return list;
    }
}
