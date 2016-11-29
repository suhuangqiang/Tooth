package com.example.tooth.Message;

import com.example.tooth.Entity.Score;

import java.util.List;

/**
 * Created by shq1 on 2016/11/8.
 */

public class ScoreMessage extends Message {
    private List<Score> data;

    public List<Score> getData() {
        return data;
    }

    public void setData(List<Score> data) {
        this.data = data;
    }
}
