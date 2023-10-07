package org.example.palindrom.service;

import org.example.palindrom.entity.Game;

public interface GameService {

    Game createGame();

    void gameStart(Game game);
}
