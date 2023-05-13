package pl.ing.pionteching.onlinegame.resource;

import io.smallrye.common.annotation.NonBlocking;
import pl.ing.pionteching.onlinegame.aggregator.PlayersAggregator;
import pl.ing.pionteching.onlinegame.dto.Clan;
import pl.ing.pionteching.onlinegame.dto.Players;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("onlinegame")
public class OnlineGameResource {
    @Inject
    PlayersAggregator playersAggregator;

    @Path("calculate")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @NonBlocking
    public List<List<Clan>> calculate(Players players) {
        return playersAggregator.aggregate(players);
    }
}