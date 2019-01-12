package parallel;

import static java.util.stream.Collectors.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class ParallelStreams
{
   public static void main(String[] args) throws IOException
   {
      String contents = new String(Files.readAllBytes(
            Paths.get("../gutenberg/alice30.txt")), StandardCharsets.UTF_8);
      List<String> wordList = Arrays.asList(contents.split("\\PL+"));

      // Very bad code ahead
      int[] shortWords = new int[10];
      wordList.parallelStream().forEach(s -> 
         {
            if (s.length() < 10) shortWords[s.length()]++;
         });
      System.out.println(Arrays.toString(shortWords));//[1, 1712, 4609, 7056, 5721, 3444, 2043, 1707, 800, 532]

      // Try again--the result will likely be different (and also wrong)
      Arrays.fill(shortWords, 0);
      wordList.parallelStream().forEach(s -> 
         {
            if (s.length() < 10) shortWords[s.length()]++;
         });
      System.out.println(Arrays.toString(shortWords));//[1, 1691, 4363, 6572, 5472, 3324, 1994, 1668, 787, 534]

      // Remedy: Group and count
      Map<Integer, Long> shortWordCounts = wordList.parallelStream()
         .filter(s -> s.length() < 10)
         .collect(groupingBy(String::length, counting()));

      System.out.println(shortWordCounts);//{0=1, 1=1746, 2=4753, 3=7423, 4=6033, 5=3543, 6=2071, 7=1720, 8=805, 9=536}

      // Downstream order not deterministic
      Map<Integer, List<String>> result = wordList.parallelStream().collect(
         Collectors.groupingByConcurrent(String::length));

      System.out.println(result.get(14));//[contemptuously, Multiplication, disappointment, affectionately, electronically, electronically, electronically, co
//      ntemptuously]

      result = wordList.parallelStream().collect(
         Collectors.groupingByConcurrent(String::length));

      System.out.println(result.get(14));//[affectionately, Multiplication, contemptuously, disappointment, electronically, electronically, electronically, co
//      ntemptuously]

      Map<Integer, Long> wordCounts = wordList.parallelStream().collect(
         groupingByConcurrent(String::length, counting()));

      System.out.println(wordCounts);//{0=1, 1=1746, 2=4753, 3=7423, 4=6033, 5=3543, 6=2071, 7=1720, 8=805, 9=536, 10=234, 11=139, 12=44, 13=18, 14=8, 15=
//      1}
   }
}
