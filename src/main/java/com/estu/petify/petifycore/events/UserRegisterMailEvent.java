package com.estu.petify.petifycore.events;

public class UserRegisterMailEvent {
    private String subject;
    private String user;
    private String message;

    public UserRegisterMailEvent() {
        // Empty Constructor.
    }

    public UserRegisterMailEvent(String subject, String user, String message) {
        this.subject = subject;
        this.user = user;
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String body) {
        this.message = body;
    }
}
