package com.iron.gift.config.data;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UserSession {

    public final Long id;

    public UserSession(Long id) {
        this.id = id;
    }
}
