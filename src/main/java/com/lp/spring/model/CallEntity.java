package com.lp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lp.spring.utility.CommonUtils;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;

@Entity(name = "logs")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(CommonUtils.DiscriminatorColumns.LOGS_TYPE_CALL)
public class CallEntity {
    private Long entryId;
    private User user;
    private String logId, fromNumber,time, contactName;

    @Column(nullable = false)
    public String getLogId() {
        return logId;
    }

    public void setLogId(String userLogId) {
        this.logId = userLogId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user", nullable = false)
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(nullable = false, length = 15)
    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    @Column(nullable = false, columnDefinition = "VARBINARY(150)")
    @ColumnTransformer(read = "AES_DECRYPT(contactName,'999')", write = "AES_ENCRYPT(?,'999')")
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Column(nullable = false, length = 15)
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
