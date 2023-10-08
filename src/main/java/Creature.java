import java.util.Random;

/**
 * Абстрактный класс Creature представляет базовую сущность в игре.
 * Этот класс описывает общие характеристики и методы для всех существ.
 */

public abstract class Creature {
    private static final Random RANDOM = new Random();

    /**
     * Минимальное значение, которое можно получить при броске кубика.
     */
    private static final int MIN_DICE_VALUE = 1;

    /**
     * Максимальное значение, которое можно получить при броске кубика.
     */
    private static final int MAX_DICE_VALUE = 6;

    /**
     * Минимальное допустимое значение атаки и защиты.
     */
    private static final int MIN_ATTACK_DEFENSE_VALUE = 1;

    /**
     * Максимальное допустимое значение атаки и защиты.
     */
    private static final int MAX_ATTACK_DEFENSE_VALUE = 30;

    protected int attack;
    protected int defense;

    protected int maxHealth;
    protected int currentHealth;

    protected int[] damageRange;
    protected String name;
    protected boolean alive;

    /**
     * Конструктор класса Creature.
     *
     * @param name         имя существа
     * @param attack       значение атаки (целое число от MIN_ATTACK_DEFENSE_VALUE до MAX_ATTACK_DEFENSE_VALUE)
     * @param defense      значение защиты (целое число от MIN_ATTACK_DEFENSE_VALUE до MAX_ATTACK_DEFENSE_VALUE)
     * @param maxHealth    максимальное значение здоровья (натуральное число больше 0)
     * @param damageRange  диапазон урона (массив из двух неотрицательных значений, где [0] < [1])
     * @throws IllegalArgumentException если переданы некорректные значения атаки, защиты, здоровья или урона
     */
    public Creature(String name, int attack, int defense, int maxHealth, int[] damageRange) {

        if (attack < MIN_ATTACK_DEFENSE_VALUE || attack > MAX_ATTACK_DEFENSE_VALUE ||
                defense < MIN_ATTACK_DEFENSE_VALUE || defense > MAX_ATTACK_DEFENSE_VALUE) {
            throw new IllegalArgumentException("Ошибка: Недопустимые значения атаки или защиты.");
        }

        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Ошибка: Значение maxHealth не может быть отрицательным.");
        }

        if (damageRange[0] < 0 || damageRange[1] < 0) {
            throw new IllegalArgumentException("Ошибка: Значения в диапазоне damageRange не могут быть отрицательными.");
        }

        if (damageRange[0] >= damageRange[1]) {
            throw new IllegalArgumentException("Ошибка: Значение damageRange[0] должно быть меньше значения damageRange[1].");
        }

        this.attack = attack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.damageRange = damageRange;
        this.name = name;
        this.alive = true;
    }

    /**
     * Метод, вызываемый при смерти существа. Устанавливает флаг alive в false.
     */
    protected void die() {
        this.alive = false;
    }

    /**
     * Метод для броска кубиков.
     *
     * @param modifier модификатор атаки, определяющий количество бросков. Всегда бросается хотя бы один кубик.
     * @return массив значений бросков кубиков
     */

    public int[] rollDice(int modifier) {
        int[] diceRolls = new int[Math.max(modifier, MIN_DICE_VALUE)];
        for (int i = 0; i < diceRolls.length; i++) {
            diceRolls[i] = RANDOM.nextInt(MAX_DICE_VALUE) + 1;
        }
        return diceRolls;
    }

    /**
     * Метод для определения успешности атаки на основе значений бросков кубиков.
     * Атака считается успешной, если хотя бы на одном из кубиков выпадает 5 или 6.
     *
     * @param diceRolls значения бросков кубиков
     * @return true, если атака успешна, иначе false
     */

    public boolean isAttackSuccessful(int[] diceRolls) {
        for (int roll : diceRolls) {
            if (roll == 5 || roll == 6) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод для удара по другому существу.
     * Алгоритм удара следующий:
     * 1. Рассчитываем модификатор атаки attackModifier (разность Атаки атакующего и Защиты защищающегося плюс 1)
     * 2. Бросаем кубики (количество бросков определяется модификатором атаки, всегда бросается хотя бы один кубик.)
     * 3. При успешном броске из Здоровья защищающегося вычитается произвольное значение из параметра Урон атакующего
     *
     * @param target цель удара
     * @throws IllegalArgumentException если цель удара равна null
     */

    protected void hit (Creature target) {
        if (target == null) {
            throw new IllegalArgumentException("Ошибка: Нельзя атаковать null.");
        }

        if (!target.alive || !this.alive) return;

        int attackModifier = this.attack - target.defense + 1;
        int[] diceRolls = rollDice(attackModifier);
        boolean successfulHit = isAttackSuccessful(diceRolls);

        if (successfulHit) {
            int damage = RANDOM.nextInt(damageRange[0], damageRange[1] + 1);
            target.currentHealth -= damage;
            System.out.printf("%s получил(а) урон %d. Здоровье: %d.\n", target.name, damage, target.currentHealth);

            if (target.currentHealth <= 0) {
                target.die();
            }
        } else {
            System.out.printf("Неудачный удар. %s не получает урон.\n", target.name);
        }
    }
    /**
     * Метод для получения текущего значения здоровья.
     *
     * @return текущее значение здоровья
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Метод проверяет, живо ли существо.
     *
     * @return true, если существо живо, иначе false
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Метод для получения максимального значения здоровья.
     *
     * @return максимальное значение здоровья
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Метод для получения имени существа.
     *
     * @return имя существа
     */
    public String getName() {
        return name;
    }

    /**
     * Метод для отображения информации о существе.
     */
    public void displayInfo() {
        System.out.println(name);
        System.out.println("Здоровье: " + currentHealth + "/" + maxHealth);
    }

}
