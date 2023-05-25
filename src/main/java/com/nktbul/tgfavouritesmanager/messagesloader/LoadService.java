package com.nktbul.tgfavouritesmanager.messagesloader;

import com.nktbul.tgfavouritesmanager.tgagent.TgAgent;
import com.nktbul.tgfavouritesmanager.tgagent.TgAgentWithBuffer;
import org.drinkless.tdlib.TdApi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoadService {
    private static Thread authThread;
    private static final ThreadBuffer buffer = TgAgentWithBuffer.getBuffer();


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
        authThread = new Thread(new TgAgentWithBuffer());
        authThread.start();
        buffer.authorize(phoneNumber);
        while (!buffer.isAuthCodeRequired()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
        return new LoadResponse("Requires authorization code!");
    }

    public LoadResponse sendAuthorizationCode(String code) {
        System.out.println("Is auth thread alive: " + authThread.isAlive());
        buffer.enterAuthCode(code);
        while (!buffer.isHaveAuthorization()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }
        authThread.interrupt();
        return new LoadResponse("Authorized successfully!");
    }

    public LoadResponse logout(String phoneNumber) {
        authThread = new Thread(new TgAgentWithBuffer());
        authThread.start();
        buffer.logout(phoneNumber);
        while (!buffer.isLoggedOut()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }
        authThread.interrupt();
        return new LoadResponse("Logged out successfully!");
    }

}
