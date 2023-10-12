package org.example.palindrom.service.impl;

import org.example.palindrom.entity.Player;
import org.example.palindrom.service.PlayersService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlayersServiceImpl implements PlayersService {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     *
     * @return Нового игрок с именем, считанного с консоли
     * @throws IOException
     */
    @Override
    public Player createPlayer() {
        return new Player(readPlayerName());
    }

    @Override
    public String readPlayerName() {
        System.out.println("Введите имя игрока");
        String name;
        try {
            name = reader.readLine().trim();
            if (name.length() < 2) {
                System.err.println("Имя игрока должно быть не короче двух символов!");
                return readPlayerName();
            }
        } catch (IOException e) {
            System.err.println("Неверные данные! Проверьте правильность ввода.");
            return readPlayerName();
        }

        return name;
    }

}
