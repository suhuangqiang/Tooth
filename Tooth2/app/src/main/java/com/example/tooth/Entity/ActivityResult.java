package com.example.tooth.Entity;

/**
 * Created by shq1 on 2016/11/25.
 */

public class ActivityResult {
    public static final int PublicFriends = 0;
    public static final int UpdateBook = 1;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
