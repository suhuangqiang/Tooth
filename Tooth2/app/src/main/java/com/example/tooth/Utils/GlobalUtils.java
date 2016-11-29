package com.example.tooth.Utils;

import com.baidu.location.BDLocation;
import com.example.tooth.Entity.User;

/**
 * Created by shq1 on 2016/11/2.
 */

public class GlobalUtils {
    public static GlobalUtils globalUtils;
    public User user;
    public BDLocation bdLocation;
    public synchronized static GlobalUtils getInstances(){
        if (globalUtils == null){
            globalUtils = new GlobalUtils();
        }
        return globalUtils;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BDLocation getBdLocation() {
        return bdLocation;
    }

    public void setBdLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
    }
}
