package com.example.festquestbackend.models.quests;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "duties")
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "sub_quest_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private SubQuest subQuest;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double price;

    public Duty() {

    }


    public Duty(long id, SubQuest subQuest, String title, Double price) {
        this.id = id;
        this.subQuest = subQuest;
        this.title = title;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SubQuest getSubQuest() {
        return subQuest;
    }

    public void setSubQuest(SubQuest subQuest) {
        this.subQuest = subQuest;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
