package com.example.tooth.Message;

import com.example.tooth.Entity.BookBack;

/**
 * Created by shq1 on 2016/11/25.
 */

public class BookMessage extends Message {
    private BookBack data;
    private int type;
    public BookBack getData() {
        return data;
    }

    public void setData(BookBack data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
