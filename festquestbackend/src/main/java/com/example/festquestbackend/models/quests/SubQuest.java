package com.example.festquestbackend.models.quests;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table ( name = "sub_quests")
public class SubQuest {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn( name = "quest_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference("quest-subquest")
    private Quest quest;

    @Column ( nullable = false)
    private String title;

    @Column ( nullable = false)
    private Double budget;

    @OneToMany(mappedBy = "subQuest", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
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
