package com.techvika.producer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGenerator")
    @SequenceGenerator(name = "userSeqGenerator", sequenceName = "userSeq", allocationSize = 1)
    @JsonIgnore
    private Long id;

    @Size(min = 2, max = 30)
    private String firstName;

    @Size(min = 1, max = 2)
    private String lastName;

    @Email(message = "please enter a valid email address")
    private String email;

    @Size(min = 8, max = 20, message = "password should be between 8 and 20 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
