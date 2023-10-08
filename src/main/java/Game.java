import java.util.*;

/**
 * Класс Game представляет игру, в которой герой сражается с монстрами.
 */

public class Game {
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\033[0;33m";

    private static final Random RANDOM = new Random();
    private final Hero hero;
    private final List<Monster> monsters;
    private List<Monster> aliveMonsters;
    private boolean gameOver;
    private final Scanner scanner;
    private int roundCounter = 1;

    /**
     * Конструктор класса Game.
     *
     * @param hero     объект героя, участвующего в игре
     * @param monsters список монстров, с которыми сражается герой
     * @throws IllegalArgumentException если передан null в качестве героя или списка монстров
     */
    public Game(Hero hero, List<Monster> monsters) {
        if (hero == null || monsters == null) {
            throw new IllegalArgumentException("Ошибка: Нельзя передавать null в конструктор Game.");
        }

        this.hero = hero;
        this.monsters = monsters;
        this.gameOver = false;
        this.aliveMonsters = new ArrayList<>(monsters);
        updateAliveMonsters();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Запускает игру и управляет раундами боя между героем и монстрами.
     */
    public void startGame() {
        System.out.println(GREEN + """
                Игра началась!
                Для того чтобы запустить следующий раунд, просто нажмите ENTER.

                """ + RESET);

        while (!gameOver) {
            System.out.println(GREEN + "РАУНД " + roundCounter + RESET);

            heroTurn();
            updateAliveMonsters();
            checkGameStatus();

            if (gameOver) {
                break;
            }

            monstersTurn();
            updateAliveMonsters();
            checkGameStatus();

            if (gameOver) {
                break;
            }

            displayGameInfo();

            roundCounter++;

            waitForEnter();
        }
        scanner.close();
    }

    /**
     * Выполняет ход героя в рамках текущего раунда.
     */
    private void heroTurn() {
        System.out.println("\nХод героя:");
        if (hero.getCurrentHealth() <= hero.getMaxHealth() * 0.4) {
            hero.heal();
        }
        hero.hit(aliveMonsters.get(randomMonsterIndex()));
    }

    /**
     * Выполняет ход одного из монстров в рамках текущего раунда.
     */
    private void monstersTurn() {
        System.out.println("\nХод монстров:");
        Monster attackingMonster = aliveMonsters.get(randomMonsterIndex());
        attackingMonster.hit(hero);
    }

    /**
     * Обновляет список живых монстров, удаляя мертвых.
     */
    private void updateAliveMonsters() {
        aliveMonsters.removeIf(monster -> !monster.isAlive());
    }

    /**
     * Проверяет текущий статус игры (победа героя, победа монстров или продолжение боя).
     */
    private void checkGameStatus() {
        if (hero.getCurrentHealth() <= 0) {
            System.out.println(RED + "Игра окончена. Победили монстры!" + RESET);
            gameOver = true;
        } else if (allMonstersDead()) {
            System.out.printf(YELLOW + "Поздравляем! %s одолел всех монстров!" + RESET, hero.getName());
            gameOver = true;
        }
    }

    /**
     * Проверяет, все ли монстры мертвы.
     *
     * @return true, если все монстры мертвы, иначе false
     */
    private boolean allMonstersDead() {
        return aliveMonsters.isEmpty();
    }

    /**
     * Генерирует случайный индекс монстра из списка живых монстров.
     *
     * @return случайный индекс монстра
     */
    private int randomMonsterIndex() {
        return RANDOM.nextInt(aliveMonsters.size());
    }

    /**
     * Выводит информацию о текущем состоянии игры (здоровье героя и монстров).
     */
    private void displayGameInfo() {
        System.out.println(GREEN + "\nТекущее состояние игры\n" + RESET);
        System.out.print("Герой: ");
        hero.displayInfo();
        System.out.println("Монстры:");
        for (Monster monster : aliveMonsters) {
            monster.displayInfo();
        }
    }

    /**
     * Ожидает нажатия Enter от пользователя для перехода к следующему раунду.
     */
    private void waitForEnter() {
        System.out.println("\nНажмите Enter, чтобы сделать ход.\n");
        scanner.nextLine();
    }
}

