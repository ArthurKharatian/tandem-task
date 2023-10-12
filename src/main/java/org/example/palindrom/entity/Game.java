package org.example.palindrom.entity;

import java.util.Set;

/**
 * Игра
 */
public class Game {

    private Set<Player> players;

    public Game(Set<Player> players) {
        this.players = players;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}
