package com.hamter.chatbot;

import lombok.Data;

@Data
public class TelegramUpdate {
    private Long update_id;
    private TelegramMessage message;

    
}

