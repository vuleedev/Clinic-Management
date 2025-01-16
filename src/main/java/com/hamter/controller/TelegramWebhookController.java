package com.hamter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.chatbot.TelegramUpdate;

@RestController
public class TelegramWebhookController {

    @PostMapping("/webhook")
    public ResponseEntity<String> handleTelegramWebhook(@RequestBody TelegramUpdate update) {
        // Lấy thông tin từ update
        String userMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChat().getId();

        // Xử lý tin nhắn (Ví dụ: gửi phản hồi tự động)
        sendMessageToTelegram(chatId, "Phản hồi từ bot: " + userMessage);

        return ResponseEntity.ok("Received");
    }

    private void sendMessageToTelegram(Long chatId, String message) {
        String url = "https://api.telegram.org/bot<7086998947:AAFGPQStmjknarT6zGia8kFEW9O2C1SgIus>/sendMessage?chat_id=" + chatId + "&text=" + message;
        // Thực hiện HTTP request gửi tin nhắn tới Telegram (Sử dụng RestTemplate hoặc HttpClient)
    }
}

