package com.jacknkiarie.couplebet.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "bet_table")
public class Bet implements Serializable {
    // variables
    @PrimaryKey (autoGenerate =true)
    private int uid;

    @ColumnInfo(name = "initiator")
    private String initiator;

    @ColumnInfo(name = "participant")
    private String participant;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "proposition")
    private String proposition;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "initiator_reward")
    private String initiatorReward;

    @ColumnInfo(name = "participant_reward")
    private String participantReward;

    @ColumnInfo(name = "expiry_date")
    private String expiryDate;

    @ColumnInfo(name = "creation_date")
    private String creationDate;

    @ColumnInfo(name = "completion_date")
    private String completionDate;

    @ColumnInfo(name = "bet_winner")
    private String betWinner;

    // bet status
    public static final String STATUS_ONGOING = "ongoing";
    public static final String STATUS_COMPLETED = "completed";
    public static final String STATUS_EXPIRED = "expired";
    public static final String STATUS_CLOSED = "closed";
    public static final String STATUS_CANCELLED = "cancelled";

    // empty constructor
    @Ignore
    public Bet() {}

    // default constructor
    public Bet(String initiator, String participant, String title, String proposition, String status,
               String betWinner, String completionDate, String creationDate) {
        this.initiator = initiator;
        this.participant = participant;
        this.title = title;
        this.proposition = proposition;
        this.status = status;
        this.betWinner = betWinner;
        this.completionDate = completionDate;
        this.creationDate = creationDate;
    }

    // getter and setter methods

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProposition() {
        return proposition;
    }

    public void setProposition(String proposition) {
        this.proposition = proposition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInitiatorReward() {
        return initiatorReward;
    }

    public void setInitiatorReward(String initiatorReward) {
        this.initiatorReward = initiatorReward;
    }

    public String getParticipantReward() {
        return participantReward;
    }

    public void setParticipantReward(String participantReward) {
        this.participantReward = participantReward;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getBetWinner() {
        return betWinner;
    }

    public void setBetWinner(String betWinner) {
        this.betWinner = betWinner;
    }
}
