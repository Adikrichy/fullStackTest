package org.aldoustv.first_lab.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Random;

@Entity
@Table(name = "test")
public class Test {
    @Id
    private Long id;

    @Column(nullable = false, name = "test_name", length = 50)
    private String name;

    @Column(name = "test_year", length = 10)
    private Integer year;


    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Integer getYear(){
        return year;
    }

    public void setYear(Integer year){
        this.year = year;
    }


    public Test(){

    }

    public Test(Long id, String name,Integer year){
        super();
        this.id = id;
        this.name = name;
        this.year = year;
    }

    @OneToMany(mappedBy = "test",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Test2> test2List;

}
