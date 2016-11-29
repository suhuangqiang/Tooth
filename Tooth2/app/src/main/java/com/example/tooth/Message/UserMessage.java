package com.example.tooth.Message;

import com.example.tooth.Entity.User;

/**
 * Created by shq1 on 2016/11/2.
 */

public class UserMessage extends Message {

    public User data;


    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
