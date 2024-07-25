package com.app.employeePortal.leads.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(name = "round_robin_config")
@AllArgsConstructor
@NoArgsConstructor
public class RoundRobbinConfig {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private int value;

    
    public RoundRobbinConfig(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
