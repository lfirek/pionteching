package pl.ing.pionteching.onlinegame.aggregator;

import pl.ing.pionteching.onlinegame.dto.Clan;

import java.util.*;
import java.util.stream.Collectors;

public class PlayersAggregationProcessor {

    private static final Comparator<Clan> CLAN_COMPARATOR = Comparator.comparing(Clan::points).reversed()
            .thenComparing(Clan::numberOfPlayers);


    /**
     * Method sort list of clans in specific order.
     * Clans are sorted by points and number of players, more points for fewer players is better.
     *
     * @param clans List of clans in any order
     * @return Sorted list of clans
     */
    public List<Clan> sort(List<Clan> clans) {
        return clans.stream().sorted(CLAN_COMPARATOR).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Method aggregate clans in groups by points and number of players.
     * Maximum size of each group is controlled by maxGroupCount parameter.
     *
     * @param clans         Sorted list of clans
     * @param maxGroupCount Maximum single group size
     * @return List of clans matched by points and number of players in order.
     */
    public List<List<Clan>> aggregation(List<Clan> clans, int maxGroupCount) {

        List<List<Clan>> groupedClans = new ArrayList<>();

        while (!clans.isEmpty()) {
            List<Clan> matchingGroup = new ArrayList<>();

            Iterator<Clan> it = clans.iterator();
            int groupCount = 0;
            while (it.hasNext()) {
                Clan clan = it.next();
                if (groupCount + clan.numberOfPlayers() <= maxGroupCount) {
                    matchingGroup.add(clan);
                    groupCount += clan.numberOfPlayers();
                    it.remove();
                }
                if (groupCount == maxGroupCount)
                    break;
            }

            groupedClans.add(matchingGroup);
        }

        return groupedClans;
    }

}
