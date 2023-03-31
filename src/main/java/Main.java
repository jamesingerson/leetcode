import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {

    }

    int doubler(int toDouble) {
        return toDouble * 2;
    }

    public int[] twoSum(int[] nums, int target) {
        // https://leetcode.com/problems/two-sum/
        Map<Integer, Integer> sumCandidates = new HashMap<>();
        // for each num
        for (int i = 0; i < nums.length; i++) {
            // try find the complement (target - nums[i])
            if (sumCandidates.containsKey(target - nums[i])) {
                // return indices on success
                return new int[]{sumCandidates.get(target - nums[i]), i};
            }
            // stash on failed look up
            sumCandidates.put(nums[i], i);
        }
        // return empty on fail
        return new int[]{};

        // Notes:
        // Initially tried to dump everything into the map, then go over it.
        // That was slightly more code and messier, also used a bit more space.
        // Either way, the outcome was linear time and space performance O(n).
    }

    public boolean isPalindrome(int x) {
        // https://leetcode.com/problems/palindrome-number/
        int original = x;
        int reverse = 0;
        // all negative numbers are false
        if (x < 0) {
            return false;
        }

        // we can reverse the number by "popping" the digits with modulo
        while (x != 0) {
            int oneDigit = x % 10;
            reverse = reverse * 10 + oneDigit;
            x /= 10;
        }
        return reverse == original;

        // Notes:
        // There is further optimization here by looking at only half the number.
    }

    public int romanToInt(String s) {
        // https://leetcode.com/problems/roman-to-integer/
        Map<Character, Integer> numeralMap = new HashMap<>();
        numeralMap.put('I', 1);
        numeralMap.put('V', 5);
        numeralMap.put('X', 10);
        numeralMap.put('L', 50);
        numeralMap.put('C', 100);
        numeralMap.put('D', 500);
        numeralMap.put('M', 1000);

        char[] numeralArray = s.toCharArray();
        int sum = 0;
        int previous = 0;

        // Work from the back
        for (int i = numeralArray.length - 1; i >= 0; i--) {
            int toModifyBy = numeralMap.get(numeralArray[i]);
            // only add if larger than the last number
            // subtract if e.g. IV (5 - 1 = 4)
            if (toModifyBy >= previous) {
                sum += toModifyBy;
            } else {
                sum -= toModifyBy;
            }
            previous = toModifyBy;
        }

        return sum;

        // Notes:
        // Can optimize with a switch for the values rather than a map.
    }

    public boolean isValid(String s) {
        // https://leetcode.com/problems/valid-parentheses/
        char[] brackets = s.toCharArray();
        Stack<Character> bracketStack = new Stack<Character>();

        for (char bracket : brackets) {
            if ("([{".indexOf(bracket) > -1) {
                bracketStack.push(bracket);
            } else if (bracketStack.isEmpty()) {
                return false;
            } else {
                char inspect = bracketStack.pop();
                switch (inspect) {
                    case '(':
                        if (bracket != ')') return false;
                        break;
                    case '[':
                        if (bracket != ']') return false;
                        break;
                    case '{':
                        if (bracket != '}') return false;
                        break;
                    default:
                        return false;
                }
            }
        }
        return bracketStack.empty();

        // Notes:
        // Messed this up a couple of times because I hadn't accounted for
        // edge cases. This can be made better with a single switch with
        // fall through for each bracket. Can also push and pop in one switch.
    }

    public int strStr(String haystack, String needle) {
        // https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/
        // I assume this is unacceptable.
        //return haystack.indexOf(needle);
        int needleLength = needle.length();
        for (int i = 0; i <= haystack.length() - needleLength; i++) {
            if (haystack.substring(i, needleLength + i).equals(needle)) {
                return i;
            }
        }
        return -1;

        // Notes:
        // Messed this up because thought I was looking for prefix
        // (brain muddled from previous problem).
    }

    public int searchInsert(int[] nums, int target) {
        // https://leetcode.com/problems/search-insert-position/
        // This is just an odd case of binary search
        int low = 0;
        int high = nums.length - 1;
        int middle = 0;

        while (low <= high) {
            middle = (low + high) / 2;
            if (nums[middle] > target) {
                high = middle - 1;
            } else if (nums[middle] < target) {
                low = middle + 1;
            } else {
                return middle;
            }
        }
        return nums[middle] < target ? middle + 1 : middle;

        // Notes:
        // The slight modification to return is the secret sauce
        // that varies this from a simple search. But it might be
        // possible to return low instead?
    }

    public int[] plusOne(int[] digits) {
        // https://leetcode.com/problems/plus-one/
        for (int i = digits.length -1 ; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;

        // Note:
        // Tried to do this with a cheeky string conversion,
        // got bit by overflow.
    }

    public List<String> fizzBuzz(int n) {
        // https://leetcode.com/problems/fizz-buzz/
        List<String> fizzBuzz = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 5 == 0 && i % 3 == 0) {
                fizzBuzz.add("FizzBuzz");
            } else if (i % 5 == 0) {
                fizzBuzz.add("Buzz");
            } else if (i % 3 == 0) {
                fizzBuzz.add("Fizz");
            } else {
                fizzBuzz.add(Integer.toString(i));
            }
        }
        return fizzBuzz;

        // Notes:
        // Classic problem. Interesting discussion about doing it without %
        // Make separate fizz and buzz vars and increment them each loop,
        // reset them if their condition(s) are met.
        // Can also make it look better with ternary operators.
    }

    public int maxSatisfaction(int[] satisfaction) {
        // https://leetcode.com/problems/reducing-dishes/
        // sort the array so we have the highest output dish first
        Arrays.sort(satisfaction);
        int dishSum = 0;
        int dishValue = 0;
        // Largest to smallest value
        for (int i = satisfaction.length - 1; i >= 0; i--) {
            // Add the new value to our "active" value
            dishValue += satisfaction[i];
            // if it's beneficial to make the new dish
            if (dishValue > 0) {
                // add the new fish + the value of all previous dishes
                // this is where the "time coefficient" lives
                dishSum += dishValue;
            }
        }
        return dishSum;

        // Notes:
        // Didn't really have any business solving this problem myself.
        // Reviewed the solutions and loved it. It's actually so simple.
        // The whole problem can be broken down to "multiplying is just
        // adding repeatedly".
    }

    public int maximumWealth(int[][] accounts) {
        // https://leetcode.com/problems/richest-customer-wealth/
        return Arrays.stream(accounts)
                .mapToInt(x -> Arrays.stream(x).sum())
                .max().orElse(0);
    }

    public int numberOfSteps(int num) {
        //https://leetcode.com/problems/number-of-steps-to-reduce-a-number-to-zero/
        int steps = 0;
        while (num != 0) {
            if (num % 2 == 0) {
                num /= 2;
                steps++;
                continue;
            }
            num -= 1;
            steps++;
        }
        return steps;
    }

    public String defangIPaddr(String address) {
        return address.replace(".", "[.]");
    }

    public String sortSentence(String s) {
        String[] numberedWords = s.split(" ");
        String[] sentence = new String[numberedWords.length];
        for (String numberedWord : numberedWords) {
            int i = Integer.parseInt(String.valueOf(numberedWord.charAt(numberedWord.length() - 1))) - 1;
            String rawWord = numberedWord.substring(0, numberedWord.length() - 1);
            sentence[i] = rawWord;
        }
        return String.join(" ", sentence);
    }

    public int[] getConcatenation(int[] nums) {
        int[] ans = new int[nums.length * 2];
        for (int i = 0; i < nums.length; i++) {
            ans[i] = nums[i];
            ans[i + nums.length] = nums[i];
        }
        return ans;
    }

    public int[] shuffle(int[] nums, int n) {
        int[] ans = new int[nums.length];
        int xpos = 0;
        int ypos = 1;
        for (int i = 0; i < nums.length; i++) {
            if (i < n) {
                ans[xpos] = nums[i];
                xpos += 2;
                continue;
            }
            ans[ypos] = nums[i];
            ypos += 2;
        }
        return ans;

        // Notes:
        // I had a more elegant idea at the start but couldn't
        // figure out the secret sauce I needed. Funnily enough,
        // the +=2 here is the trick.

        // for(int i=0; i< n;i++){
        //     arr[count] = nums[i];
        //     arr[count+1]=nums[i+n];
        //     count +=2;
        // }
    }

    public int numIdenticalPairs(int[] nums) {
        int goodPairs = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i; j < nums.length; j++) {
                if (nums[i] == nums[j] && i < j) {
                    goodPairs++;
                }
            }
        }
        return goodPairs;

        // Notes:
        // One shot this :D
        // Been fumbling a lot with "off by one" errors, but got this right.
    }

    public int[] runningSum(int[] nums) {
        AtomicInteger runSum = new AtomicInteger(0);
        return Arrays.stream(nums).map(runSum::addAndGet).toArray();

        // Notes:
        // Not as performant as raw loops, but good 'n' proper use of streams.
    }


}
