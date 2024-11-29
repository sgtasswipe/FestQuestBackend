package com.example.festquestbackend.models.users;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private long id;

    @Column (nullable = false)
    private String firstName;

    @OneToMany(mappedBy = "user")
    private List<QuestParticipant> questParticipantList;

    @Column (nullable = false)
    private String lastName;

    @Column (nullable = false,  unique = true)
    private String email;

    @Column (nullable = false)
    private String password;


    public User () {

    }

    public User(long id, String firstName, List<QuestParticipant> questParticipantList, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.questParticipantList = questParticipantList;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public List<QuestParticipant> getQuestParticipantList() {
        return questParticipantList;
    }

    public void setQuestParticipantList(List<QuestParticipant> questParticipantList) {
        this.questParticipantList = questParticipantList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
