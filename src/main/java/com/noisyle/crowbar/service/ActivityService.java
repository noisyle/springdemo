package com.noisyle.crowbar.service;

import com.noisyle.crowbar.model.Activity;
import com.noisyle.crowbar.repository.ActivityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("activityService")
public class ActivityService {
    @Autowired
    private ActivityDao activityDao;

    public List<Activity> list() {
        return activityDao.list();
    }

    public void save(Activity activity) {
        activityDao.save(activity);
    }
    public void delete(Long id) {
        Activity activity = activityDao.get(id);
        activity.setStatus(0);
        activityDao.save(activity);
    }

    public Activity get(Long id) {
        return activityDao.get(id);
    }
}
