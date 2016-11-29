package com.example.tooth.Message;

import com.example.tooth.Entity.Address;

import java.util.List;

/**
 * Created by shq1 on 2016/11/2.
 */

public class AddressListMessage extends Message {
    private List<Address> data;


    public List<Address> getData() {
        return data;
    }

    public void setData(List<Address> data) {
        this.data = data;
    }
}
