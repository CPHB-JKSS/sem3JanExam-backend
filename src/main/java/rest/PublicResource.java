package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.SportDTO;
import entities.Sport;
import facades.SportFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Joakim Skaarup Stensnæs
 */
@Path("public")
public class PublicResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final SportFacade FACADE = SportFacade.getSportFacade(EMF);
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
        List<SportDTO> SportsList = FACADE.getAllSports();
        return Response.ok(GSON.toJson(SportsList)).build();
    }
}
