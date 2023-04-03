import com.sun.source.tree.Tree;

import java.lang.reflect.Array;
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
        for (int i = digits.length - 1; i >= 0; i--) {
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
        // https://leetcode.com/problems/defanging-an-ip-address/
        return address.replace(".", "[.]");
    }

    public String sortSentence(String s) {
        // https://leetcode.com/problems/sorting-the-sentence/
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
        // https://leetcode.com/problems/concatenation-of-array/
        int[] ans = new int[nums.length * 2];
        for (int i = 0; i < nums.length; i++) {
            ans[i] = nums[i];
            ans[i + nums.length] = nums[i];
        }
        return ans;
    }

    public int[] shuffle(int[] nums, int n) {
        // https://leetcode.com/problems/shuffle-the-array/
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
        // https://leetcode.com/problems/number-of-good-pairs/
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
        // https://leetcode.com/problems/running-sum-of-1d-array/
        AtomicInteger runSum = new AtomicInteger(0);
        return Arrays.stream(nums).map(runSum::addAndGet).toArray();

        // Notes:
        // Not as performant as raw loops, but good 'n' proper use of streams.
    }

    public int finalValueAfterOperations(String[] operations) {
        // https://leetcode.com/problems/final-value-of-variable-after-performing-operations/
        int i = 0;
        for (String operation : operations) {
            switch (operation) {
                case "++X", "X++" -> i++;
                case "--X", "X--" -> i--;
            }
        }
        return i;
    }

    public boolean checkIfPangram(String sentence) {
        // https://leetcode.com/problems/check-if-the-sentence-is-pangram/
        Set<Character> charSet = new HashSet<>();
        for (Character c : sentence.toCharArray()) {
            charSet.add(c);
        }
        return charSet.size() == 26;
    }

    public double[] convertTemperature(double celsius) {
        // https://leetcode.com/problems/convert-the-temperature/
        return new double[]{celsius + 273.15, celsius * 1.80 + 32.00};
    }

    public int sum(int num1, int num2) {
        // https://leetcode.com/problems/add-two-integers/
        return num1 + num2;
    }

    public boolean checkTree(TreeNode root) {
        // https://leetcode.com/problems/root-equals-sum-of-children/
        return root.left.val + root.right.val == root.val;
    }

    public int kItemsWithMaximumSum(int numOnes, int numZeros, int numNegOnes, int k) {
        // https://leetcode.com/problems/k-items-with-the-maximum-sum/
        if (k <= numOnes) {
            return k;
        }

        if (k <= numOnes + numZeros) {
            return numOnes;
        }

        return numOnes - (k - numOnes - numZeros);
    }

    public int[] evenOddBit(int n) {
        // https://leetcode.com/problems/number-of-even-and-odd-bits/
        StringBuilder reverseBinary = new StringBuilder();
        reverseBinary.append(Integer.toBinaryString(n)).reverse();
        char[] binary = reverseBinary.toString().toCharArray();

        int even = 0;
        int odd = 0;
        for (int i = 0; i < binary.length; i++) {
            if (i % 2 == 0) {
                if (binary[i] == '1') {
                    even++;
                }
            } else if (binary[i] == '1') {
                odd++;
            }
        }
        return new int[]{even, odd};

        // Notes:
        // Could've checked if the char is one then checked if even otherwise
        // incremented odd. Would've been tidier.
        // There's also some fancy bit stuff you can do.
    }

    public int vowelStrings(String[] words, int left, int right) {
        // https://leetcode.com/problems/count-the-number-of-vowel-strings-in-range/
        return Arrays.stream(words, left, right + 1)
                .mapToInt(x -> (
                        "aeiou".indexOf(x.charAt(0)) != -1 &&
                                "aeiou".indexOf(x.charAt(x.length() - 1)) != -1
                ) ? 1 : 0).sum();
    }

    public int passThePillow(int n, int time) {
        // https://leetcode.com/problems/pass-the-pillow/

        // it takes 2n - 2 time for the pillow to be back at the start
        // pillow changes direction every n - 1 time

        // we only care about difference from starting position
        time %= (2 * n - 2);

        // if were moving forward, we just return time + 1
        if (time <= n - 1) {
            return 1 + time;
        }

        // if we're moving backwards, we subtract abs(n - 1 - time)
        return n - (-1 * (n - 1 - time));

        // Notes:
        // The last formula was a pain. It was difficult to figure out
        // where this was falling apart.
    }

    public int splitNum(int num) {
        // https://leetcode.com/problems/split-with-minimum-sum/
        int numLength = (int) Math.log10(num) + 1;
        int[] nums = new int[numLength];
        int i = 0;
        while (num > 0) {
            nums[i] = num % 10;
            num /= 10;
            i++;
        }
        Arrays.sort(nums);
        int num1 = 0;
        int num2 = 0;
        for (i = 0; i <= numLength - 1; i++) {
            if (i % 2 != 0) {
                num1 *= 10;
                num1 += nums[i];
                continue;
            }
            num2 *= 10;
            num2 += nums[i];
        }
        return num1 + num2;

        // Notes:
        // Lots of other solutions did string tricks. I don't think
        // that's the best/correct/proper way. The while loop here
        // feels awkward, though. Could've done fori using numLength?
    }

    public long pickGifts(int[] gifts, int k) {
        // https://leetcode.com/problems/take-gifts-from-the-richest-pile/
        for (int i = 1; i <= k; i++) {
            int highestIndex = 0;
            for (int j = 1; j < gifts.length; j++) {
                if (gifts[j] > gifts[highestIndex]) {
                    highestIndex = j;
                }
            }
            gifts[highestIndex] = (int) Math.floor(Math.sqrt(gifts[highestIndex]));
        }
        long sum = 0;
        for (int i : gifts) {
            sum += i;
        }
        return sum;

        // Notes:
        // As usual I got bit by not reading things properly.
        // Needed a long to sum everything.
    }

    public String longestCommonPrefix(String[] strs) {
        // https://leetcode.com/problems/longest-common-prefix/
        if (strs.length == 0) {
            return "";
        }

        if (strs.length == 1) {
            return strs[0];
        }

        Arrays.sort(strs);
        char[] word1 = strs[0].toCharArray();
        char[] word2 = strs[strs.length - 1].toCharArray();
        int i = 0;

        for (i = 0; i <= word1.length - 1; i++) {
            if (word1[i] != word2[i]) {
                break;
            }
        }
        return String.valueOf(word1).substring(0, i);

        // Notes:
        // I made a lot of mistakes and got bitten by nearly
        // every test case on this one :\
    }

    public int search(int[] nums, int target) {
        // https://leetcode.com/problems/binary-search/
        int min = 0;
        int max = nums.length - 1;

        while (min <= max) {
            int index = (min + max) / 2;
            if (nums[index] == target) {
                return index;
            }
            if (nums[index] > target) {
                max = index - 1;
            }
            if (nums[index] < target) {
                min = index + 1;
            }
        }
        return -1;
        // Notes:
        // Still messing up adjusting max and min.
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // https://leetcode.com/problems/merge-two-sorted-lists/
        ListNode sortedListMarker = new ListNode(-1);
        ListNode currentNode = sortedListMarker;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                currentNode.next = list1;
                list1 = list1.next;
            } else {
                currentNode.next = list2;
                list2 = list2.next;
            }
            currentNode = currentNode.next;
        }

        if (list1 == null) {
            currentNode.next = list2;
        } else {
            currentNode.next = list1;
        }
        return sortedListMarker.next;

        // Notes:
        // I had very a very strong idea but it was slightly off.
        // Can't get IDE tests to work for this without extra work.
    }

    public int removeDuplicates(int[] nums) {
        // https://leetcode.com/problems/remove-duplicates-from-sorted-array/
        int lastLargest = 1;
        int countUniqueNums = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = lastLargest; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    lastLargest++;
                    continue;
                }
                nums[i + 1] = nums[j];
                countUniqueNums++;
                break;
            }
        }

        return countUniqueNums;
    }

    public int removeElement(int[] nums, int val) {
        // https://leetcode.com/problems/remove-element/
        int position = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[position] = nums[i];
                position++;
            }
        }
        return position;

        // Notes:
        // I really over-engineered this trying to do clever things moving
        // values from the end of the array. All it really takes is building
        // a new array of unique values.
    }

    public int lengthOfLastWord(String s) {
        // https://leetcode.com/problems/length-of-last-word/
        s = s.trim();
        int index = s.lastIndexOf(" ");
        String lastWord = s.substring(index + 1);
        return lastWord.length();
    }

    public int[] successfulPairsFail(int[] spells, int[] potions, long success) {
        // https://leetcode.com/problems/successful-pairs-of-spells-and-potions/
        return Arrays.stream(spells)
                .map(x -> (int) Arrays.stream(potions)
                        .mapToLong(y -> (long) y * x)
                        .filter(z -> z >= success)
                        .count())
                .toArray();
        // Notes:
        // This solution produces the correct output but hits
        // the execution time limit because it's brute force.
        // The correct approach is to sort the potions and
        // calculate the needed value and indexing into that.
        // Leverage a tree map +
        // long need = (success + spells[i] - 1) / spells[i];
        //     spells[i] = potions.length - map.ceilingEntry(need).getValue();

        // Another approach is to implement binary search on
        // indexing into the potions array.
        // long product = (long) spell * potions[mid];
        // if (product >= success)
    }

    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        // https://leetcode.com/problems/successful-pairs-of-spells-and-potions/
        int n = spells.length;
        int m = potions.length;
        int[] casts = new int[n];
        Arrays.sort(potions);
        // For each spell
        for (int i = 0; i < n; i++) {
            int spell = spells[i];
            int min = 0;
            int max = m - 1;
            // Binary search the potions
            while (min <= max) {
                int mid = (min + max) / 2;
                // Trying to find out if the target index
                // will make a successful cast
                long product = (long) spell * potions[mid];
                if (product >= success) {
                    max = mid - 1;
                } else {
                    min = mid + 1;
                }
            }
            // Store the number of successful casts
            // offset array length by floor value
            casts[i] = m - min;
        }
        return casts;
    }

    public int mySqrt(int x) {
        // https://leetcode.com/problems/sqrtx/
        int max = x;
        int min = 0;
        int mid = min + (max - min) / 2;
        while (min <= max) {
            mid = (min + max) / 2;
            long square = (long) mid * mid;
            if (square == x) {
                return (int) mid;
            }
            if (square >= x) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return max;

        // Notes:
        // Originally had max as Integer.MAX_VALUE.
        // Later realised that x itself is the ceiling.
    }

    public int maxProfit(int[] prices) {
        // https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
        int lowest = Integer.MAX_VALUE;
        int highest = -1;
        int bestSeen = 0;

        for (int price : prices) {
            if (price < lowest) {
                lowest = price;
                highest = -1;
            }
            if (price > highest) {
                highest = price;
                if (highest - lowest > bestSeen) {
                    bestSeen = highest - lowest;
                }
            }
        }

        return bestSeen;

        // Notes:
        // First attempt didn't account for the possibility that the
        // most efficient trade might not be the final trade tracked.
        // I also made a dumb mistake where I used the for each loop
        // syntax then tried to index into the array with the value.
    }

    public boolean isPalindrome(String s) {
        // https://leetcode.com/problems/valid-palindrome/
        s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String sReverse = new StringBuilder(s).reverse().toString().toLowerCase();
        return s.equals(sReverse) || s.length() == 0;

        // Notes:
        // This is the simple, yet slow way.
        // It is obviously faster to leverage character comparisons
        // in a char array, converging towards the middle or only looking
        // at half the string.
        // There is also the option to use a StringBuffer vs StringBuilder.
        // I'm not sure which is more appropriate.
        // I don't really get how the ^ works in the regex here. I understand
        // what it normally does, but don't see how its removal inverts the
        // behaviour in this case.
    }

    public TreeNode invertTree(TreeNode root) {
        // https://leetcode.com/problems/invert-binary-tree/
        if (root == null) {
            return root;
        }

        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);

        root.left = right;
        root.right = left;

        return root;

        // Notes:
        // Had to look this up, misunderstood the question at first
        // (thought it only wanted the root inverted).
    }

    public boolean isAnagram(String s, String t) {
        // https://leetcode.com/problems/valid-anagram/
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> characterCount = new HashMap<>();
        for (char cInS : s.toCharArray()) {
            characterCount.put(cInS, characterCount.getOrDefault(cInS, 0) + 1);
        }
        for (char cInT : t.toCharArray()) {
            if (characterCount.get(cInT) == null) {
                return false;
            }
            if (characterCount.get(cInT) == 1) {
                characterCount.remove(cInT);
            } else {
                characterCount.put(cInT, characterCount.get(cInT) - 1);
            }
        }
        return characterCount.keySet().isEmpty();

        // Notes:
        // I need a bit more practice with map methods. But had the
        // algorithm down and just had to tinker.
    }

    public int numRescueBoats(int[] people, int limit) {
        // https://leetcode.com/problems/boats-to-save-people/
        int min = 0;
        int max = people.length - 1;
        int boats = 0;
        Arrays.sort(people);

        while (min <= max) {
            boats++;
            if (people[min] + people[max] <= limit) {
                min++;
            }
            max--;
        }

        return boats;

        // Notes:
        // Made a mistake, max must be decremented after the check.
        // I also knew the array needed to be sorted, but didn't actually
        // code that at first.
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        // https://leetcode.com/problems/flood-fill/
        int startingColour = image[sr][sc];
        if (color != startingColour) floodFillSearch(image, sr, sc, color, startingColour);
        return image;

        // Notes:
        // I was trying to figure out how to do this with just the given
        // method signature. I also got some of the conditions wrong.
        // I've written it with braces, but there's probably an
        // argument for not using them.
        // You can also defensively check the conditions at the start of
        // floodFillSearch rather than checking them pre-call.
    }

    public void floodFillSearch(int[][] image, int sr, int sc, int color, int oldColour) {
        if (image[sr][sc] == oldColour) {
            image[sr][sc] = color;
            if (sr - 1 >= 0) {
                floodFillSearch(image, sr - 1, sc, color, oldColour);
            }
            if (sc - 1 >= 0) {
                floodFillSearch(image, sr, sc - 1, color, oldColour);
            }
            if (sr + 1 < image.length) {
                floodFillSearch(image, sr + 1, sc, color, oldColour);
            }
            if (sc + 1 < image[0].length) {
                floodFillSearch(image, sr, sc + 1, color, oldColour);
            }
        }
    }

    public boolean isBalanced(TreeNode root) {
        // https://leetcode.com/problems/balanced-binary-tree/
        if (root == null) {
            return true;
        }

        int leftHeight = treeHeight(root.left);
        int rightHeight = treeHeight(root.right);

        return Math.abs(rightHeight - leftHeight) <= 1 &&
                isBalanced(root.left) && isBalanced(root.right);

        // Notes:
        // Forgot to add one in the height calculation, which was silly.
        // Needed to && the child nodes being balanced as well, also silly.
    }

    public int treeHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(treeHeight(root.left), treeHeight(root.right));
    }

    public boolean hasCycle(ListNode head) {
        // https://leetcode.com/problems/linked-list-cycle/
        Set<ListNode> nodeSet = new HashSet<>();
        while (head != null) {
            if (!nodeSet.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;

        // Notes:
        // This can also be achieved with fast/slow pointers
        // looking at .next and .next.next until they're equal.
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        return root;

        // Notes:
        // One day I'll reflect on this and see it as obvious. But
        // right now it feels like you either know it or you don't.
        // There's also a pretty swish direct walk solution by
        // multiplying the difference between the values and the targets
        // and checking if it's bigger than 0 or not.
    }

    public int firstBadVersion(int n) {
        // https://leetcode.com/problems/first-bad-version/

        /* The isBadVersion API is defined in the parent class VersionControl.
           boolean isBadVersion(int version); */

        int min = 0;
        int max = n;
        int lowestBadVersion = n;

        while (min <= max) {
            int mid = (max + min) >>> 1;
            if (isBadVersion(mid)) {
                max = mid - 1;
                if (mid < lowestBadVersion) {
                    lowestBadVersion = mid;
                }
            } else {
                min = mid + 1;
            }
        }
        return lowestBadVersion;

        // Notes:
        // Trick here is that if you do the (mid + max) / 2 as
        // your midpoint calculation, you'll overflow.
    }

    public boolean isBadVersion(int n) {
        // just to quieten the compiler
        return true;
    }

    public boolean canConstruct(String ransomNote, String magazine) {
        // https://leetcode.com/problems/ransom-note/
        Map<Character, Integer> charMap = new HashMap<>();
        for (char charInMagazine : magazine.toCharArray()) {
            charMap.put(charInMagazine,
                    charMap.getOrDefault(charInMagazine, 0) + 1);
        }
        for (char charInNote : ransomNote.toCharArray()) {
            if (charMap.get(charInNote) == null) {
                return false;
            }

            int charCount = charMap.get(charInNote);
            if (charCount == 1) {
                charMap.remove(charInNote);
            } else {
                charMap.put(charInNote, --charCount);
            }
        }
        return true;

        // Notes:
        // Could use .containsKey() rather than .get() == null
    }

    public ListNode reverseListStack(ListNode head) {
        // https://leetcode.com/problems/reverse-linked-list/
        if (head == null || head.next == null) {
            return head;
        }
        Stack<ListNode> nodeStack = new Stack<>();
        while (head != null) {
            nodeStack.push(head);
            head = head.next;
        }
        ListNode reversedListHead = nodeStack.pop();
        ListNode reversedListCurrent = reversedListHead;
        while (!nodeStack.isEmpty()) {
            reversedListCurrent.next = nodeStack.pop();
            reversedListCurrent = reversedListCurrent.next;
        }
        reversedListCurrent.next = null;
        return reversedListHead;

        // Notes:
        // So this works but is totally wrong, you can do this in place iteratively.
        // I've implemented that below.
    }

    public ListNode reverseListIterative(ListNode head) {
        // https://leetcode.com/problems/reverse-linked-list/

        ListNode previous = null;
        ListNode current = head;

        // Until we've traversed the whole list
        while (current != null) {
            // The next node is the child of what we're currently looking at
            ListNode next = current.next;
            // The one we're looking at should point to the previous node
            current.next = previous;
            // For the next iteration, the current node will be "previous"
            previous = current;
            // For the next iteration, move to the next node
            current = next;
        }
        // Return previous because we overshoot the list
        return previous;

        // Notes:
        // There is also a recursive approach. I've implemented that below.
        // if (head == null || head.next == null) return head;
        // ListNode res = reverseList(head.next);
        // head.next.next = head;
        // head.next = null;
        // return res;
    }

    public ListNode reverseList(ListNode head) {
        // Base case, head or its child is null
        if (head == null || head.next == null) {
            return head;
        }
        // Recursive call, the reversed list is the reversed list of the
        // next node until we've been through them all
        ListNode reverse = reverseList(head.next);
        // so at this stage you have a reversely sorted list that doesn't
        // include you (where you = previous head of your own sublist)
        // (you)     null <- (node) <- (node) [these have been reversed already]
        // you need to set your next nodes next property to point at you
        // rather than null
        head.next.next = head;
        // and you should take the role of pointing to null
        // (because we're recursive, each node just assumes it's the last one)
        head.next = null;
        return reverse;
    }
}
