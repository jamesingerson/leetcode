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

    @Test
    void kItemsWithMaximumSum() {
        var example1 = main.kItemsWithMaximumSum(3, 2, 0, 2);
        assertThat(example1).isEqualTo(2);

        var example2 = main.kItemsWithMaximumSum(3, 2, 0, 4);
        assertThat(example2).isEqualTo(3);

        var test1 = main.kItemsWithMaximumSum(0, 2, -5, 4);
        assertThat(test1).isEqualTo(-2);

        var fail1 = main.kItemsWithMaximumSum(6, 6, 6, 13);
        assertThat(fail1).isEqualTo(5);
    }

    @Test
    void evenOddBit() {
        var example1 = main.evenOddBit(17);
        assertThat(example1).isEqualTo(new int[]{2, 0});

        var example2 = main.evenOddBit(2);
        assertThat(example2).isEqualTo(new int[]{0, 1});
    }

    @Test
    void passThePillow() {
        var example1 = main.passThePillow(4, 5);
        assertThat(example1).isEqualTo(2);

        var example2 = main.passThePillow(3, 2);
        assertThat(example2).isEqualTo(3);

        var test1 = main.passThePillow(4, 15);
        assertThat(test1).isEqualTo(4);

        var fail1 = main.passThePillow(8, 9);
        assertThat(fail1).isEqualTo(6);
    }

    @Test
    void splitNum() {
        var example1 = main.splitNum(4325);
        assertThat(example1).isEqualTo(59);

        var example2 = main.splitNum(687);
        assertThat(example2).isEqualTo(75);
    }

    @Test
    void pickGifts() {
        var example1 = main.pickGifts(new int[]{25, 64, 9, 4, 100}, 4);
        assertThat(example1).isEqualTo(29);

        var example2 = main.pickGifts(new int[]{1, 1, 1, 1}, 4);
        assertThat(example2).isEqualTo(4);

        var fail1 = main.pickGifts(new int[]{411042986, 697316006, 569259488, 665293106, 728558122, 395308016, 962051539, 449602622, 225273018, 421053664, 772795795, 42557563, 640312042, 791181812, 239411012, 610918759, 7894884, 951279693, 478806887, 792321489, 11566125, 445499925, 164783184, 832628691, 216001498, 68086337, 621597421, 683035143, 138851304, 999858983, 978638960, 957360800, 845854378, 339740721, 805732223, 301257941, 948144266, 316601503, 834324760, 952034242, 485765680, 367489151, 562273287, 443830581, 527380862, 354003764, 868621029, 285858008, 554465828, 934261207, 326805671, 476383643, 849715786, 573608875, 356570192, 430933288, 720102531, 360069216, 998389465, 433353834, 960876305, 811449015, 300988814, 997136000, 748106345, 15324739, 229841322, 902721256, 976259160, 766465590, 501357697, 808233891, 320808719, 432897238, 460174119, 793856268, 794308304, 888513797, 919561695, 997018294, 872517844, 732758397, 139382267, 987197536, 192080964, 512521917, 204398822, 183408531, 670587276, 993461405, 207192986, 83676396, 933501217, 290641393, 539672160, 902963870, 543574898, 175895424, 630556454, 917789451, 198596337, 710134838, 520718131, 130351018, 516751550, 266412928, 466028542, 197238216, 124798889, 905882992, 58409955, 553925368, 41052728, 754849139, 791608447, 96498420, 847746924, 801453189, 775979365, 101433510, 948173651, 634882737, 147469850, 157994270, 604456360, 225338800, 693732347, 385582201, 393211915, 107235426, 102866862, 404442716, 524299261, 134876886, 360405003, 615748736, 956635493, 560785416, 218147103, 652012553, 240280795, 819791421, 641230611, 612132184, 476730617, 647516851, 308142039, 102486973, 150690655, 449220082, 901741894, 102594315, 804298689, 970625448, 202387635, 770166478, 139857289, 191053305, 992821372, 738572151, 285304203, 295950554, 850907897, 726252282, 348602857, 181617317, 478453262, 762885760, 360811868, 586520050, 107418066, 810231588, 97005102, 425532800, 12367528, 66900692, 202519795, 368788277, 467295402, 949869622, 266533684, 861493897, 355296238, 994480595, 459013013, 632639412, 133044329, 352413436, 70343030, 517236863, 652861971, 529559148, 318834519, 263079141, 411904232, 935516207, 767239406, 63426685, 370300413, 356019633, 181482985, 96414165, 601664676, 296441949, 491988465, 6674257, 850240442, 871498452, 666111648, 671651141, 171469356, 919558542, 151653696, 788784252, 598942504, 609197094, 362651934, 730271250, 798858341, 487662381, 906902772, 934112890, 744407596, 72592439, 849616589, 418288497, 879569216, 291826753, 502745282, 304119041, 31290767, 434303562, 921982054, 25527606, 868664232, 971790103, 489230345, 870879486, 119499912, 141546125, 866303287, 878976557, 322745805, 353861446, 319787966, 651560269, 167019426, 525272089, 859549346, 477477637, 7427659, 653921116, 348581572, 974282539, 166669447, 444640945, 867049851, 28627058, 320740040, 914163072, 191557374, 620217111, 866699502, 871487886, 168225942, 84026741, 452237455, 928364217, 812696278, 549676604, 927295304, 486213535, 934007957, 875127560, 991747269, 573593181, 688927535, 836051208, 148889527, 974243495, 451141055, 881966921, 364621818, 141976346, 742897361, 603021279, 386094075, 634451699, 180948759, 873438053, 88509922, 99771148, 260170730, 120645424, 217135839, 513643461, 971010503, 770526142, 907624281, 702552414, 205602290, 29752018, 851929924, 920273349, 403963200, 354132106, 283801239, 381227158, 633412448, 271311898, 475587437, 975601692, 557477248, 804808599, 41207245, 334503344, 937968148, 478646630, 465456593, 979273300, 908101061, 239262911, 940678691, 812776515, 791191677, 799904975, 508692780, 15944845, 560212039, 880241046, 640034488, 783520451, 424680812, 766159285, 859039236, 917783030, 871413702, 361793594, 230180851, 604216769, 374617264, 315288336, 859870129, 174739859, 190118992, 779900639, 769355033, 301815420, 214430557, 868533122, 127535880, 378491820, 790297619, 405855516, 165730573, 324055125, 871416614, 274519616, 763142457, 668802502, 888748004, 180060246, 928876224, 435649804, 29735032, 716244055, 973244582, 884623533, 83382602, 883979419, 199512766, 994944436, 419113063, 442077405, 84099164, 82540477, 726492160, 611438472, 268399816, 575195446, 72366636, 231854163, 347907916, 797139540, 486082975, 599032788, 769091075, 652606018, 852704099, 105368721, 829719903, 182048441, 870274119, 962235423, 171365385, 822409839, 650252876, 631725581, 653176131, 51915363, 938485327, 470192660, 833392364, 279356386, 229570434, 576193154, 65367587, 529999468, 78669537, 18068075, 696647571, 52372953, 638044540, 201977712, 183972533, 528894777, 810322059, 707105593, 213971717, 559401462, 312354436, 617277034, 795249812, 609787046, 94106276, 161430129, 400185044, 908279151, 585583350, 867265461, 848956981, 882019, 948880258, 665977535, 359065646, 703912241, 332647390, 960303115, 586855316, 850391910, 242862987, 623597894, 326194681, 13078289, 616248648, 443035222, 380149333, 76113162, 352961827, 300601771, 887591621, 823055398, 371485122, 881226207, 107854719, 322544421, 605683146, 160084817, 378073277, 41181728, 776600450, 15567729, 899040778, 689247697, 641000130, 976144331, 878662449, 430009684, 9099429, 463825298, 250674312, 881396273, 462408891, 612326918, 499316266, 315573963, 872672283, 4532574, 188446732, 740874206, 615138293, 487808202, 577594187, 72528868, 695666803, 833728952, 14701387, 718160936, 46955296, 856421274, 97595319, 136755829, 422403964, 411957448, 641830807, 188179649, 788133810, 236096053, 698907105, 588407611, 115440136, 32011245, 929674397, 43907497, 423599729, 435271853, 876558411, 303851021, 183781062, 904357040, 408335123, 28239876, 719874350, 342359383, 676551709, 450935265, 557047058, 215812496, 991212477, 551781058, 871404599, 382544267, 639791865, 264206417, 727637536, 34625114, 445816797, 528760496, 550724151, 676734411, 51210506, 899160067, 889280489, 214724708, 363240264, 491477502, 781617792, 197612253, 609786094, 211742038, 228020156, 38310889, 214780008, 127970607, 315317381, 813198777, 347401798, 354282481, 786946268, 327223470, 852873245, 154494142, 193808643, 618885231, 773704523, 445694962, 296040523, 59340167, 410799255, 856521445, 867163276, 118660678, 812578479, 610798196, 851114616, 920707519, 985560979, 534391149, 174275920, 850807350, 408014465, 843304903, 238343030, 440261622, 817897261, 415698115, 138230444, 601397431, 678427635, 475197016, 948814172, 668875016, 739529092, 873781684, 389387538, 986735913, 49398891, 690766496, 662321022, 563508177, 244511403, 231740021, 550844436, 429042319, 522091360, 240782770, 613929459, 782593342, 84010984, 580133049, 119227278, 164588358, 944585194, 177404234, 325700527, 30251353, 342672830, 122180798, 140878553, 664826945, 684663665, 567221091, 664721891, 847649906, 192953136, 586787758, 446795782, 275321411, 14173780, 822005201, 846750288, 393453854, 147868793, 47769104, 553058369, 232106965, 768048280, 477544930, 716251629, 658327875, 289968927, 836994003, 447489638, 924979759, 831386055, 64393174, 806872606, 267549779, 632804099, 285891578, 207446592, 362094292, 715264602, 112773344, 745585494, 901250825, 287864117, 528363005, 398468678, 892443651, 315495717, 62419851, 284394209, 748523613, 115700555, 385658281, 305660144, 209122422, 984258192, 365656256, 249753092, 258966265, 892043565, 213952883, 201880108, 735567606, 406360625, 130651969, 574379368, 269978892, 713822399, 181339543, 371879414, 970310784, 419482553, 28845115, 941990857, 572926369, 718384124, 46956823, 574079170, 465705946, 735426031, 117596781}, 630);
        assertThat(fail1).isEqualTo(2192086545L);

    }

    @Test
    void longestCommonPrefix() {
        var example1 = main.longestCommonPrefix(new String[]{"flower", "flow", "flight"});
        assertThat(example1).isEqualTo("fl");

        var example2 = main.longestCommonPrefix(new String[]{"dog", "racecar", "car"});
        assertThat(example2).isEqualTo("");

        var fail1 = main.longestCommonPrefix(new String[]{""});
        assertThat(fail1).isEqualTo("");

        var fail2 = main.longestCommonPrefix(new String[]{"a"});
        assertThat(fail2).isEqualTo("a");

        var fail3 = main.longestCommonPrefix(new String[]{"ab", "a"});
        assertThat(fail3).isEqualTo("a");

        var fail4 = main.longestCommonPrefix(new String[]{"reflower", "flow", "flight"});
        assertThat(fail4).isEqualTo("");
    }

    @Test
    void search() {
        var example1 = main.search(new int[]{-1, 0, 3, 5, 9, 12}, 9);
        assertThat(example1).isEqualTo(4);

        var example2 = main.search(new int[]{-1, 0, 3, 5, 9, 12}, 2);
        assertThat(example2).isEqualTo(-1);
    }

    @Test
    void removeDuplicates() {
        var example1 = main.removeDuplicates(new int[]{1, 1, 2});
        assertThat(example1).isEqualTo(2);

        var example2 = main.removeDuplicates(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4});
        assertThat(example2).isEqualTo(5);

        var test1 = main.removeDuplicates(new int[]{4});
        assertThat(test1).isEqualTo(1);
    }

    @Test
    void removeElement() {
        var example1 = main.removeElement(new int[]{3, 2, 2, 3}, 3);
        assertThat(example1).isEqualTo(2);

        var example2 = main.removeElement(new int[]{0, 1, 2, 2, 3, 0, 4, 2}, 2);
        assertThat(example2).isEqualTo(5);

        var test1 = main.removeElement(new int[]{}, 2);
        assertThat(test1).isEqualTo(0);

        var test2 = main.removeElement(new int[]{1, 1}, 2);
        assertThat(test2).isEqualTo(2);

        var test3 = main.removeElement(new int[]{2, 2}, 2);
        assertThat(test3).isEqualTo(0);

        var fail1 = main.removeElement(new int[]{1}, 1);
        assertThat(fail1).isEqualTo(0);

        var fail2 = main.removeElement(new int[]{4, 5}, 5);
        assertThat(fail2).isEqualTo(1);
    }

    @Test
    void lengthOfLastWord() {
        var example1 = main.lengthOfLastWord("Hello World");
        assertThat(example1).isEqualTo(5);

        var example2 = main.lengthOfLastWord("   fly me   to   the moon  ");
        assertThat(example2).isEqualTo(4);

        var test1 = main.lengthOfLastWord("luffy is still joyboy");
        assertThat(test1).isEqualTo(6);
    }

    @Test
    void successfulPairs() {
        var example1 = main.successfulPairs(new int[]{5, 1, 3}, new int[]{1, 2, 3, 4, 5}, 7);
        assertThat(example1).isEqualTo(new int[]{4, 0, 3});

        var example2 = main.successfulPairs(new int[]{3, 1, 2}, new int[]{8, 5, 8}, 16);
        assertThat(example2).isEqualTo(new int[]{2, 0, 2});

    }

    @Test
    void mySqrt() {
        var example1 = main.mySqrt(4);
        assertThat(example1).isEqualTo(2);

        var example2 = main.mySqrt(8);
        assertThat(example2).isEqualTo(2);

        var test1 = main.mySqrt(1);
        assertThat(test1).isEqualTo(1);

        var test2 = main.mySqrt(0);
        assertThat(test2).isEqualTo(0);

        var test3 = main.mySqrt(100000);
        assertThat(test3).isEqualTo(316);

        var test4 = main.mySqrt(2140000000);
        assertThat(test4).isEqualTo(46260);

        var fail1 = main.mySqrt(2);
        assertThat(fail1).isEqualTo(1);
    }

    @Test
    void maxProfit() {
        var example1 = main.maxProfit(new int[]{7, 1, 5, 3, 6, 4});
        assertThat(example1).isEqualTo(5);

        var example2 = main.maxProfit(new int[]{7, 6, 4, 3, 1});
        assertThat(example2).isEqualTo(0);

        var test1 = main.maxProfit(new int[]{});
        assertThat(test1).isEqualTo(0);

        var test2 = main.maxProfit(new int[]{5});
        assertThat(test2).isEqualTo(0);

        var test3 = main.maxProfit(new int[]{5, 6});
        assertThat(test3).isEqualTo(1);

        var test4 = main.maxProfit(new int[]{6, 5});
        assertThat(test4).isEqualTo(0);

        var fail1 = main.maxProfit(new int[]{2, 4, 1});
        assertThat(fail1).isEqualTo(2);
    }

    @Test
    void isPalindromeString() {
        var example1 = main.isPalindrome("A man, a plan, a canal: Panama");
        assertThat(example1).isTrue();

        var example2 = main.isPalindrome("race a car");
        assertThat(example2).isFalse();

        var example3 = main.isPalindrome(" ");
        assertThat(example3).isTrue();
    }

    @Test
    void isAnagram() {
        var example1 = main.isAnagram("anagram", "nagaram");
        assertThat(example1).isTrue();

        var example2 = main.isAnagram("rat", "car");
        assertThat(example2).isFalse();

        var test1 = main.isAnagram("r", "c");
        assertThat(test1).isFalse();

        var test2 = main.isAnagram("r", "r");
        assertThat(test2).isTrue();
    }

    @Test
    void numRescueBoats() {
        var example1 = main.numRescueBoats(new int[]{1,2}, 3);
        assertThat(example1).isEqualTo(1);

        var example2 = main.numRescueBoats(new int[]{3,2,2,1}, 3);
        assertThat(example2).isEqualTo(3);

        var example3 = main.numRescueBoats(new int[]{3,5,3,4}, 5);
        assertThat(example3).isEqualTo(4);
    }

    @Test
    void canConstruct() {
        var example1 = main.canConstruct("a", "b");
        assertThat(example1).isFalse();

        var example2 = main.canConstruct("aa", "b");
        assertThat(example2).isFalse();

        var test1 = main.canConstruct("aa", "aab");
        assertThat(test1).isTrue();
    }
}