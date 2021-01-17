package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Joakim Skaarup Stensnæs
 */
@Entity
@Table(name = "SportTeam")
public class SportTeam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "teamName", length = 20)
    private String teamName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pricePerYear")
    private Integer pricePerYear; //multiply by 100 for whole danish 'Kroner'.
    @Basic(optional = false)
    @NotNull
    @Column(name = "minAge")
    private Integer minAge;
    @Basic(optional = false)
    @NotNull
    @Column(name = "maxAge")
    private Integer maxAge;

    @ManyToOne
    private Sport sport;

    public SportTeam() {
    }

    public SportTeam(@NotNull String teamName, @NotNull Integer pricePerYear, @NotNull Integer minAge, @NotNull Integer maxAge, Sport sport) {
        this.teamName = teamName;
        this.pricePerYear = pricePerYear;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.sport = sport;
    }

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getPricePerYear() {
        return pricePerYear;
    }

    public void setPricePerYear(Integer pricePerYear) {
        this.pricePerYear = pricePerYear;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }
}
