package com.scaler.EcomProductService.demo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String bookName;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Book(String bookName, Author author) {
        this.bookName = bookName;
        this.author = author;
    }
}
