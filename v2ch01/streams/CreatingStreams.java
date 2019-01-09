package streams;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreatingStreams
{
   public static <T> void show(String title, Stream<T> stream)
   {
      final int SIZE = 10;
      List<T> firstElements = stream
            .limit(SIZE + 1) //限制集合的数据为前11个 注意：第一个为""字符串
            .collect(Collectors.toList());
      System.out.print(title + ": ");
      for (int i = 0; i < firstElements.size(); i++)
      {
         if (i > 0) System.out.print(", ");
         if (i < SIZE) System.out.print(firstElements.get(i));
         else System.out.print("...");
      }
      System.out.println();
   }

   public static void main(String[] args) throws IOException
   {
      Path path = Paths.get("../gutenberg/alice30.txt");
      String contents = new String(Files.readAllBytes(path),
            StandardCharsets.UTF_8);

      Stream<String> words = Stream.of(contents.split("\\PL+"));
      show("words", words);//words: , This, is, the, Project, Gutenberg, Etext, of, Alice, in, ...

      Stream<String> song = Stream.of("gently", "down", "the", "stream");
      show("song", song);//song: gently, down, the, stream

      Stream<String> silence = Stream.empty();
      show("silence", silence);//silence:

      //Stream.generate() 参数为不接受任何引元的函数  是个创建无限流的静态方法 产生的值时反复调用这个函数产生的
      Stream<String> echos = Stream.generate(() -> "Echo");
      show("echos", echos);//echos: Echo, Echo, Echo, Echo, Echo, Echo, Echo, Echo, Echo, Echo, ...

      //使用方法引用的方法创建无限流  注意格式：相当于代用类的静态方法（不家小括号）
      Stream<Double> randoms = Stream.generate(Math::random);
      show("randoms", randoms);//randoms: 0.9829096425597169, 0.3465237368630335, 0.2768086973142173, 0.4895816559525238, 0.5858459841304908, 0.7702
     // 498534090478, 0.29752965544179344, 0.8446365048101186, 0.3253218862462002, 0.8521232846605891, ...

      //也是创建无限流的方法 包含种子  在种子上调用函数产生的值等
      Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE,
            n -> n.add(BigInteger.ONE));
      show("integers", integers);//integers: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ...

      //
      Stream<String> wordsAnotherWay = Pattern.compile("\\PL+").splitAsStream(
            contents);
      show("wordsAnotherWay", wordsAnotherWay);//输出结果和第一个一样

      //产生一个流，它的元素是文件中的行 且制定了字符集
      try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8))
      {
         show("lines", lines);
      }
   }
}
