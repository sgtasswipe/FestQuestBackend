package com.example.festquestbackend.models.misc;

import com.example.festquestbackend.models.users.FestUser;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "change_logs")
public class ChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_stamp", nullable = false)
    private LocalDateTime timeStamp;

    @Column(name = "changed", nullable = false, length = 1000)
    private String change;

    @ManyToOne
    @JoinColumn(name = "fest_user_id", referencedColumnName = "id", nullable = false)
    private User user;

//  Constructor
    public ChangeLog() {
    }

    public ChangeLog(FestUser festUser, LocalDateTime timeStamp, String change) {
        this.festUser = festUser;
        this.timeStamp = timeStamp;
        this.change = change;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public FestUser getUser() {
        return festUser;
    }

    public void setUser(FestUser festUser) {
        this.festUser = festUser;
    }
}
