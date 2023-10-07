package org.example.palindrom;

import org.example.palindrom.entity.Game;
import org.example.palindrom.service.GameService;
import org.example.palindrom.service.impl.GameServiceImpl;

public class GameStarter {

    private final GameService gameService = new GameServiceImpl();

    /**
     * Запуск игры
     */
    public static void main(String[] args) {
        GameStarter starter = new GameStarter();
        GameService service = starter.gameService;

        Game game = service.createGame();
        while (true) {
            service.gameStart(game);
        }
    }

}
