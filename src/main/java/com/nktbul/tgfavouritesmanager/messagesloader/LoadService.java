package com.nktbul.tgfavouritesmanager.messagesloader;

import com.nktbul.tgfavouritesmanager.tgagent.TgAgent;
import com.nktbul.tgfavouritesmanager.tgagent.TgAgentListener;
import org.drinkless.tdlib.TdApi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class LoadService {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private final Listener listener = new Listener();


    public LoadResponse loadChatMessages() {
        try {
            ArrayList<TdApi.Message> messages = TgAgent.getFavouriteMessages();
            System.out.println("From LoadService.loadChatMessages: " + messages.size());
            return new LoadResponse(messages.size() + " messages loaded successfully!");
        } catch (Exception e) {
            System.out.println("Caught exception from LoadService.loadChatMessages: " + e.getMessage());
            e.printStackTrace();
        }
        return new LoadResponse("Messages was not loaded");
    }

    public LoadResponse authorize(String phoneNumber) {
        TgAgent.authorize(phoneNumber, listener);
        System.out.println("before auth code required");
        while (!listener.authorizationCodeRequired) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("after auth code required");
        listener.authorizationCodeRequired = false;
        return new LoadResponse("Requires authorization code!");
    }

    public LoadResponse sendAuthorizationCode(String code){
        TgAgent.setAuthorizationCode(code);
        while (!listener.authorizationSuccessful){
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
        listener.authorizationSuccessful = false;
        return new LoadResponse("Authorized successfully!");
    }

    public LoadResponse logout(){
        TgAgent.logout();
        while (!listener.logOutSuccessful){
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
        listener.logOutSuccessful = false;
        return new LoadResponse("Logged out successfully!");
    }

    private static class Listener implements TgAgentListener {
        private boolean authorizationCodeRequired = false;
        private boolean authorizationSuccessful = false;
        private boolean logOutSuccessful = false;

        @Override
        public void authorizationCodeRequired() {
            authorizationCodeRequired = true;
//            lock.lock();
//            try {
//                waitAuthorizationCode.signal();
//            } finally {
//                lock.unlock();
//            }
        }

        @Override
        public void authorizationSuccessful() {
            authorizationSuccessful = true;
        }
        @Override
        public void logout() {
            logOutSuccessful = true;
        }
    }

}
