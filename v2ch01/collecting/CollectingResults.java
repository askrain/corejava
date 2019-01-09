package collecting;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class CollectingResults
{
   public static Stream<String> noVowels() throws IOException
   {
      String contents = new String(Files.readAllBytes(
            Paths.get("../gutenberg/alice30.txt")),
            StandardCharsets.UTF_8);
      List<String> wordList = Arrays.asList(contents.split("\\PL+"));
      Stream<String> words = wordList.stream();
      return words.map(s -> s.replaceAll("[aeiouAEIOU]", ""));
   }

   public static <T> void show(String label, Set<T> set)
   {
      System.out.print(label + ": " + set.getClass().getName());
      System.out.println("["
            + set.stream().limit(10).map(Object::toString)
                  .collect(Collectors.joining(", ")) + "]");
   }

   public static void main(String[] args) throws IOException
   {
      Iterator<Integer> iter = Stream.iterate(0, n -> n + 1).limit(10)
            .iterator();
      while (iter.hasNext())
         System.out.println(iter.next());//0~9

      Object[] numbers = Stream.iterate(0, n -> n + 1).limit(10).toArray();
      System.out.println("Object array:" + numbers); // Note it's an Object[] array
      //结果为Object array:[Ljava.lang.Object;@7cca494b
      try
      {
         Integer number = (Integer) numbers[0]; // OK
         System.out.println("number: " + number);//number: 0
         System.out.println("The following statement throws an exception:");
         Integer[] numbers2 = (Integer[]) numbers; // Throws exception
      }
      catch (ClassCastException ex)
      {
         System.out.println(ex);//java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.Integer;
      }

      Integer[] numbers3 = Stream.iterate(0, n -> n + 1).limit(10)
         .toArray(Integer[]::new);
      System.out.println("Integer array: " + numbers3); // Note it's an Integer[] array
      //Integer array: [Ljava.lang.Integer;@58372a00
      Set<String> noVowelSet = noVowels()
            .collect(Collectors.toSet());
      show("noVowelSet", noVowelSet);//noVowelSet: java.util.HashSet[, Pppr, rsrc, tdy, srprsd, kss, Txts, gssd, WLD, clmly]

      TreeSet<String> noVowelTreeSet = noVowels().collect(
            Collectors.toCollection(TreeSet::new));
      show("noVowelTreeSet", noVowelTreeSet);//noVowelTreeSet: java.util.TreeSet[, B, BC, BCDC, BFR, BG, BRCH, BST, BSY, BT]

      String result = noVowels().limit(10).collect(
            Collectors.joining());
      System.out.println("Joining: " + result);//Joining: ThssthPrjctGtnbrgtxtflcn
      result = noVowels().limit(10)
            .collect(Collectors.joining(", "));
      System.out.println("Joining with commas: " + result);//Joining with commas: , Ths, s, th, Prjct, Gtnbrg, txt, f, lc, n

      IntSummaryStatistics summary = noVowels().collect(
            Collectors.summarizingInt(String::length));
      double averageWordLength = summary.getAverage();
      double maxWordLength = summary.getMax();
      System.out.println("Average word length: " + averageWordLength);//Average word length: 2.4493551160791056
      System.out.println("Max word length: " + maxWordLength);//Max word length: 10.0
      System.out.println("forEach:");
      noVowels().limit(10).forEach(System.out::println);
      /*Ths
s
th
Prjct
Gtnbrg
txt
f
lc
n
*/
   }
}
