package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.SportDTO;
import dtos.SportTeamDTO;
import facades.APIFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Joakim Skaarup Stensnæs
 */
@Path("admin")
public class AdminResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final APIFacade FACADE = APIFacade.getSportFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"admin\"}";
    }

    @Path("sport/add")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public void addSport(@HeaderParam("x-access-token") String token, String body) {

        if (!body.equals("")) {
            JsonObject JSONBody = JsonParser.parseString(body).getAsJsonObject();
            String sportName = (JSONBody.get("sportName").getAsString());
            String sportDesc = JSONBody.get("sportDesc").getAsString();

            try {
                SportDTO sportDTO = FACADE.addSport(sportName, sportDesc);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }

    @Path("team/add")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public void addTeam(@HeaderParam("x-access-token") String token, String body) {

        if (!body.equals("")) {
            JsonObject JSONBody = JsonParser.parseString(body).getAsJsonObject();
            String teamName = (JSONBody.get("teamName").getAsString());
            Integer pricePerYear = (JSONBody.get("pricePerYear").getAsInt())*100; //Value equal to 'øre'.
            Integer minAge = JSONBody.get("minAge").getAsInt();
            Integer maxAge = JSONBody.get("maxAge").getAsInt();
            String sport = JSONBody.get("sport").getAsString();

            try {
                SportTeamDTO sportTeamDTO = FACADE.addSportTeam(teamName, pricePerYear, minAge, maxAge, sport);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }

    @Path("team/remove")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public void removeTeam(@HeaderParam("x-access-token") String token, String body) {

        if (!body.equals("")) {
            JsonObject JSONBody = JsonParser.parseString(body).getAsJsonObject();
            String teamName = (JSONBody.get("teamName").getAsString());

            try {
                FACADE.removeSportTeam(teamName);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }
}
