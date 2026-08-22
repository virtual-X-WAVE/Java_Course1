package com.homework.second;

import java.util.Objects;

public class Book {
    private final String title;
    private final String author;
    private final int year;
    private final int pages;

    public Book(String title, String author, int year, int pages) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }

    public int getYear() {
        return year;
    }

    public int getPages() {
        return pages;
    }

    /*
     * distinct() использует equals() и hashCode().
     * Поэтому две книги с одинаковыми данными считаются одной книгой.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;

        return year == book.year
                && pages == book.pages
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year, pages);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", pages=" + pages +
                '}';
    }
}
