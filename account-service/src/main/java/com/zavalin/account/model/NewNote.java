package com.zavalin.account.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class NewNote {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
