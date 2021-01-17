package dtos;

import entities.Sport;

public class SportDTO {
    private final String name, description;

    public SportDTO(Sport sport) {
        this.name = sport.getSportName();
        this.description = sport.getSportDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
