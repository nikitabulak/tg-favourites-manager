package com.nktbul.tgfavouritesmanager.messagesloader;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LoadController {
    private final LoadService loadService;

    @GetMapping("/load")
    public LoadResponse loadChatMessages() {
        return loadService.loadChatMessages();
    }

    @GetMapping("/authorize")
    public LoadResponse authorize(@RequestParam String phoneNumber) {
        return loadService.authorize(phoneNumber);
    }

    @GetMapping("/send_code")
    public LoadResponse sendAuthorizationCode(@RequestParam String code) {
        return loadService.sendAuthorizationCode(code);
    }

    @GetMapping("/logout")
    public LoadResponse logout(){
        return loadService.logout();
    }

}
