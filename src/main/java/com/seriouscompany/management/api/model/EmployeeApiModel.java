package com.seriouscompany.management.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seriouscompany.management.statemachiene.State;

import javax.validation.constraints.NotNull;

public class EmployeeApiModel {

    @JsonIgnore
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer age;

    @JsonIgnore
    private State state;

    public EmployeeApiModel() {
    }

    public EmployeeApiModel(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty
    public State getState() {
        return state;
    }


    @JsonIgnore
    public void setState(State state) {
        this.state = state;
    }
}
