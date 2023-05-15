package com.nktbul.tgfavouritesmanager.tgagent;

import com.nktbul.tgfavouritesmanager.messagesloader.ThreadBuffer;

public class TgAgentThread implements Runnable {
    ThreadBuffer buffer;

    public TgAgentThread(ThreadBuffer threadCommander) {
        buffer = threadCommander;
    }

    @Override
    public void run() {
        while (true) {
            if (buffer.isAuthorizationRequired()) {
                buffer.getLock().lock();
                TgAgentWithBuffer.authorize();
            }
        }
    }

    private void authorize(String number) {
    }

    private void logout() {
    }
}
