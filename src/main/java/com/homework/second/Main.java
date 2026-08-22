package com.homework.second;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // Это не промежуточная переменная, а требуемая коллекция студентов.
    private static List<Student> students;

    public static void main(String[] args) throws IOException {

        Path file = Path.of(System.getProperty("user.dir") + "\\src\\main\\java\\com\\homework\\second\\students.txt");

        // 1. Создаём текстовый файл со студентами и книгами.
        Files.writeString(file, """
                Иван Иванов;Мастер и Маргарита;Булгаков;1967;480
                Иван Иванов;1984;Оруэлл;1949;328
                Иван Иванов;Атлант расправил плечи;Рэнд;1957;1168
                Иван Иванов;Гарри Поттер и философский камень;Роулинг;1997;432
                Иван Иванов;Гарри Поттер и тайная комната;Роулинг;1998;352
                Пётр Петров;Властелин колец;Толкин;1954;1178
                Пётр Петров;Властелин колец;Толкин;1954;1178
                Пётр Петров;Код да Винчи;Браун;2003;544
                Пётр Петров;Шантарам;Робертс;2003;944
                Пётр Петров;Метро 2033;Глуховский;2005;384
                Пётр Петров;Sapiens;Харари;2011;512
                Анна Смирнова;Три товарища;Ремарк;1936;480
                Анна Смирнова;Парфюмер;Зюскинд;1985;320
                Анна Смирнова;Тень горы;Робертс;2015;800
                Анна Смирнова;451 градус по Фаренгейту;Брэдбери;1953;256
                Анна Смирнова;Марсианин;Вейр;2011;384
                """);

        // 2. Заполнение списка студентов
        students = Files.readAllLines(file)
                .stream()
                .collect(Collectors.groupingBy(
                        line -> line.split(";")[0],
                        LinkedHashMap::new,
                        Collectors.mapping(
                                line -> new Book(
                                        line.split(";")[1],
                                        line.split(";")[2],
                                        Integer.parseInt(line.split(";")[3]),
                                        Integer.parseInt(line.split(";")[4])
                                ),
                                Collectors.toList()
                        )
                ))
                .entrySet()
                .stream()
                .map(entry -> new Student(entry.getKey(), entry.getValue()))
                .peek(System.out::println)
                .collect(Collectors.toList());

        // 3. Операции над списком в одном потоке
        students.stream()
                .flatMap(student -> student.getBooks().stream())
                .sorted(Comparator.comparingInt(Book::getPages))
                .distinct()
                .peek(System.out::println)
                .filter(book -> book.getYear() > 2000)
                .limit(3)
                .map(Book::getYear)
                .findFirst()
                .ifPresentOrElse(
                        year -> System.out.println(
                                "Год выпуска найденной книги: " + year
                        ),
                        () -> System.out.println(
                                "Книга, выпущенная после 2000 года, отсутствует"
                        )
                );
    }
}