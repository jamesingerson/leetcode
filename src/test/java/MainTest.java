import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class MainTest {
    static Main main;

    @BeforeAll
    static void init() {
        main = new Main();
    }

    @Test
    void testMain() {
        var value = main.doubler(2);
        assertThat(value).isEqualTo(4);
    }

    @Test
    void twoSum() {
        var example1 = main.twoSum(new int[]{2, 7, 11, 15}, 9);
        assertThat(example1).isEqualTo(new int[]{0, 1});

        var example2 = main.twoSum(new int[]{3, 2, 4}, 6);
        assertThat(example2).isEqualTo(new int[]{1, 2});

        var example3 = main.twoSum(new int[]{3, 3}, 6);
        assertThat(example3).isEqualTo(new int[]{0, 1});
    }

    @Test
    void isPalindrome() {
        var example1 = main.isPalindrome(121);
        assertThat(example1).isTrue();

        var example2 = main.isPalindrome(-121);
        assertThat(example2).isFalse();

        var example3 = main.isPalindrome(10);
        assertThat(example3).isFalse();
    }

    @Test
    void romanToInt() {
        var example1 = main.romanToInt("III");
        assertThat(example1).isEqualTo(3);

        var example2 = main.romanToInt("LVIII");
        assertThat(example2).isEqualTo(58);

        var example3 = main.romanToInt("MCMXCIV");
        assertThat(example3).isEqualTo(1994);
    }

    @Test
    void isValid() {
        var example1 = main.isValid("()");
        assertThat(example1).isTrue();

        var example2 = main.isValid("()[]{}");
        assertThat(example2).isTrue();

        var example3 = main.isValid("(]");
        assertThat(example3).isFalse();

        var fail1 = main.isValid("[");
        assertThat(fail1).isFalse();

        var fail2 = main.isValid("]");
        assertThat(fail2).isFalse();
    }

    @Test
    void strStr() {
        var example1 = main.strStr("sadbutsad", "sad");
        assertThat(example1).isEqualTo(0);

        var example2 = main.strStr("leetcode", "leeto");
        assertThat(example2).isEqualTo(-1);

        var example3 = main.strStr("hello", "ll");
        assertThat(example3).isEqualTo(2);
    }

    @Test
    void searchInsert() {
        var example1 = main.searchInsert(new int[]{1, 3, 5, 6}, 5);
        assertThat(example1).isEqualTo(2);

        var example2 = main.searchInsert(new int[]{1, 3, 5, 6}, 2);
        assertThat(example2).isEqualTo(1);

        var example3 = main.searchInsert(new int[]{1, 3, 5, 6}, 7);
        assertThat(example3).isEqualTo(4);
    }

    @Test
    void plusOne() {
        var example1 = main.plusOne(new int[]{1, 2, 3});
        assertThat(example1).isEqualTo(new int[]{1, 2, 4});

        var example2 = main.plusOne(new int[]{4, 3, 2, 1});
        assertThat(example2).isEqualTo(new int[]{4, 3, 2, 2});

        var example3 = main.plusOne(new int[]{9});
        assertThat(example3).isEqualTo(new int[]{1, 0});

        var fail1 = main.plusOne(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0});
        assertThat(fail1).isEqualTo(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 1});
    }

    @Test
    void fizzBuzz() {
        var example1 = main.fizzBuzz(3);
        assertThat(example1).isEqualTo(Arrays.asList("1", "2", "Fizz"));

        var example2 = main.fizzBuzz(5);
        assertThat(example2).isEqualTo(Arrays.asList("1", "2", "Fizz", "4", "Buzz"));

        var example3 = main.fizzBuzz(15);
        assertThat(example3).isEqualTo(Arrays.asList("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz"));
    }

    @Test
    void maximumWealth() {
        var example1 = main.maximumWealth(new int[][]{{1, 2, 3}, {3, 2, 1}});
        assertThat(example1).isEqualTo(6);

        var example2 = main.maximumWealth(new int[][]{{1, 5}, {7, 3}, {3, 5}});
        assertThat(example2).isEqualTo(10);

        var example3 = main.maximumWealth(new int[][]{{2, 8, 7}, {7, 1, 3}, {1, 9, 5}});
        assertThat(example3).isEqualTo(17);
    }

    @Test
    void numberOfSteps() {
        var example1 = main.numberOfSteps(14);
        assertThat(example1).isEqualTo(6);

        var example2 = main.numberOfSteps(8);
        assertThat(example2).isEqualTo(4);

        var example3 = main.numberOfSteps(123);
        assertThat(example3).isEqualTo(12);
    }

    @Test
    void defangIPaddr() {
        var example1 = main.defangIPaddr("1.1.1.1");
        assertThat(example1).isEqualTo("1[.]1[.]1[.]1");

        var example2 = main.defangIPaddr("255.100.50.0");
        assertThat(example2).isEqualTo("255[.]100[.]50[.]0");
    }

    @Test
    void sortSentence() {
        var example1 = main.sortSentence("is2 sentence4 This1 a3");
        assertThat(example1).isEqualTo("This is a sentence");

        var example2 = main.sortSentence("Myself2 Me1 I4 and3");
        assertThat(example2).isEqualTo("Me Myself and I");
    }

    @Test
    void getConcatenation() {
        var example1 = main.getConcatenation(new int[]{1, 2, 1});
        assertThat(example1).isEqualTo(new int[]{1, 2, 1, 1, 2, 1});

        var example2 = main.getConcatenation(new int[]{1, 3, 2, 1});
        assertThat(example2).isEqualTo(new int[]{1, 3, 2, 1, 1, 3, 2, 1});
    }

    @Test
    void shuffle() {
        var example1 = main.shuffle(new int[]{2, 5, 1, 3, 4, 7}, 3);
        assertThat(example1).isEqualTo(new int[]{2, 3, 5, 4, 1, 7});

        var example2 = main.shuffle(new int[]{1, 2, 3, 4, 4, 3, 2, 1}, 4);
        assertThat(example2).isEqualTo(new int[]{1, 4, 2, 3, 3, 2, 4, 1});

        var example3 = main.shuffle(new int[]{1, 1, 2, 2}, 2);
        assertThat(example3).isEqualTo(new int[]{1, 2, 1, 2});
    }

    @Test
    void numIdenticalPairs() {
        var example1 = main.numIdenticalPairs(new int[]{1, 2, 3, 1, 1, 3});
        assertThat(example1).isEqualTo(4);

        var example2 = main.numIdenticalPairs(new int[]{1, 1, 1, 1});
        assertThat(example2).isEqualTo(6);

        var example3 = main.numIdenticalPairs(new int[]{1, 2, 3});
        assertThat(example3).isEqualTo(0);
    }

    @Test
    void runningSum() {
        var example1 = main.runningSum(new int[]{1, 2, 3, 4});
        assertThat(example1).isEqualTo(new int[]{1, 3, 6, 10});

        var example2 = main.runningSum(new int[]{1, 1, 1, 1, 1});
        assertThat(example2).isEqualTo(new int[]{1, 2, 3, 4, 5});

        var example3 = main.runningSum(new int[]{3, 1, 2, 10, 1});
        assertThat(example3).isEqualTo(new int[]{3, 4, 6, 16, 17});
    }

    @Test
    void finalValueAfterOperations() {
        var example1 = main.finalValueAfterOperations(new String[]{"--X", "X++", "X++"});
        assertThat(example1).isEqualTo(1);

        var example2 = main.finalValueAfterOperations(new String[]{"++X", "++X", "X++"});
        assertThat(example2).isEqualTo(3);

        var example3 = main.finalValueAfterOperations(new String[]{"X++", "++X", "--X", "X--"});
        assertThat(example3).isEqualTo(0);
    }

    @Test
    void checkIfPangram() {
        var example1 = main.checkIfPangram("thequickbrownfoxjumpsoverthelazydog");
        assertThat(example1).isTrue();

        var example2 = main.checkIfPangram("leetcode");
        assertThat(example2).isFalse();
    }

    @Test
    void checkTree() {
        var example1 = main.checkTree(
                new TreeNode(
                        10,
                        new TreeNode(4),
                        new TreeNode(6)
                ));
        assertThat(example1).isTrue();

        var example2 = main.checkTree(
                new TreeNode(
                        5,
                        new TreeNode(3),
                        new TreeNode(1)
                ));
        assertThat(example2).isFalse();
    }
}