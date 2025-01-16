package com.hamter.chatbot;

import lombok.Data;

@Data
public class TelegramMessage {
    private Long message_id;
    private TelegramUser from;
    private TelegramChat chat;
    private String text;

    
}

