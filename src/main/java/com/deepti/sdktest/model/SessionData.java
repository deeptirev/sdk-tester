package com.deepti.sdktest.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SessionData {
    @Id
    public String sessionId;
}
