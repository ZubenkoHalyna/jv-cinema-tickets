package com.dev.cinema.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cinema_halls")
public class CinemaHall extends BaseEntity {
    private int capacity;
    private String description;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CinemaHall{"
                + "id=" + getId()
                + ", capacity=" + capacity
                + ", description='" + description + '\''
                + '}';
    }
}
