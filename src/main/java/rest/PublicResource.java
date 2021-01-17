package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.SportDTO;
import dtos.SportTeamDTO;
import entities.SportTeam;
import facades.APIFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joakim Skaarup Stensnæs
 */
@Path("public")
public class PublicResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final APIFacade FACADE = APIFacade.getSportFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"admin\"}";
    }

    @Path("sports")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getSports() {
        List<SportDTO> sportsList = FACADE.getAllSports();
        return Response.ok(GSON.toJson(sportsList)).build();
    }

    @Path("teams")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTeams() {
        List<SportTeamDTO> teamsList = FACADE.getAllTeams();
        return Response.ok(GSON.toJson(teamsList)).build();
    }
}
