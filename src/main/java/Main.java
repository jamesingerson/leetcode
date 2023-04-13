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

    public ListNode reverseListRecursive(ListNode head) {
        // https://leetcode.com/problems/reverse-linked-list/
        // Base case, head or its child is null
        if (head == null || head.next == null) {
            return head;
        }
        // Recursive call, the reversed list is the reversed list of the
        // next node until we've been through them all
        ListNode reverse = reverseListRecursive(head.next);
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

    public boolean containsDuplicate(int[] nums) {
        // https://leetcode.com/problems/contains-duplicate/
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            if (!numSet.add(num)) {
                return true;
            }
        }
        return false;
    }

    public int climbStairs(int n) {
        // https://leetcode.com/problems/climbing-stairs/
        int minor = 1;
        int major = 1;

        if (n == 0) {
            return minor;
        }

        for (int i = 2; i <= n; i++) {
            int current = minor + major;
            minor = major;
            major = current;
        }

        return major;

        // Notes:
        // This is just fibonacci except starting from 1 1 rather than 0 1.
        // This is a space optimized variant. Which is good for getting
        // fib(x) but not great if you want a list.
    }

    public int maxSubArray(int[] nums) {
        // https://leetcode.com/problems/maximum-subarray/
        int highestSum = Integer.MIN_VALUE;
        int sumToPosition = 0;

        for (int num : nums) {
            sumToPosition += num;
            if (sumToPosition > highestSum) {
                highestSum = sumToPosition;
            }
            if (sumToPosition < 0) {
                sumToPosition = 0;
            }
        }
        return highestSum;

        // Notes:
        // This is called Kadane's algorithm.
        // The trick is realising that if your running sum is less than
        // zero, then you may as well throw out whatever you've got going
        // and start fresh.
    }

    public ListNode middleNode(ListNode head) {
        // https://leetcode.com/problems/middle-of-the-linked-list/
        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;

        // Notes:
        // Felt pretty good arriving at this solution.
    }

    public int maxDepth(TreeNode root) {
        // https://leetcode.com/problems/maximum-depth-of-binary-tree/
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        // https://leetcode.com/problems/diameter-of-binary-tree/
        longestWalk(root);
        return diameter;
    }

    public int longestWalk(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = longestWalk(root.left);
        int right = longestWalk(root.right);

        diameter = Math.max(diameter, left + right);

        return Math.max(left, right) + 1;

        // Notes:
        // Had a nightmarish time getting this one right. Off by one
        // all over the place. Had trouble navigating the fact that
        // a class variable or otherwise object was needed instead of
        // just passing an int down. Also tripped up making the initial call
        // diameter = longestWalk(root); which is NOT what you want here.
    }

    public int majorityElement(int[] nums) {
        // https://leetcode.com/problems/majority-element/
        Map<Integer, Integer> numMap = new HashMap<>();
        int greatestValue = Integer.MIN_VALUE;
        int greatestKey = 0;
        for (int num : nums) {
            numMap.put(num, numMap.getOrDefault(num, 0) + 1);
        }
        for (int key : numMap.keySet()) {
            if (numMap.get(key) > greatestValue) {
                greatestKey = key;
                greatestValue = numMap.get(key);
            }
        }
        return greatestKey;

        // Notes:
        // This works, but it would've been better to put then immediately
        // get and check if the value is over half the length. (Which was
        // actually my first thought, but I wound up over engineering here.)
        // Also, you can obviously sort and jump to (length - 1) / 2 for a
        // more time, less space solution.
    }

    public int partitionString(String s) {
        // https://leetcode.com/problems/optimal-partition-of-string/
        int partitionCount = 1;
        Set<Character> charSet = new HashSet<>();
        for (char c : s.toCharArray()) {
            boolean newAddition = charSet.add(c);
            if (!newAddition) {
                partitionCount++;
                charSet.clear();
                charSet.add(c);
            }
        }
        return partitionCount;

        // Notes:
        // Happy that I came up with this. Pretty straightforward once you
        // realise you only care about the partition count and not the
        // string itself. You can do some uber leet character math and bit
        // manipulation and do it with no additional space.
    }

    public int longestPalindrome(String s) {
        // https://leetcode.com/problems/longest-palindrome/
        int oddCharacter = 0;
        int pairedCharacters = 0;
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        int i = 0;
        while (i <= chars.length - 1) {
            if (i + 1 <= chars.length - 1 && chars[i] == chars[i + 1]) {
                pairedCharacters++;
                i++;
            } else if (oddCharacter == 0) {
                oddCharacter = 1;
            }
            i++;
        }
        return pairedCharacters * 2 + oddCharacter;

        // Notes:
        // Sort is expensive, but there's a low character limit here, so it's
        // actually pretty performant. The alternative approach I also thought
        // of that seems more common is a map (or a set). You only care about
        // the count of odd letters because the result is either:
        // length - oddCount + 1
        // or length
        // You can also do a frequency array. That's the solution which is
        // faster than this.
    }

    public String addBinary(String a, String b) {
        // https://leetcode.com/problems/add-binary/
        int carry = 0;
        int character = 0;
        int aIndex = a.length() - 1;
        int bIndex = b.length() - 1;
        StringBuilder sum = new StringBuilder();

        while (aIndex >= 0 || bIndex >= 0 || carry != 0) {
            if (aIndex >= 0) {
                character += a.charAt(aIndex) - '0';
                aIndex--;
            }
            if (bIndex >= 0) {
                character += b.charAt(bIndex) - '0';
                bIndex--;
            }
            if (character == 2) {
                if (carry == 0) {
                    sum.append(0);
                } else {
                    sum.append(1);
                }
                carry = 1;
            }
            if (character == 1) {
                if (carry == 0) {
                    sum.append(1);
                } else {
                    sum.append(0);
                }
            }
            if (character == 0) {
                if (carry == 0) {
                    sum.append(0);
                } else {
                    sum.append(1);
                    carry = 0;
                }
            }
            if (carry == 1 && aIndex == -1 && bIndex == -1) {
                sum.append(1);
                carry = 0;
            }
            character = 0;
        }
        return sum.reverse().toString();

        // Notes:
        // This is heinous and impractical compared to the God Mode solution:
//        while (i >= 0 || j >= 0 || carry == 1) {
//            if (i >= 0) {
//                carry += a.charAt(i--) - '0';
//            }
//            if (j >= 0) {
//                carry += b.charAt(j--) - '0';
//            }
//            sb.append(carry % 2);
//            carry /= 2;
//        }
        // But it's my own machination and it works.
    }

    public int evalRPN(String[] tokens) {
        // https://leetcode.com/problems/evaluate-reverse-polish-notation/
        Stack<Integer> polishNotation = new Stack<>();
        for (String token : tokens) {
            switch (token) {
                case ("+") -> {
                    polishNotation.push(polishNotation.pop()
                            + polishNotation.pop());
                }
                case ("-") -> {
                    int subtract = polishNotation.pop();
                    polishNotation.push(polishNotation.pop()
                            - subtract);
                }
                case ("/") -> {
                    int denominator = polishNotation.pop();
                    polishNotation.push(polishNotation.pop()
                            / denominator);
                }
                case ("*") -> {
                    polishNotation.push(polishNotation.pop()
                            * polishNotation.pop());
                }
                default -> polishNotation.push(Integer.parseInt(token));
            }
        }
        return polishNotation.pop();

        // Notes:
        // I realised that division is "directional" but didn't catch that
        // subtraction is as well at first.
        // I initially ran with a string stack. This was bulky/cumbersome.
        // Much cleaner to have refactored. Need to make a habit of reviewing
        // for cleanliness/efficiencies after I get things working.
        // Also was using an unneeded variable that I tidied up.
    }

    public int[] buildArray(int[] nums) {
        // https://leetcode.com/problems/build-array-from-permutation/
        int[] answer = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            answer[i] = nums[nums[i]];
        }
        return answer;
    }

    public int[] leftRigthDifference(int[] nums) {
        // https://leetcode.com/problems/left-and-right-sum-differences/
        int[] leftSum = new int[nums.length];
        int[] rightSum = new int[nums.length];
        int[] answer = new int[nums.length];
        int currentSum = 0;
        for (int i = 0; i < nums.length; i++) {
            leftSum[i] = currentSum;
            currentSum += nums[i];
        }
        currentSum = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            rightSum[i] = currentSum;
            currentSum += nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            answer[i] = Math.abs(leftSum[i] - rightSum[i]);
        }
        return answer;
    }

    public int numJewelsInStones(String jewels, String stones) {
        // https://leetcode.com/problems/jewels-and-stones/
        int jewelCount = 0;
        char[] individualStones = stones.toCharArray();
        for (char possibleJewel : individualStones) {
            if (jewels.indexOf(possibleJewel) > -1) {
                jewelCount++;
            }
        }
        return jewelCount;
    }

    public int smallestEvenMultiple(int n) {
        // https://leetcode.com/problems/smallest-even-multiple/
        if (n % 2 == 0) {
            return n;
        }
        return n * 2;
    }

    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        // https://leetcode.com/problems/kids-with-the-greatest-number-of-candies/
        int highestCandies = Arrays.stream(candies).max().orElse(0);
        List<Boolean> willHaveMost = new ArrayList<>();
        for (int childsCandies : candies) {
            if (childsCandies + extraCandies >= highestCandies) {
                willHaveMost.add(true);
            } else {
                willHaveMost.add(false);
            }
        }
        return willHaveMost;

        // Notes:
        // Could (should?) obviously find the max value by iterating,
        // but streams are cool.
    }

    public int mostWordsFound(String[] sentences) {
        // https://leetcode.com/problems/maximum-number-of-words-found-in-sentences/
        return Arrays.stream(sentences)
                .mapToInt(x -> x.split(" ").length)
                .max().orElse(0);

        // Notes:
        // Could iterate but streams are cool. An interesting alternative
        // iterative solution involved StringTokenizer.
    }

    public int minimizeArrayValue(int[] nums) {
        // https://leetcode.com/problems/minimize-maximum-of-array/
        long sum = 0;
        long result = 0;
        for (int i = 0; i < nums.length; ++i) {
            sum += nums[i];
            result = Math.max(result, (sum + i) / (i + 1));
        }
        return (int) result;

        // Notes:
        // I understand the implementation, but not how to see it
        // in the first place. Somehow you're supposed to realise that
        // the proposed operation doesn't actually change the sum and
        // therefore conclude that the upper boundary is this offset average.
    }

    public int minimumSum(int num) {
        // https://leetcode.com/problems/minimum-sum-of-four-digit-number-after-splitting-digits/
        int[] digits = new int[4];
        for (int i = 0; i <= 3; i++) {
            digits[i] = num % 10;
            num /= 10;
        }
        Arrays.sort(digits);
        int num1 = 0;
        int num2 = 0;
        for (int i = 0; i <= 3; i++) {
            if ((i + 1) % 2 != 0) {
                num1 *= 10;
                num1 += digits[i];
            } else {
                num2 *= 10;
                num2 += digits[i];
            }
        }
        return num1 + num2;

        // Notes:
        // The second iteration here is overkill. Because we know it's 4 digits,
        // we can hard code it. On the other hand, if I changed the array size
        // and indices to be calculated on log 10 of num, then this would
        // generalize to arbitrarily sized positive numbers;
    }

    public int subtractProductAndSum(int n) {
        // https://leetcode.com/problems/subtract-the-product-and-sum-of-digits-of-an-integer/
        int sum = 0;
        int product = 1;
        int[] digits = new int[(int) (Math.log10(n) + 1)];
        int i = 0;
        while (n > 0) {
            digits[i] = n % 10;
            n /= 10;
            i++;
        }
        for (int digit : digits) {
            sum += digit;
            product *= digit;
        }
        return product - sum;

        // Notes:
        // Storing the digits here is overkill, can just slap them straight
        // into sum and product.
    }

    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        // https://leetcode.com/problems/find-a-corresponding-node-of-a-binary-tree-in-a-clone-of-that-tree/
        if (cloned == null) {
            return null;
        }
        if (cloned.val == target.val) {
            return cloned;
        }
        TreeNode leftSearch = getTargetCopy(original, cloned.left, target);
        TreeNode rightSearch = getTargetCopy(original, cloned.right, target);
        return leftSearch != null ? leftSearch : rightSearch;

        // Notes:
        // Feeling much more comfortable with basic tree stuff.
    }

    public int minPartitions(String n) {
        // https://leetcode.com/problems/partitioning-into-minimum-number-of-deci-binary-numbers/
        char[] digits = n.toCharArray();
        int minPartitions = 0;
        for (char digit : digits) {
            minPartitions = Math.max(minPartitions, digit - '0');
        }
        return minPartitions;

        // Notes:
        // Could just make an index value and iterate over charAt();
        // Could also use a stream over the chars array.
    }

    public ListNode mergeNodes(ListNode head) {
        // https://leetcode.com/problems/merge-nodes-in-between-zeros/
        ListNode answerHead = new ListNode();
        ListNode answerHelp = answerHead;
        int runningSum = 0;
        head = head.next;
        while (head.next != null) {
            runningSum += head.val;
            if (head.val == 0) {
                answerHelp.next = new ListNode(runningSum);
                answerHelp = answerHelp.next;
                runningSum = 0;
            }
            head = head.next;
        }
        answerHelp.next = new ListNode(runningSum);
        return answerHead.next;

        // Notes:
        // I feel like (well, know) there's some way to write this such that
        // the extra node at the end could be encapsulated in the loop.
    }

    public int[][] sortTheStudents(int[][] score, int k) {
        // https://leetcode.com/problems/sort-the-students-by-their-kth-score/
        Arrays.sort(score, (x, y) -> y[k] - x[k]);
        return score;

        // Notes:
        // Had this completely correct from the get go but forgot that
        // Arrays.sort operates in place without returning so got all confused.
        // Battled with a comparator in a stream but then realised it's just
        // this easy.
    }

    public int[] pivotArray(int[] nums, int pivot) {
        // https://leetcode.com/problems/partition-array-according-to-given-pivot/
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> pivots = new ArrayList<>();
        for (int num : nums) {
            if (num < pivot) {
                left.add(num);
            } else if (num > pivot) {
                right.add(num);
            } else {
                pivots.add(num);
            }
        }
        left.addAll(pivots);
        left.addAll(right);
        return left.stream().mapToInt(x -> x).toArray();

        // Notes:
        // This is the noob way of doing this. Pivots should probably
        // be a count and then fill.
        // You can do this with an array of the same size as nums.
        // Two pointers at far left and right, build towards middle.
    }

    public int maxWidthOfVerticalArea(int[][] points) {
        // https://leetcode.com/problems/widest-vertical-area-between-two-points-containing-no-points/
        Arrays.sort(points, (x, y) -> x[0] - y[0]);
        int largestGapSeen = 0;
        for (int i = 0; i < points.length - 2; i++) {
            largestGapSeen = Math.max(largestGapSeen, points[i + 1][0] - points[i][0]);
        }
        return largestGapSeen;

        // Notes:
        // I took Intellij's advice and swapped my lambda with the comparator.
        // This was actually a significant (by LeetCode's standards) performance
        // hit. This was surprising as I'd assume the comparator would just expand
        // to exactly the same check anyway, evidently not.
    }

    public int maxCoins(int[] piles) {
        // https://leetcode.com/problems/maximum-number-of-coins-you-can-get/
        Arrays.sort(piles);
        int sum = 0;
        int totalPiles = piles.length / 3;
        for (int i = piles.length - 2; i > (piles.length - 2) - (2 * totalPiles); i -= 2) {
            sum += piles[i];
        }
        return sum;

        // Notes:
        // There's probably too much logic in the for loop condition.
    }

    public int countDistinctIntegers(int[] nums) {
        // https://leetcode.com/problems/count-number-of-distinct-integers-after-reverse-operations/
        Set<Integer> uniqueNums = new HashSet<>();
        int reversedNum = 0;
        for (int num : nums) {
            uniqueNums.add(num);
            reversedNum = 0;
            while (num > 0) {
                reversedNum *= 10;
                reversedNum += num % 10;
                num /= 10;
            }
            uniqueNums.add(reversedNum);
        }
        return uniqueNums.size();
    }

    public int triangularSum(int[] nums) {
        // https://leetcode.com/problems/find-triangular-sum-of-an-array/
        while (nums.length != 1) {
            int[] newNums = new int[nums.length - 1];
            for (int i = 0; i < nums.length - 1; i++) {
                newNums[i] = (nums[i] + nums[i + 1]) % 10;
            }
            nums = newNums;
        }
        return nums[0];

        // Notes:
        // You can write this recursively as depth first search,
        // but in this case I don't really see the merit.
    }

    public int numIslands(char[][] grid) {
        // https://leetcode.com/problems/number-of-islands/
        int numIslands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    numIslands++;
                    markIsland(grid, i, j, '*');
                }
            }
        }
        return numIslands;
    }

    public void markIsland(char[][] grid, int x, int y, char mark) {
        if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length) {
            return;
        }
        if (grid[x][y] == '1') {
            grid[x][y] = mark;
            markIsland(grid, x - 1, y, mark);
            markIsland(grid, x, y - 1, mark);
            markIsland(grid, x + 1, y, mark);
            markIsland(grid, x, y + 1, mark);
        }
    }

    public int closedIsland(int[][] grid) {
        // https://leetcode.com/problems/number-of-closed-islands/description/
        int numIslands = 0;

        // Mark outer row islands as seen (because they don't count)
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][0] == 0) {
                markIsland(grid, i, 0, -1);
            }
            if (grid[i][grid[0].length - 1] == 0) {
                markIsland(grid, i, grid[0].length - 1, -1);
            }
        }

        // Mark outer column islands as seen (because they don't count)
        for (int i = 0; i < grid[0].length; i++) {
            if (grid[0][i] == 0) {
                markIsland(grid, 0, i, -1);
            }
            if (grid[grid.length - 1][i] == 0) {
                markIsland(grid, grid.length - 1, i, -1);
            }
        }

        // Count remaining islands
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    numIslands++;
                    markIsland(grid, i, j, -1);
                }
            }
        }
        return numIslands;

        // Notes:
        // Got caught on the inversion of island and land number
        // from an earlier problem. Can probably remove the edges
        // from the remaining isle count.
        // Can probably simplify the edge clear as well, but this
        // way, it's apparent what's going on, if verbose.
    }

    public void markIsland(int[][] grid, int x, int y, int mark) {
        if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length) {
            return;
        }
        if (grid[x][y] == 0) {
            grid[x][y] = mark;
            markIsland(grid, x - 1, y, mark);
            markIsland(grid, x, y - 1, mark);
            markIsland(grid, x + 1, y, mark);
            markIsland(grid, x, y + 1, mark);
        }
    }

    public int[] decode(int[] encoded, int first) {
        // https://leetcode.com/problems/decode-xored-array/
        int[] decoded = new int[encoded.length + 1];
        decoded[0] = first;
        for (int i = 0; i < decoded.length - 1; i++) {
            decoded[i + 1] = decoded[i] ^ encoded[i];
        }
        return decoded;

        // Notes:
        // I knew XOR was the inverse of XOR, but struggled to get this:
        // decoded[i + 1] = decoded[i] ^ encoded[i];
        // the right way around because of how the question was worded.
    }

    public int[] findArray(int[] pref) {
        // https://leetcode.com/problems/find-the-original-array-of-prefix-xor/
        int[] decoded = new int[pref.length];
        decoded[0] = pref[0];
        for (int i = 0; i < decoded.length - 1; i++) {
            decoded[i + 1] = decoded[i] ^ pref[i];
        }
        return decoded;

        // Notes:
        // Funnily enough, found this question after the above. This:
        // decoded[i + 1] = decoded[i] ^ pref[i];
        // It actually how I wrote the earlier question.
    }

    public int balancedStringSplit(String s) {
        // https://leetcode.com/problems/split-a-string-in-balanced-strings/
        int balancedStringCount = 0;
        int currentStringValue = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'R') {
                currentStringValue++;
            } else if (s.charAt(i) == 'L') {
                currentStringValue--;
            }
            if (currentStringValue == 0) {
                balancedStringCount++;
            }
        }
        return balancedStringCount;

        // Notes:
        // Else if rather than if if has a performance gain here.
        // I'd wondered if the compiler would be smart enough to
        // optimize, guess not.
    }

    int rangeSumBST = 0;

    public int rangeSumBST(TreeNode root, int low, int high) {
        // https://leetcode.com/problems/range-sum-of-bst/
        if (root == null) {
            return 0;
        }
        if (root.val >= low && root.val <= high) {
            rangeSumBST += root.val;
        }
        if (root.val > low) {
            rangeSumBST(root.left, low, high);
        }
        if (root.val < high) {
            rangeSumBST(root.right, low, high);
        }
        return rangeSumBST;

        // Notes:
        // I need to remember to be careful when handling member variables/fields.
        // Tried to += the calls on left and right, that was not smart.
        // Also, I didn't abuse the fact that this is a binary tree at first.
        // Only need to check left if the value is bigger than the lowest.
        // Only need to check right if the value is bigger than the highest.
    }

    public int getSum(int a, int b) {
        // https://leetcode.com/problems/sum-of-two-integers/
        int carry = 0;
        while (b != 0) {
            carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;

        // Notes:
        // Where there is carry digits can be determined using AND.
        // The "basic addition" is XOR.
        // Carries are left shifted one (jump one place/house over).
        // Then we have to do the addition of our previous value
        // and the shifted carry(s). Repeat until we achieve the result.
    }

    int enclaveTileCount = 0;

    public int numEnclaves(int[][] grid) {
        // https://leetcode.com/problems/number-of-enclaves/
        enclaveTileCount = 0;

        // Remove islands touching external columns
        for (int i = 0; i < grid[0].length; i++) {
            markEnclave(grid, 0, i, -1);
            markEnclave(grid, grid.length - 1, i, -1);
        }

        // Remove islands touching external rows
        for (int i = 0; i < grid.length; i++) {
            markEnclave(grid, i, 0, -1);
            markEnclave(grid, i, grid[0].length - 1, -1);
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                markEnclave(grid, i, j, 2);
            }
        }
        return enclaveTileCount;

        // Notes:
        // Got bitten by running my tests on the wrong method :(
        // Then hit increasingly fringe off by one errors.
        // Also discovered that leaving sout lying around is quite
        // computationally expensive (in LeetCode terms).
    }

    public void markEnclave(int[][] grid, int x, int y, int mark) {
        if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length) {
            return;
        }
        if (grid[x][y] == 1) {
            grid[x][y] = mark;
            if (mark == 2) {
                enclaveTileCount++;
            }
            markEnclave(grid, x + 1, y, mark);
            markEnclave(grid, x, y + 1, mark);
            markEnclave(grid, x - 1, y, mark);
            markEnclave(grid, x, y - 1, mark);
        }
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        // https://leetcode.com/problems/insert-interval/
        List<int[]> allIntervals = new ArrayList<>();
        int maxIndex = intervals.length - 1;
        int i = 0;

        while (i <= maxIndex && intervals[i][1] < newInterval[0]) {
            allIntervals.add(intervals[i]);
            i++;
        }

        while (i <= maxIndex && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(intervals[i][0], newInterval[0]);
            newInterval[1] = Math.max(intervals[i][1], newInterval[1]);
            i++;
        }
        allIntervals.add(newInterval);

        while (i <= maxIndex) {
            allIntervals.add(intervals[i]);
            i++;
        }

        return allIntervals.toArray(new int[allIntervals.size()][2]);

        // Notes:
        // I initially forgot to update newInterval[0]. Then I tried to optimize
        // by doing it only once outside the loop, but missed the 0 length case.
        // Could probably guard against this and save a repeated check.
    }

    Map<Integer, Node> cloneGraphMap = new HashMap<>();

    public Node cloneGraph(Node node) {
        // https://leetcode.com/problems/clone-graph/
        if (node == null) {
            return null;
        }

        if (cloneGraphMap.containsKey(node.val)) {
            return cloneGraphMap.get(node.val);
        }

        Node cloneNode = new Node(node.val, new ArrayList<>());
        cloneGraphMap.put(cloneNode.val, cloneNode);
        for (Node neighbor : node.neighbors) {
            cloneNode.neighbors.add(cloneGraph(cloneNode));
        }
        return cloneNode;

        // Notes:
        // This is a (recursive) depth first search implementation.
        // Can also do this breadth first using a queue.
        // Not sure if a "global" map is the best approach here,
        // could have clone graph call a separate clone method and
        // pass a local map around.
        // Apparently this use of a map is a common pattern in graph questions.
        // The gist of this is, return null if null, return the clone if
        // we've got it already. Otherwise, make a fresh node, store it in the
        // map against its value, then for each of its neighbours, make a clone.
        // Note the Americanized spelling of neighbor.
    }

    public String interpret(String command) {
        // https://leetcode.com/problems/goal-parser-interpretation/
        return command
                .replace("()", "o")
                .replace("(al)", "al");

        // Notes:
        // You can squeeze more juice out of this for loops and character
        // position checks and a string builder. Doesn't seem worthwhile.
    }

    public int[] createTargetArray(int[] nums, int[] index) {
        // https://leetcode.com/problems/create-target-array-in-the-given-order/
        List<Integer> target = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            target.add(index[i], nums[i]);
        }
        return target.stream().mapToInt(x -> x).toArray();

        // Notes:
        // You can add the nums[i], index[i] pair to an array list
        // and then pull from that into the target array for maximum
        // performance at the expense of code complexity.
    }

    public int[] decompressRLElist(int[] nums) {
        // https://leetcode.com/problems/decompress-run-length-encoded-list/
        List<Integer> decompressed = new ArrayList<>();
        for (int i = 0; i < nums.length - 1; i += 2) {
            for (int j = 1; j <= nums[i]; j++) {
                decompressed.add(nums[i + 1]);
            }
        }
        return decompressed.stream().mapToInt(x -> x).toArray();

        // Notes:
        // You can pre-calculate the size in one pass then do a second pass
        // to populate if you wanna make the code zoom zoom.
    }

    public String restoreString(String s, int[] indices) {
        // https://leetcode.com/problems/shuffle-string/
        char[] restoredString = new char[indices.length];
        for (int i = 0; i < indices.length; i++) {
            restoredString[indices[i]] = s.charAt(i);
        }
        return new String(restoredString);

        // Notes:
        // I got tripped up thinking it was position i in the restored
        // string and char at indices i.
    }

    public int differenceOfSum(int[] nums) {
        // https://leetcode.com/problems/difference-between-element-sum-and-digit-sum-of-an-array/
        int elementSum = 0;
        int digitSum = 0;

        for (int num : nums) {
            elementSum += num;
            while (num > 0) {
                digitSum += num % 10;
                num /= 10;
            }
        }
        return Math.abs(elementSum - digitSum);

        // Notes:
        // Could have hardcoded the various magnitudes of division to
        // cheese LeetCode performance metrics if I wanted to sweat.
    }

    public int countDigits(int num) {
        // https://leetcode.com/problems/count-the-digits-that-divide-a-number/description/
        int workingNum = num;
        int divisor = 0;
        int noRemainderCount = 0;
        while (workingNum > 0) {
            divisor = workingNum % 10;
            workingNum /= 10;
            if (num % divisor == 0) {
                noRemainderCount++;
            }
        }
        return noRemainderCount;
    }

    public int findCenter(int[][] edges) {
        // https://leetcode.com/problems/find-center-of-star-graph/
        return (edges[0][0] == edges[1][0]
                || edges[0][0] == edges[1][1])
                ? edges[0][0] : edges[0][1];

        // Notes:
        // This is cheese, because we know it's a star graph then the
        // centre has to be one of the two values. If the first value in the
        // first pair doesn't match either value in the second pair then
        // it must be the second value in the first pair.
    }

    public String toLowerCase(String s) {
        // https://leetcode.com/problems/to-lower-case/
        return s.toLowerCase();

        // Notes:
        // Could check char range and do char math. But really?
    }

    public int countAsterisks(String s) {
        // https://leetcode.com/problems/count-asterisks/
        boolean inBars = false;
        int asterisksCount = 0;
        for (char c : s.toCharArray()) {
            if (c == '|') {
                inBars = !inBars;
            }
            if (c == '*' && !inBars) {
                asterisksCount++;
            }
        }
        return asterisksCount;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        // https://leetcode.com/problems/binary-tree-level-order-traversal/
        List<List<Integer>> traversal = new ArrayList<>();
        Queue<TreeNode> treeQueue = new LinkedList<>();
        treeQueue.add(root);

        while (!treeQueue.isEmpty()) {
            int nodeCountInLevel = treeQueue.size();
            List<Integer> nodesInLevel = new ArrayList<>();
            for (int i = 1; i <= nodeCountInLevel; i++) {
                TreeNode node = treeQueue.poll();
                if (node != null) {
                    nodesInLevel.add(node.val);
                    treeQueue.add(node.left);
                    treeQueue.add(node.right);
                }
            }
            if (!nodesInLevel.isEmpty()) {
                traversal.add(nodesInLevel);
            }
        }
        return traversal;

        // Notes:
        // At first I had an unneeded null guard and started the loop at zero
        // rather than one.
        // You can actually do this recursively without a queue.
        // The gist of this is:
        // Initialize the queue with root
        // While the queue is not empty
        // Make a list for the nodes in this level, iterate for each enqueued node
        // poll() (fifo get) the node, if it's not null, store its value and enqueue children
        // if the list isn't empty, add it to the returned list
        // return the list
    }

    public int deepestLeavesSum(TreeNode root) {
        // https://leetcode.com/problems/deepest-leaves-sum/
        int sum = 0;
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        while (!nodeQueue.isEmpty()) {
            sum = 0;
            int treeWidth = nodeQueue.size();
            for (int i = 1; i <= treeWidth; i++) {
                TreeNode node = nodeQueue.poll();
                if (node != null) {
                    sum += node.val;
                    if (node.left != null) {
                        nodeQueue.add(node.left);
                    }
                    if (node.right != null) {
                        nodeQueue.add(node.right);
                    }
                }
            }
        }
        return sum;

        // Notes:
        // Can avoid double diving by only queuing when not null and
        // simply falling out with the current sum.
        // Could also DFS this by passing the level and resetting sum
        // when you see a new level and only adding if you're
        // at the right level.
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        // https://leetcode.com/problems/same-tree/
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val
                && isSameTree(p.left, q.left)
                && isSameTree(p.right, q.right);

        // Notes:
        // I got caught up trying to make this a one-liner.
    }

    public boolean isSymmetric(TreeNode root) {
        // https://leetcode.com/problems/symmetric-tree/
        return treeSymmetry(root.left, root.right);
    }

    public boolean treeSymmetry(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val
                && treeSymmetry(p.left, q.right)
                && treeSymmetry(p.right, q.left);
    }

    public int minDepth(TreeNode root) {
        // https://leetcode.com/problems/minimum-depth-of-binary-tree/
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        if (root.left == null) {
            return 1 + minDepth(root.right);
        }
        if (root.right == null) {
            return 1 + minDepth(root.left);
        }
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));

        // Notes:
        // I had the right idea at the start but got it slightly wrong.
        // Started tacking things on making it more wrong, rather than
        // dialling back and clearing the slate.
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        // https://leetcode.com/problems/binary-tree-level-order-traversal-ii/description/
        List<List<Integer>> traversalList = new ArrayList<>();
        Stack<List<Integer>> traversalStack = new Stack<>();
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        while (!nodeQueue.isEmpty()) {
            int layerWidth = nodeQueue.size();
            List<Integer> layerList = new ArrayList<>();
            for (int i = 1; i <= layerWidth; i++) {
                TreeNode node = nodeQueue.poll();
                if (node != null) {
                    layerList.add(node.val);
                    if (node.left != null) {
                        nodeQueue.add(node.left);
                    }
                    if (node.right != null) {
                        nodeQueue.add(node.right);
                    }
                }
            }
            if (layerList.size() > 0) {
                traversalStack.push(layerList);
            }
        }
        while (!traversalStack.empty()) {
            traversalList.add(traversalStack.pop());
        }
        return traversalList;

        // Notes:
        // You can just do a regular BFS layer traversal and reverse the list.
    }

    public int[][] updateMatrix(int[][] mat) {
        // https://leetcode.com/problems/01-matrix/description/
        Queue<int[]> coordinates = new LinkedList<>();
        for (int x = 0; x < mat.length; x++) {
            for (int y = 0; y < mat[0].length; y++) {
                if (mat[x][y] == 0) {
                    coordinates.offer(new int[]{x, y});
                } else {
                    mat[x][y] = -1;
                }
            }
        }
        int[][] cardinalDirections = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int distanceToZero = 0;
        while (!coordinates.isEmpty()) {
            int queueLength = coordinates.size();
            distanceToZero++;
            for (int i = 1; i <= queueLength; i++) {
                int[] position = coordinates.poll();
                for (int[] direction : cardinalDirections) {
                    assert position != null;
                    int x = position[0] + direction[0];
                    int y = position[1] + direction[1];
                    if (x >= 0 && y >= 0 && x < mat.length && y < mat[0].length) {
                        if (mat[x][y] == -1) {
                            mat[x][y] = distanceToZero;
                            coordinates.offer(new int[]{x, y});
                        }
                    }
                }
            }
        }
        return mat;

        // Notes:
        // This is a BFS implementation, this can also be handled dynamically.
        // We enqueue every 0 co-ordinate, and set all non-zeroes to -1, so they
        // can be set to their correct value later.
        // Then while there is still something in the queue, we check each
        // direction from that position. If it's in bounds and not yet got a
        // value, then set its distance value and enqueue it.
        // Or, less abstractly, for each zero we know that its adjacent positions
        // are one away from a zero (unless they are zero). Then for each one,
        // we know its adjacent positions are two (unless they are already set).
        // And so on.
    }

    public int[][] kClosest(int[][] points, int k) {
        // https://leetcode.com/problems/k-closest-points-to-origin/
        Map<Double, List<int[]>> distanceMap = new TreeMap<>();

        for (int[] point : points) {
            double distanceFromOrigin = euclideanDistanceFromOrigin(point);
            if (distanceMap.containsKey(distanceFromOrigin)) {
                distanceMap.get(distanceFromOrigin).add(point);
            } else {
                distanceMap.put(distanceFromOrigin, new ArrayList<>(Collections.singletonList(point)));
            }
        }

        int[][] kClosest = new int[k][2];
        int i = 0;
        for (Map.Entry<Double, List<int[]>> entry : distanceMap.entrySet()) {
            for (int[] point : entry.getValue()) {
                kClosest[i] = point;
                i++;
                if (i == k) {
                    return kClosest;
                }
            }
        }

        return kClosest;

        // Notes:
        // This can also be done with a priority queue with a custom comparator.
        // This solution leverages a tree map (red black tree) with natural
        // ordering (because the shortest distance is what we're interested in).
        // We keep a list of all points at each distance in the map.
        // then for each entry, we grab the list and add points to the
        // array we're going to return until it's full, or we run out of points.
        // I need further practice with both tree maps and priority queues,
        // I knew immediately that's the type of thing I wanted to use, but
        // needed reference to get it right.
    }

    public double euclideanDistanceFromOrigin(int[] point) {
        // √(x1 - x2)2 + (y1 - y2)2
        return Math.sqrt((Math.pow(point[0], 2) + Math.pow(point[1], 2)));
    }

    public String removeStarsRegex(String s) {
        // https://leetcode.com/problems/removing-stars-from-a-string/
        while (s.contains("*")) {
            s = s.replaceFirst("[a-z]?\\*", "");
        }
        return s;

        // Notes:
        // This solution gets a TLE.
        // You can use a stack to do this, but there's an even better way.
    }

    public String removeStars(String s) {
        // https://leetcode.com/problems/removing-stars-from-a-string/
        StringBuilder starFreeString = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == '*') {
                starFreeString.setLength(starFreeString.length() - 1);
            } else {
                starFreeString.append(c);
            }
        }
        return starFreeString.toString();
    }

    public int lengthOfLongestSubstring(String s) {
        // https://leetcode.com/problems/longest-substring-without-repeating-characters/
        Set<Character> charSet = new HashSet<>();
        int longestSeen = 0;
        int minor = 0;

        for (int major = 0; major < s.length(); major++) {
            if (!charSet.contains(s.charAt(major))) {
                charSet.add(s.charAt(major));
                longestSeen = Math.max(longestSeen, major - minor + 1);
            } else {
                while (s.charAt(minor) != s.charAt(major)) {
                    charSet.remove(s.charAt(minor));
                    minor++;
                }
                charSet.remove(s.charAt(minor));
                minor++;
                charSet.add(s.charAt(major));
            }
        }
        return longestSeen;

        // Notes:
        // Did this in a single pass at first, not thinking.
        // Needs sliding window.
    }

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        // https://leetcode.com/problems/validate-stack-sequences/solutions/?languageTags=java
        Stack<Integer> validationStack = new Stack<>();

        int i = 0;
        for (int pushedValue : pushed) {
            validationStack.push(pushedValue);
            while (!validationStack.isEmpty() && validationStack.peek() == popped[i]) {
                validationStack.pop();
                i++;
            }
        }
        return validationStack.isEmpty();

        // Notes:
        // We attempt to do a "play through" to see if it's possible.
        // Push the pushed elements one at a time.
        // While there's stuff in the stack and the top element is the next popped
        // element, pop it and move along.
        // If the stack is empty, then we've confirmed the stack sequence is valid.
    }
}
