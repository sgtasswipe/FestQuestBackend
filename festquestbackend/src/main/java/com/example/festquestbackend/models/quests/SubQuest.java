package com.example.festquestbackend.models.quests;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table ( name = "sub_quests")
public class SubQuest {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn( name = "quest_id", referencedColumnName = "id", nullable = false)
    private Quest quest;

    @Column ( nullable = false)
    private String title;

    @Column ( nullable = false)
    private Double budget;

    @OneToMany(mappedBy = "subQuest", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Duty> dutyList;

    public SubQuest () {

    }

    public SubQuest(long id, Quest quest, String title, Double budget) {
        this.id = id;
        this.quest = quest;
        this.title = title;
        this.budget = budget;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public List<Duty> getDutyList() {
        return dutyList;
    }

    public void setDutyList(List<Duty> dutyList) {
        this.dutyList = dutyList;
    }
}
