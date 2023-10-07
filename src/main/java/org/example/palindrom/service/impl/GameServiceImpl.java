package org.example.palindrom.service.impl;

import org.example.palindrom.entity.Game;
import org.example.palindrom.entity.Player;
import org.example.palindrom.exceptions.GameException;
import org.example.palindrom.service.GameService;
import org.example.palindrom.service.PlayersService;
import org.example.palindrom.utils.StringValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class GameServiceImpl implements GameService {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private final PlayersService playersService = new PlayersServiceImpl();

    /**
     * @return Игра с участниками
     */
    @Override
    public Game createGame() {
        System.out.println("Игра Палиндром!");
        int playersCount = getPlayersCount();
        List<Player> players = setPlayersNamesInTheGame(playersCount);
        return new Game(players);
    }

    /**
     * @return Количество игроков, которых необходимо создать для игры
     */
    public int getPlayersCount() {
        System.out.println("Введите количество участников игры(1, 2, 3...): ");

        int playersCount;
        try {
            String line = reader.readLine();
            playersCount = Integer.parseInt(line);

            if (playersCount < 1) {
                System.err.println("Количество участников должно быть больше 0!");
                return getPlayersCount();
            }

            return playersCount;

        } catch (IOException | NumberFormatException e) {
            System.err.println("Неверные данные! Проверьте правильность ввода.");
            return getPlayersCount();
        }

    }

    /**
     * @param playersCount Принимает количество участников игры и
     *                     создает нового игрока путем ввода с консоли имени игрока
     * @return Список созданных игроков
     */
    public List<Player> setPlayersNamesInTheGame(int playersCount) {
        if (playersCount < 1) {
            return Collections.emptyList();
        }

        List<Player> players = new ArrayList<>();

        while (players.size() < playersCount) {
            Player player = playersService.createPlayer();
            if (!players.contains(player)) {
                players.add(player);
            } else {
                System.err.printf("Игрок с именем '%s' уже существует!%n", player.getName());
            }
        }

        return players;
    }

    /**
     * @param players Принимает всех участников игры
     * @return Пять участников с наибольшим количеством очков.
     */

    public List<Player> top5LeadDashboard(List<Player> players) {
        return players.stream()
                .filter(p -> Objects.nonNull(p.getName()))
                .filter(p -> Objects.nonNull(p.getScore()))
                .sorted(Comparator.comparing(Player::getScore, Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Логическое ядро игры
     * @param game Принимает новую игру и запуск ее
     */
    @Override
    public void gameStart(Game game) {

        System.out.println("Выберите участника игры");
        List<Player> players = game.getPlayers();
        players.forEach(player -> System.out.println(player.getName()));

        Player player = getPlayerFromGame(players);

        String phrase = readPhraseForPlayer(player);

        boolean isPalindrome = StringValidator.isPalindrome(phrase);
        long score = calculateScore(phrase, isPalindrome);
        player.setScore(player.getScore() + score);

        System.out.println("==================================");
        System.out.println("ТОП-5 Игроков");
        top5LeadDashboard(players).forEach(p -> System.out.println(p.getName() + "   " + p.getScore() + " очка(ов)"));
        System.out.println("==================================");
    }

    /**
     * Поиск игрока по имени, считанного с консоли
     * @param players Принимает список участников игры
     * @return Найденный пользователя
     */

    private Player getPlayerFromGame(List<Player> players) {
        String playerName = playersService.readPlayerName();
        try {
            return players.stream()
                    .filter(p -> p.getName().equalsIgnoreCase(playerName))
                    .findFirst()
                    .orElseThrow(GameException::new);
        } catch (GameException e) {
            System.err.println("Игрок с таким именем не найден. Попробуйте еще раз...");
            return getPlayerFromGame(players);
        }
    }

    /**
     * Считывает с консоли фразу и добавляет ее игроку
     * @param player Игрок, которому нужно добавить фразу для игры
     * @return Слово, которое игрок еще не использовал
     */
    private String readPhraseForPlayer (Player player) {
        System.out.println("Введите текст-палиндром: ");
        String phrase;
        try {
            phrase = reader.readLine();
            String noSpacePhrase = StringValidator.noSpacePhrase(phrase);

            List<String> phrases = player.getPhrases().stream()
                    .filter(ph -> StringValidator.noSpacePhrase(ph).equals(noSpacePhrase))
                    .collect(Collectors.toList());
            if (phrases.isEmpty()) {
                player.getPhrases().add(phrase);
                return phrase;
            } else {
                System.err.println("Фраза уже использовалась!");
                return readPhraseForPlayer(player);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new GameException("Игра завершилась по непредвиденным причинам");
        }
    }

    /**
     *
     * @param text Принимает текст для расчета очков
     * @param isPalindrome Признак того, что текст является палиндромом
     * @return Количество очков согласно длине текста за вычетом символов пробела
     */

    public long calculateScore(String text, boolean isPalindrome) {
        if (isPalindrome) {
            long score = text.replace(" ", "").length();
            System.out.printf("Введенное слово палиндром, вы заработали %d очков.%n", score);
            return score;
        }

        System.out.println("Введенное слово не палиндром, вы заработали 0 очков");
        return 0;
    }

}
