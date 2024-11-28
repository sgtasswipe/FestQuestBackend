package com.example.festquestbackend.models.quests;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table ( name = "quests")
public class  Quest {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private  long id;

    @Column ( nullable = false)
    private String title;

    @Column ( nullable = false)
    private  String description;

    @Column ( nullable = false, length = 1000)
    private String imageUrl;

    @Column ( nullable = false)
    private LocalDateTime startTime;

    @Column ( nullable = false)
    private LocalDateTime endTime;

    public Quest() {

    }
    public Quest(long id, String title, String description, String imageUrl, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imgUrl) {
        this.imageUrl = imgUrl;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
