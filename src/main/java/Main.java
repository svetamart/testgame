import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Hero witcher = new Hero("Геральт из Ривии", 30,20, 100, new int[]{6, 10});

        Monster monster1 = new Monster(10, 10, 30, new int[]{4, 8});
        Monster monster2 = new Monster(25, 21, 90, new int[]{10, 20});

        List<Monster> monsters = new ArrayList<>();
        monsters.add(monster1);
        monsters.add(monster2);

        Game game = new Game(witcher, monsters);
        game.startGame();

    }
}
