package com.example.lbelvale.ft_hangouts;

/**
 * Created by lbelvale on 12/23/16.
 */

public class Message {

    private int id;
    private String number;
    private String message;
    private int isSendMessage;
    private String createAt;

    public Message(String number, String message, int isSendMessage, String createAt) {
        this.number = number;
        this.message = message;
        this.isSendMessage = isSendMessage;
        this.createAt = createAt;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSendMessage() {
        return isSendMessage;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setSendMessage(int sendMessage) {
        isSendMessage = sendMessage;
    }

    public String getNumber() {
        return number;
    }

    public String getCreateAt() {
        return createAt;
    }
}
