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
@NamedQuery(name = "Sport.deleteAllRows", query = "DELETE from Sport")
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

    //TODO try removing this reference to fix circular loop when serializing with GSON


    public Sport() {
    }

    public Sport(@NotNull String sportName, @NotNull String sportDescription) {
        this.sportName = sportName;
        this.sportDescription = sportDescription;
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


}