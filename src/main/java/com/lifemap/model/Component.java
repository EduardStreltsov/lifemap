package com.lifemap.model;

import javax.persistence.*;

@Entity
public class Component {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @OneToOne
    private Component parent;
    
    @OneToOne
    private User user;
    
}
