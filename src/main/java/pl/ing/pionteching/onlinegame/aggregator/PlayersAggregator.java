package pl.ing.pionteching.onlinegame.aggregator;

import pl.ing.pionteching.common.Aggregator;
import pl.ing.pionteching.onlinegame.dto.Clan;
import pl.ing.pionteching.onlinegame.dto.Players;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class PlayersAggregator implements Aggregator<Players, List<List<Clan>>> {

    private final PlayersAggregationProcessor playersAggregationProcessor = new PlayersAggregationProcessor();

    /**
     * Method aggregates Players object into list of clans matched by points and number of players in order.
     *
     * @param players Players object that contains list of clans and group count.
     * @return List of clans matched by points and number of players in order.
     */
    public List<List<Clan>> aggregate(Players players) {
        List<Clan> sortedClans = this.playersAggregationProcessor.sort(players.clans());
        return this.playersAggregationProcessor.aggregation(sortedClans, players.groupCount());
    }


}