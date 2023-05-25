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
    private volatile boolean haveAuthorization;

    private String phoneNumber;
    private volatile boolean phoneNumberReady;
    private String authCode;
    private volatile boolean authRequired;
    private volatile boolean authCodeReady;
    private volatile boolean authCodeRequired;
    private LoadResponse loadResponse;

    private List<TdApi.Message> messages;
    private boolean gotMessages;
    private boolean requiredMessages;
    private boolean logoutRequired;
    private boolean loggedOut;
    
    public ThreadBuffer(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
        this.messages = new ArrayList<>();
        this.phoneNumber = "";
        this.authCode = "";
    }

    public void authorize(String number) {
        this.phoneNumber = number;
        this.phoneNumberReady = true;
        this.authRequired = true;
    }

    public void logout(String number) {
        this.phoneNumber = number;
        this.phoneNumberReady = true;
        this.logoutRequired = true;
    }

    public void enterAuthCode(String code) {
        this.authCode = code;
        this.authCodeReady = true;
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void resetNumberAndAuthCode() {
        this.phoneNumber = "";
        this.phoneNumberReady = false;
        this.authCode = "";
        this.authCodeReady = false;
    }

    public void resetMessages() {
        this.messages = new ArrayList<>();
        this.gotMessages = false;
    }

    public void resetLogout() {
        this.logoutRequired = false;
    }
}
