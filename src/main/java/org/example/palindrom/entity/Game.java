package org.example.palindrom.entity;

import java.util.List;
/**
 * Игра
 */
public class Game {

    private List<Player> players;

    public Game(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
