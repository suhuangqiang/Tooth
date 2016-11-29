package com.example.tooth.Message;

import com.example.tooth.Entity.Question;

import java.util.List;

/**
 * Created by shq1 on 2016/11/10.
 */

public class QuestionMessage extends Message {
    private List<Question> data;
    private int type;

    public List<Question> getData() {
        return data;
    }

    public void setData(List<Question> data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
