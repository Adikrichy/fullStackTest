package org.aldoustv.first_lab.dto;

public class TestResponse {
    private String message;
    private Long id;
    private String name;
    private Integer year;

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
