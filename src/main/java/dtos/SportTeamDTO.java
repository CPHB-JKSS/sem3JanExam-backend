package dtos;

import entities.Sport;
import entities.SportTeam;

public class SportTeamDTO {
    private final String name;
    private final Integer pricePerYear, minAge, maxAge;
    private final Sport sport;

    public SportTeamDTO(SportTeam sportTeam) {
        this.name = sportTeam.getTeamName();
        this.pricePerYear = sportTeam.getPricePerYear();
        this.minAge = sportTeam.getMinAge();
        this.maxAge = sportTeam.getMaxAge();
        this.sport = sportTeam.getSport();
    }

    public String getName() {
        return name;
    }

    public Integer getPricePerYear() {
        return pricePerYear;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public Sport getSport() {
        return sport;
    }
}
