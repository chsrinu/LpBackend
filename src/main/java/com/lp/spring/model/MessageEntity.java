package com.lp.spring.model;

import com.lp.spring.utility.CommonUtils;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(CommonUtils.DiscriminatorColumns.LOGS_TYPE_MESSAGE)
public class MessageEntity extends CallEntity{
    private String message;
    @Column(columnDefinition = "VARBINARY(600)")
    @ColumnTransformer(read = "AES_DECRYPT(message,'999')", write = "AES_ENCRYPT(?,'999')")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
