package okjava.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MonsterStory {

    public static void main(String[] args) {
        if (args.length < 3) {
            printUsages();
            return;
        }
        List<String> monsters = Arrays.asList(Arrays.copyOfRange(args, 1, args.length));
        MonsterStory monsterStory = new MonsterStory(monsters, resolveLang(args[0]));
        monsterStory.tell();
    }

    private static Lang resolveLang(String name) {
        if (LangRUS.NAME.equals(name)) {
            return LangRUS.INSTANCE;
        } else if (LangENG.NAME.equals(name)) {
            return LangENG.INSTANCE;
        }
        printUsages();
        throw new IllegalArgumentException(name);
    }

    private static void printUsages() {
        System.out.printf("usage Monster %s MonsterName1 MonsterName2 MonsterName3 ...", String.join("|", LangRUS.NAME, LangENG.NAME));
    }

    private final List<String> monsters;
    private final Lang lang;

    public MonsterStory(List<String> monsters, Lang lang) {
        this.monsters = List.copyOf(monsters);
        this.lang = Objects.requireNonNull(lang);
    }

    private void tell() {
        println(0, lang.intro(), String.join(", ", monsters));
        System.out.println("");

        first();
        for (int i = 0; i < monsters.size() - 1; i++) {
            System.out.println("");
            eat(i);
        }

        System.out.println("");
        for (int i = monsters.size() - 1; i > 1; i--) {
            sick(i, monsters.size() - i - 1);
        }
        sick2(1, monsters.size() - 1);

        System.out.println("");
        for (int i = monsters.size() - 1; i > 0; i--) {
            fart(i, monsters.size() - i - 1);
        }

        System.out.println("");
        finish();
    }


    void first() {
        println(0, lang.lived(), monsters.get(0));
    }

    void eat(int i) {
        println(i, lang.eat(), monsters.get(i + 1), monsters.get(i));
        ///System.out.println(tab(i) + "но тут пршел монстер " + monsters.get(i + 1) + " и съел монстра " + monsters.get(i) + ".");
        sitInStomach(i, i);
        lived2(i + 1);
    }

    void sitInStomach(int i, int tab) {
        println(tab, lang.sitInStomach(), monsters.get(i), monsters.get(i + 1));
        for (int j = i - 1; j >= 0; j--) {
            sitInStomachNext(j, tab - j + i);
        }
    }

    void sitInStomachNext(int i, int tab) {
        println(tab, lang.sitInStomach2(), monsters.get(i + 1), monsters.get(i));
    }

    void lived2(int i) {
        println(i - 1, lang.lived2(), monsters.get(i));
    }

    void sick(int i, int tab) {
        println(tab, lang.sick(), monsters.get(i), monsters.get(i - 1));
    }

    void sick2(int i, int tab) {
        println(tab, lang.sick2(), monsters.get(i), monsters.get(i - 1), monsters.get(i - 1));
    }

    void fart(int i, int tab) {
        println(tab, lang.fart(), monsters.get(i), monsters.get(i - 1));
    }

    void finish() {
        println(0, lang.finish());
    }


    private static final class LangENG implements Lang {
        private static final String NAME = "ENG";
        private static final Lang INSTANCE = new LangENG();

        @Override
        public String intro() {
            return "Acting monsters in a fairy tale: %s.";
        }

        @Override
        public String lived() {
            return "There was a monster %s and everything was fine with him.";
        }

        @Override
        public String eat() {
            return "But then the monster %s came and ate the monster %s.";
        }

        @Override
        public String sitInStomach() {
            return "The monster %s sits in the stomach of the monster %s and cries with bitter tears.";
        }

        @Override
        public String sitInStomach2() {
            return "And in the stomach of the monster %s sits the monster %s and also cries with bitter tears.";
        }

        @Override
        public String lived2() {
            return "And so the monster %s lived and everything was fine with him.";
        }

        @Override
        public String sick() {
            return "And so the monster %s got a very bad stomach, because the monster %S was sitting in his stomach and was also sick.";
        }

        @Override
        public String sick2() {
            return "And now the monster %s got a very bad stomach, because the monster %s was sitting in his stomach, but the monster %s didn’t have a stomachache.";
        }

        @Override
        public String fart() {
            return "And the monster %s farted very hard, and the monster %s flew out of his ass and was very happy.";
        }

        @Override
        public String finish() {
            return "And now all the monsters began to live long, friendly and happy and no one was sick and no one ate each other.";
        }
    }

    private static final class LangRUS implements Lang {
        private static final String NAME = "RUS";
        private static final Lang INSTANCE = new LangRUS();

        @Override
        public String intro() {
            return "Действующие монстры в сказке: %s.";
        }

        @Override
        public String lived() {
            return "Жил был монстр %s и все у него было хорошо.";
        }

        @Override
        public String eat() {
            return "Но тут пришел монстр %s и съел монстра %s.";
        }

        @Override
        public String sitInStomach() {
            return "Сидит монстр %s в животе у монстра %s и плачет горькими слезами.";
        }

        @Override
        public String sitInStomach2() {
            return "А в животе у монстра %s сидит монстр %s и тоже плачет горькими слезами.";
        }

        @Override
        public String lived2() {
            return "Так и жил монстр %s и все у него было хорошо.";
        }

        @Override
        public String sick() {
            return "И вот у монстра %s очень сильно заболел живот, потому что у него в животе сидел монстр %s и тоже болел.";
        }

        @Override
        public String sick2() {
            return "И вот у монстра %s очень сильно заболел живот, потому что у него в животе сидел монстр %s, но у монстра %s живот не болел.";
        }

        @Override
        public String fart() {
            return "И вот монстр %s очень сильно пукнул, и из его попы вылетел монстр %s и очень сильно обрадовался.";
        }

        @Override
        public String finish() {
            return "И теперь все монстры стали жить долго, дружно и счастливо и не никто не болел и никто друг друга не ел.";
        }
    }

    private interface Lang {
        String intro();

        String lived();

        String eat();

        String sitInStomach();

        String sitInStomach2();

        String lived2();

        String sick();

        String sick2();

        String fart();

        String finish();
    }

    @SuppressWarnings("varargs")
    void println(int tab, String text, Object... args) {
        System.out.println(tab(tab) + String.format(text, args));
    }

    private String tab(int i) {
        if (i == 0) {
            return "";
        }
        if (i % 2 == 0) {
            String res = tab(i / 2);
            return res + res;
        } else {
            return tab(i - 1) + " ";
        }
    }
}
