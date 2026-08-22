package com.homework.second;

import java.util.List;

public class Student {
    private final String name;
    private final List<Book> books;
    public Student(String name, List<Book> books) {
        if (books.size() < 5) {
            throw new IllegalArgumentException(
                    "У каждого студента должно быть минимум 5 книг"
            );
        }

        this.name = name;
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}