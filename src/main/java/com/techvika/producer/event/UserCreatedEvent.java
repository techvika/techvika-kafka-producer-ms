package com.techvika.producer.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;
}
