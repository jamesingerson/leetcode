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

        var example4 = main.isValid("[");
        assertThat(example4).isFalse();

        var example5 = main.isValid("]");
        assertThat(example5).isFalse();
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

        var example4 = main.plusOne(new int[]{9,8,7,6,5,4,3,2,1,0});
        assertThat(example4).isEqualTo(new int[]{9,8,7,6,5,4,3,2,1,1});
    }

    @Test
    void fizzBuzz() {
        var example1 = main.fizzBuzz(3);
        assertThat(example1).isEqualTo(Arrays.asList("1","2","Fizz"));

        var example2 = main.fizzBuzz(5);
        assertThat(example2).isEqualTo(Arrays.asList("1","2","Fizz","4","Buzz"));

        var example3 = main.fizzBuzz(15);
        assertThat(example3).isEqualTo(Arrays.asList("1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","14","FizzBuzz"));
    }
}