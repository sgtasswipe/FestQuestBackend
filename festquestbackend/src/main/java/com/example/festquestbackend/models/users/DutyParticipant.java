package com.example.festquestbackend.models.users;

import com.example.festquestbackend.models.quests.Duty;
import jakarta.persistence.*;

@Entity
@Table(name = "duty_participants")
public class DutyParticipant {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "quest_participant_id", referencedColumnName = "id", nullable = false)
    private QuestParticipant questParticipant;

    @ManyToOne
    @JoinColumn(name = "duty_id", referencedColumnName = "id", nullable = false)
    private Duty duty;

    @Column (nullable = false)
    private boolean isDone;

    public DutyParticipant () {

    }
    public DutyParticipant(long id, QuestParticipant questParticipant, Duty duty, boolean isDone) {
        this.id = id;
        this.questParticipant = questParticipant;
        this.duty = duty;
        this.isDone = isDone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public QuestParticipant getQuestParticipant() {
        return questParticipant;
    }

    public void setQuestParticipant(QuestParticipant questParticipant) {
        this.questParticipant = questParticipant;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
