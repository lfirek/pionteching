package pl.ing.pionteching.onlinegame.aggregator;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.ing.pionteching.onlinegame.dto.Clan;
import pl.ing.pionteching.onlinegame.dto.Players;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@QuarkusTest
public class PlayersAggregatorTest {
    @Inject
    PlayersAggregator playersAggregator;

    @Test
    public void testAggregateOptimalGroupSize() {
        List<Clan> clans = Arrays.asList(
                new Clan(6, 100),
                new Clan(7, 50),
                new Clan(4, 40),
                new Clan(3, 30));
        Players players = new Players(10, clans);

        List<List<Clan>> groupedClans = playersAggregator.aggregate(players);

        Assertions.assertEquals(groupedClans.size(), 2);
    }

    @Test
    public void testAggregatePointPriority() {
        List<Clan> clans = Arrays.asList(
                new Clan(6, 100),
                new Clan(7, 50),
                new Clan(4, 40),
                new Clan(3, 30),
                new Clan(2, 30));
        Players players = new Players(10, clans);

        List<List<Clan>> groupedClans = playersAggregator.aggregate(players);

        Assertions.assertEquals(groupedClans.size(), 3);
        Assertions.assertEquals(groupedClans.get(0).get(0), clans.get(0));
        Assertions.assertEquals(groupedClans.get(0).get(1), clans.get(2));
        Assertions.assertEquals(groupedClans.get(1).get(0), clans.get(1));
        Assertions.assertEquals(groupedClans.get(1).get(1), clans.get(4));
        Assertions.assertEquals(groupedClans.get(2).get(0), clans.get(3));
    }

    @Test
    public void testAggregateAllClans() {
        List<Clan> clans = Arrays.asList(
                new Clan(6, 100),
                new Clan(7, 50),
                new Clan(4, 40),
                new Clan(6, 21),
                new Clan(7, 42),
                new Clan(4, 123),
                new Clan(6, 65),
                new Clan(7, 33),
                new Clan(4, 40),
                new Clan(3, 30),
                new Clan(2, 5));
        Players players = new Players(10, clans);

        List<List<Clan>> groupedClans = playersAggregator.aggregate(players);

        for (Clan clan : clans) {
            Assertions.assertTrue(groupedClans.stream().anyMatch(group -> group.contains(clan)));
        }

    }

}
