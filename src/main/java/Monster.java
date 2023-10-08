import java.util.Random;

/**
 * Класс Monster представляет монстра в игре. Является подклассом Creature и наследует его характеристики и методы.
 */
public class Monster extends Creature{

    private static final String[] MONSTER_NAMES =
            {"Гуль", "Брукса", "Виверна", "Василиск", "Утопец", "Кикимора", "Упырь", "Оборотень"};

    /**
     * Конструктор класса Monster.
     *
     * @param attack      значение атаки монстра
     * @param defense     значение защиты монстра
     * @param maxHealth   максимальное значение здоровья монстра
     * @param damageRange диапазон урона монстра
     */
    public Monster(int attack, int defense, int maxHealth, int[] damageRange) {
        super(generateRandomName(), attack, defense, maxHealth, damageRange);
    }

    /**
     * Генерирует случайное имя для монстра из списка MONSTER_NAMES.
     *
     * @return случайное имя монстра из списка
     */
    private static String generateRandomName() {
        Random random = new Random();
        int randomIndex = random.nextInt(MONSTER_NAMES.length);
        return MONSTER_NAMES[randomIndex];
    }

    /**
     * Переопределенный метод для обработки смерти монстра.
     * Вызывает родительский метод и выводит сообщение о победе над монстром.
     */
    @Override
    protected void die() {
        super.die();
        System.out.printf("Монстр %s повержен.\n\n", this.name);
    }
}
