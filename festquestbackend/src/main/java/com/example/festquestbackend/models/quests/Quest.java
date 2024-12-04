package com.example.festquestbackend.models.quests;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.example.festquestbackend.models.users.QuestParticipant;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "quest")
    @JsonManagedReference // Placed on the parent (Includes the child on serialization)
    private List<QuestParticipant> questParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "quest", cascade =  CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<SubQuest> subQuestList;

    public Quest() {

    }

    public Quest(long id, String title, String description, String imageUrl, LocalDateTime startTime, LocalDateTime endTime, List<QuestParticipant> questParticipants) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.questParticipants = questParticipants;
    }

    public List<QuestParticipant> getQuestParticipants() {
        return questParticipants;
    }

    public void setQuestParticipants(List<QuestParticipant> questParticipants) {
        this.questParticipants = questParticipants;
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

    public List<SubQuest> getSubQuestList() {
        return subQuestList;
    }

    public void setSubQuestList(List<SubQuest> subQuestList) {
        this.subQuestList = subQuestList;
    }

}
