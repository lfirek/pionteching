package pl.ing.pionteching.onlinegame.dto;

import java.util.List;

public record Players(int groupCount, List<Clan> clans) {
}