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
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.example.palindrom.utils.StringValidator.noSpacePhrase;

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
        Set<Player> players = setPlayersNamesInTheGame(playersCount);
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
    public Set<Player> setPlayersNamesInTheGame(int playersCount) {
        if (playersCount < 1) {
            return Collections.emptySet();
        }

        Set<Player> players = new HashSet<>();

        while (players.size() < playersCount) {
            int actualPlayersCount = players.size();

            Player player = playersService.createPlayer();
            players.add(player);

            if (actualPlayersCount == players.size()) {
                System.err.printf("Игрок с именем '%s' уже существует!%n", player.getName());
            }
        }

        return players;
    }

    /**
     * @param players Принимает всех участников игры
     * @return Пять участников с наибольшим количеством очков.
     */

    public Set<Player> top5LeadDashboard(Set<Player> players) {

        return new ArrayList<>(players)
                .stream()
                .sorted(Comparator.comparing(Player::getScore, Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Логическое ядро игры
     *
     * @param game Принимает новую игру и запуск ее
     */
    @Override
    public void gameStart(Game game) {

        System.out.println("Выберите участника игры");
        Set<Player> players = game.getPlayers();
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
     *
     * @param players Принимает список участников игры
     * @return Найденный пользователя
     */

    private Player getPlayerFromGame(Set<Player> players) {
        String playerName = playersService.readPlayerName().toLowerCase();

        Map<String, Player> playersMap = players.stream()
                .collect(Collectors.toMap(p -> p.getName().toLowerCase(), Function.identity()));

        Player player = playersMap.get(playerName);

        if (player == null) {
            System.err.println("Игрок с таким именем не найден. Попробуйте еще раз...");
            return getPlayerFromGame(players);
        }

        return player;
    }

    /**
     * Считывает с консоли фразу и добавляет ее игроку
     *
     * @param player Игрок, которому нужно добавить фразу для игры
     * @return Слово, которое игрок еще не использовал
     */
    private String readPhraseForPlayer(Player player) {
        System.out.println("Введите текст-палиндром: ");
        String phrase;
        try {
            phrase = reader.readLine();
            String noSpacePhrase = noSpacePhrase(phrase);

            Set<String> phrases = player.getPhrases();
            int originalSize = phrases.size();

            phrases.add(noSpacePhrase);

            if (originalSize == phrases.size()) {
                System.err.println("Фраза уже использовалась!");
                return readPhraseForPlayer(player);
            }

            return phrase;

        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new GameException("Игра завершилась по непредвиденным причинам");
        }
    }

    /**
     * @param text         Принимает текст для расчета очков
     * @param isPalindrome Признак того, что текст является палиндромом
     * @return Количество очков согласно длине текста за вычетом символов пробела
     */

    public long calculateScore(String text, boolean isPalindrome) {
        if (isPalindrome) {
            long score = noSpacePhrase(text).length();
            System.out.printf("Введенное слово палиндром, вы заработали %d очков.%n", score);
            return score;
        }

        System.out.println("Введенное слово не палиндром, вы заработали 0 очков");
        return 0;
    }

}
