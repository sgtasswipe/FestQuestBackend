package com.example.festquestbackend.models.quests;

import com.example.festquestbackend.models.users.FestUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "fest_user_id", referencedColumnName = "id", nullable = false)
    private FestUser festUser;

    @Column (nullable = false)
    private String content;

    @Column (nullable = false)
    private LocalDateTime timeStamp;

    public Message () {

    }

    public Message(long id, Chat chat, FestUser festUser, String content, LocalDateTime timeStamp) {
        this.id = id;
        this.chat = chat;
        this.festUser = festUser;
        this.content = content;
        this.timeStamp = timeStamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public FestUser getUser() {
        return festUser;
    }

    public void setUser(FestUser festUser) {
        this.festUser = festUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
