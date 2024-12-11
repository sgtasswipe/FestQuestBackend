package com.example.festquestbackend.models.users;

import com.example.festquestbackend.models.quests.Quest;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "quest_participants")
public class QuestParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "fest_user_id", referencedColumnName = "id", nullable = false)
    private FestUser festUser;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "quest_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Quest quest;

    @Column(nullable = false)
    private boolean isGoing;

    public QuestParticipant() {

    }

    public QuestParticipant(long id, FestUser festUser, Role role, Quest quest, boolean isGoing) {
        this.id = id;
        this.festUser = festUser;
        this.role = role;
        this.quest = quest;
        this.isGoing = isGoing;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FestUser getUser() {
        return festUser;
    }

    public void setUser(FestUser festUser) {
        this.festUser = festUser;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public boolean isGoing() {
        return isGoing;
    }

    public void setGoing(boolean going) {
        isGoing = going;
    }
}
