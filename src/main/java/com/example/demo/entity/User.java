package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="\"user\"")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_gen")
    @SequenceGenerator(name = "seq_user_gen", sequenceName = "seq_user_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String password;
}
