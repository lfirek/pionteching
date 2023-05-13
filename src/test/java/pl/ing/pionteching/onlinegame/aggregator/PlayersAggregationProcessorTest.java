package pl.ing.pionteching.onlinegame.aggregator;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.ing.pionteching.onlinegame.dto.Clan;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@QuarkusTest
class PlayersAggregationProcessorTest {

    PlayersAggregationProcessor playersAggregationProcessor = new PlayersAggregationProcessor();

    @Test
    public void testSort() {
        List<Clan> clans = Arrays.asList(
                new Clan(4, 10),
                new Clan(6, 100),
                new Clan(4, 50),
                new Clan(3, 50)
        );

        List<Clan> sortedClans = playersAggregationProcessor.sort(clans);

        Assertions.assertEquals(sortedClans.get(0), clans.get(1));
        Assertions.assertEquals(sortedClans.get(1), clans.get(3));
        Assertions.assertEquals(sortedClans.get(2), clans.get(2));
        Assertions.assertEquals(sortedClans.get(3), clans.get(0));
    }

    @Test
    public void testAggregation() {
        List<Clan> clans = new LinkedList<Clan>(Arrays.asList(
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
                new Clan(2, 5)));

        List<List<Clan>> groupedClans = playersAggregationProcessor.aggregation(clans, 10);

        for (Clan clan : clans) {
            Assertions.assertTrue(groupedClans.stream().anyMatch(group -> group.contains(clan)));
        }
    }

    @Test
    public void testAggregateOptimalGroupSize() {
        List<Clan> clans = new LinkedList<Clan>(Arrays.asList(
                new Clan(6, 100),
                new Clan(7, 50),
                new Clan(4, 40),
                new Clan(3, 30)));

        List<List<Clan>> groupedClans = playersAggregationProcessor.aggregation(clans, 10);

        Assertions.assertEquals(groupedClans.size(), 2);
    }

}