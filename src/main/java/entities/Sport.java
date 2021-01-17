package entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joakim Skaarup Stensnæs
 */
@Entity
@Table(name = "Sport")
public class Sport implements Serializable {

    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sportName", length = 20)
    @Expose
    private String sportName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sportDescription", length = 100)
    @Expose
    private String sportDescription;

    @OneToMany(mappedBy = "sport", cascade = CascadeType.PERSIST)
    private List<SportTeam> sportTeamList;

    public Sport() {
    }

    public Sport(@NotNull String sportName, @NotNull String sportDescription) {
        this.sportName = sportName;
        this.sportDescription = sportDescription;
        this.sportTeamList = new ArrayList<>();
    }

    /*public Long getId() {
        return id;
    }*/

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getSportDescription() {
        return sportDescription;
    }

    public void setSportDescription(String sportDescription) {
        this.sportDescription = sportDescription;
    }

    public List<SportTeam> getSportTeamList() {
        return sportTeamList;
    }

    public void setSportTeamList(List<SportTeam> sportTeamList) {
        this.sportTeamList = sportTeamList;
    }

    public void addTeam(SportTeam sportTeam) {
        this.sportTeamList.add(sportTeam);
    }
}