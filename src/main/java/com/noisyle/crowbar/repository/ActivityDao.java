package com.noisyle.crowbar.repository;

import com.noisyle.crowbar.core.base.AbstractDao;
import com.noisyle.crowbar.model.Activity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("activityDao")
public class ActivityDao extends AbstractDao<Activity, Long> {
    public List<Activity> list() {
        List<Activity> list = createQuery("from Activity where status=1").list();
        return list;
    }
}
