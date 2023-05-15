package com.nktbul.tgfavouritesmanager.messagesloader;

import lombok.Data;
import org.drinkless.tdlib.TdApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Data
public class ThreadBuffer {
    private Lock lock;
    private Condition condition;
    private List<TdApi.Message> messages;
    private boolean gotMessages;
    private boolean requiredMessages;
    private boolean requiredLogout;
    private boolean sendCode;
    private String phoneNumber;
    private boolean authorizationRequired;
    private String authCode;
    private volatile boolean authCodeRequired;
    private volatile boolean authCodeReady;

    public ThreadBuffer(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
        this.messages = new ArrayList<>();
        this.phoneNumber = "";
        this.authCode = "";
    }

    public void authorize(String number) {
        this.phoneNumber = number;
        this.authorizationRequired = true;
    }

    public void resetNumber() {
        this.phoneNumber = "";
        this.authorizationRequired = false;
    }

    public void resetMessages() {
        this.messages = new ArrayList<>();
        this.gotMessages = false;
    }

    public void resetLogout() {
        this.requiredLogout = false;
    }

    public void resetSendCode() {
        this.authCode = "";
    }
}
