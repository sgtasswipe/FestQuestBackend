package com.example.festquestbackend.models.quests;

import jakarta.persistence.*;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private long id;

    @OneToOne // Foreign key to id in quest. Not null.
    @JoinColumn(name = "quest_id", referencedColumnName = "id", nullable = false)
    private Quest quest;

    public Chat () {

    }
    public Chat(long id, Quest quest) {
        this.id = id;
        this.quest = quest;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }
}
