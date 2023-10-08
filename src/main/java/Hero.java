/**
 * Класс Hero представляет героя в игре. Является подклассом Creature и наследует его характеристики и методы.
 */
public class Hero extends Creature {
    private static final int MAX_HEAL_COUNT = 4;
    private static final double HEAL_PERCENTAGE = 0.3;
    private int healCount;

    /**
     * Конструктор класса Hero.
     *
     * @param name        имя героя
     * @param attack      значение атаки героя
     * @param defense     значение защиты героя
     * @param maxHealth   максимальное значение здоровья героя
     * @param damageRange диапазон урона героя
     */
    public Hero(String name, int attack, int defense, int maxHealth, int[] damageRange) {
        super(name, attack, defense, maxHealth, damageRange);
        this.healCount = MAX_HEAL_COUNT;
    }

    /**
     * Метод для исцеления героя. Герой может исцелиться до 4 раз на 30% от максимального здоровья.
     * Если исцеление возможно, уменьшает количество оставшихся исцелений и увеличивает текущее здоровье.
     * Выводит информацию о процессе исцеления.
     */
    public void heal() {
        if (healCount > 0) {
            int healAmount = (int) (maxHealth * HEAL_PERCENTAGE);
            currentHealth += healAmount;
            healCount--;
            System.out.println(this.name + " исцелил себя на " + healAmount + " единиц здоровья.");
        } else {
            System.out.println(this.name + " не может больше исцеляться.");
        }
    }

    /**
     * Переопределенный метод для обработки смерти героя.
     * Вызывает родительский метод и выводит сообщение о смерти героя.
     */
    @Override
    protected void die() {
        super.die();
        System.out.println(this.name + " пал смертью храбрых.\n");
    }

    /**
     * Переопределенный метод для отображения информации о герое, включая количество оставшихся исцелений.
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Может исцелиться " + healCount + " раз(а).");
        System.out.println();
    }
}
