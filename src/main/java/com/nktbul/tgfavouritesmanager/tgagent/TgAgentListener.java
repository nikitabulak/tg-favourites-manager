package com.nktbul.tgfavouritesmanager.tgagent;

public interface TgAgentListener {
    void authorizationCodeRequired();
    void authorizationSuccessful();
    void logout();
}
