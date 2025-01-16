package com.hamter.chatbot;

import lombok.Data;

@Data
public class TelegramChat {
    private Long id;
    private String type; // "private", "group", "supergroup", etc.

    
}

