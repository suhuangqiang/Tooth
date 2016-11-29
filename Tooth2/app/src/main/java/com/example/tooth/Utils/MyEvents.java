package com.example.tooth.Utils;

import android.util.Log;

import com.example.tooth.Entity.Code;
import com.example.tooth.Message.ActivityListMessage;
import com.example.tooth.Message.AddressListMessage;
import com.example.tooth.Message.AdvMessage;
import com.example.tooth.Message.AppointmentListMessage;
import com.example.tooth.Message.BaseMessage;
import com.example.tooth.Message.BookMessage;
import com.example.tooth.Message.CodeMessage;
import com.example.tooth.Message.CollectionMessage;
import com.example.tooth.Message.ConsultListMessage;
import com.example.tooth.Message.DoctorMessage;
import com.example.tooth.Message.DynamicMessage;
import com.example.tooth.Message.FamilyDetailMessage;
import com.example.tooth.Message.FamilyListMessage;
import com.example.tooth.Message.HospitalListMessage;
import com.example.tooth.Message.LoginMessage;
import com.example.tooth.Message.OrderListMessage;
import com.example.tooth.Message.PhotoMessage;
import com.example.tooth.Message.ProjectMessage;
import com.example.tooth.Message.QuestionMessage;
import com.example.tooth.Message.RecordMessage;
import com.example.tooth.Message.ScoreMessage;
import com.example.tooth.Message.SearchHospMessage;
import com.example.tooth.Message.SignMessage;
import com.example.tooth.Message.TimeMessage;
import com.example.tooth.Message.UserMessage;
import com.google.gson.Gson;

/**
 * Created by shq1 on 2016/11/2.
 * EventBus选择器，根据type判断返回类型
 */

public class MyEvents {
    private static MyEvents myEvents;
    public static MyEvents getInstace(){
        if (myEvents == null){
            myEvents = new MyEvents();
        }
        return myEvents;
    }

    public Object getMode(int type,String result){
        Gson gson = new Gson();
        BaseMessage baseMessage = gson.fromJson(result,BaseMessage.class);
        switch (type){
            case ModeEnum.CODE:
                CodeMessage codeMessage = gson.fromJson(result,CodeMessage.class);
                return codeMessage;
            case ModeEnum.LOGIN:
                UserMessage userMessage = gson.fromJson(result,UserMessage.class);
                return userMessage;
            case ModeEnum.FAMILY_LIST:
                FamilyListMessage familyListMessage = gson.fromJson(result,FamilyListMessage.class);
                return familyListMessage;
            case ModeEnum.ADD_FAMILY:
                return baseMessage;
            case ModeEnum.HOSPITAL_LIST:
                HospitalListMessage hospitalListMessage = gson.fromJson(result,HospitalListMessage.class);
                return hospitalListMessage;
            case ModeEnum.ADDRESS_LIST:
                AddressListMessage addressListMessage = gson.fromJson(result,AddressListMessage.class);
                return addressListMessage;
            case ModeEnum.DOC_LIST:
                DoctorMessage doctorMessage = gson.fromJson(result,DoctorMessage.class);
                return doctorMessage;
            case ModeEnum.ACTIVITY_LIST:
                ActivityListMessage activityListMessage = gson.fromJson(result,ActivityListMessage.class);
                return activityListMessage;
            case ModeEnum.MY_APPOINTMENT:
                AppointmentListMessage appointmentListMessage = gson.fromJson(result,AppointmentListMessage.class);
                appointmentListMessage.setType(type);
                return appointmentListMessage;
            case ModeEnum.MY_APPOINTMENT_ING:
                AppointmentListMessage appointmentListMessage1 = gson.fromJson(result,AppointmentListMessage.class);
                appointmentListMessage1.setType(type);
                return appointmentListMessage1;
            case ModeEnum.ORDER_LIST_1:
            case ModeEnum.ORDER_LIST_2:
            case ModeEnum.ORDER_LIST_3:
            case ModeEnum.ORDER_LIST_4:
            case ModeEnum.ORDER_LIST_5:
            case ModeEnum.ORDER_LIST:
                OrderListMessage orderListMessage = gson.fromJson(result,OrderListMessage.class);
                orderListMessage.setType(type);
                return orderListMessage;
            case ModeEnum.FRIENDS_LIST:
                DynamicMessage dynamicMessage = gson.fromJson(result,DynamicMessage.class);
                dynamicMessage.setType(type);
                return dynamicMessage;
            case ModeEnum.SEARCH_HOSPITAL:
                SearchHospMessage searchHospMessage = gson.fromJson(result,SearchHospMessage.class);
                return searchHospMessage;
            case ModeEnum.TIME_LIST:
                TimeMessage timeMessage = gson.fromJson(result,TimeMessage.class);
                return timeMessage;
            case ModeEnum.PROJECT_LIST:
                ProjectMessage projectMessage = gson.fromJson(result,ProjectMessage.class);
                return projectMessage;
            case ModeEnum.SCORE_DETAIL:
                ScoreMessage scoreMessage = gson.fromJson(result,ScoreMessage.class);
                return scoreMessage;
            case ModeEnum.ADV_LIST0:
            case ModeEnum.ADV_LIST1:
            case ModeEnum.ADVLIST_3:
            case ModeEnum.ADV_LIST2:
                AdvMessage advMessage = gson.fromJson(result,AdvMessage.class);
                advMessage.setType(type);
                return advMessage;
            case ModeEnum.COLLECT_LIST_0:
            case ModeEnum.COLLECT_LIST_1:
            case ModeEnum.COLLECT_LIST_2:
                CollectionMessage collectionMessage = gson.fromJson(result,CollectionMessage.class);
                collectionMessage.setType(type);
                return collectionMessage;
            case ModeEnum.UPLOAD_Friends_PIC:
                PhotoMessage photoMessage = gson.fromJson(result,PhotoMessage.class);
                photoMessage.setType(type);
                return photoMessage;
            case ModeEnum.LOOK_PROJECT_LIST:
                ProjectMessage projectMessage1 = gson.fromJson(result,ProjectMessage.class);
                projectMessage1.setType(type);
                return projectMessage1;
            case ModeEnum.QUESTION_LIST:
                QuestionMessage questionMessage = gson.fromJson(result,QuestionMessage.class);
                questionMessage.setType(type);
                return questionMessage;
            case ModeEnum.CONSULT_LIST_0:
                ConsultListMessage consultListMessage = gson.fromJson(result,ConsultListMessage.class);
                consultListMessage.setType(type);
                return consultListMessage;
            case ModeEnum.CONSULT_LIST_1:
                ConsultListMessage consultListMessage1 = gson.fromJson(result,ConsultListMessage.class);
                consultListMessage1.setType(type);
                return consultListMessage1;
            case ModeEnum.DOOR_SIGN:
            case ModeEnum.SIGN:
                SignMessage signMessage = gson.fromJson(result,SignMessage.class);
                signMessage.setType(type);
                return signMessage;
            case ModeEnum.FAMILY_DETAIL:
                FamilyDetailMessage familyDetailMessage = gson.fromJson(result,FamilyDetailMessage.class);
                return familyDetailMessage;
            case ModeEnum.RECORD_DETAIL:
                RecordMessage recordMessage = gson.fromJson(result,RecordMessage.class);
                return recordMessage;
            case ModeEnum.UPDATE_BOOK:
            case ModeEnum.ORDER:
                BookMessage bookMessage = gson.fromJson(result,BookMessage.class);
                bookMessage.setType(type);
                Log.i("order","order or update event "+type);
                return bookMessage;
            default:
                Log.i("friends","myEvent");
                baseMessage.setType(type);
                return baseMessage;
        }
    }
}
