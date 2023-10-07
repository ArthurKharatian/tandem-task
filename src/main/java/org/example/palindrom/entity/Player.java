package org.example.palindrom.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Игрок
 */

public class Player {

    private String name;
    private Long score = 0L;
    private List<String> phrases = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public List<String> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<String> phrases) {
        this.phrases = phrases;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", phrases=" + phrases +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getName().equalsIgnoreCase(player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
