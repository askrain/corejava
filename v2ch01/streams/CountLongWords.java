package streams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CountLongWords
{
   public static void main(String[] args) throws IOException
   {
      String contents = new String(Files.readAllBytes(
            Paths.get("../gutenberg/alice30.txt")), StandardCharsets.UTF_8);
      List<String> words = Arrays.asList(contents.split("\\PL+"));//以非字母间隔生成字符串数组
      System.out.println("words's length="+words.size());//29075
      long count = 0;
      for (String w : words)
      {
         if (w.length() > 12) count++;
      }
      System.out.println(count);//以下的打印都是27

      //集合转换为顺序流  filter起过滤作用,产生一个流，其中包含所有满足条件的所有元素  count()产生当前流中的元素数量，是个终结操作
      count = words.stream().filter(w -> w.length() > 12).count();
      System.out.println(count);
      //集合转换为并行流
      count = words.parallelStream().filter(w -> w.length() > 12).count();
      System.out.println(count);
   }
}