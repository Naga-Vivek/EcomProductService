package com.scaler.EcomProductService.demo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    //Author : Book => 1 : M
    @OneToMany(mappedBy = "author" , cascade = CascadeType.ALL ,orphanRemoval = true , fetch = FetchType.EAGER)
    //Default fetch mode for EAGER is JOIN
    @Fetch(FetchMode.SELECT)
    private List<Book> books;

    public Author(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    public void removeBook(Book book){
        books.remove(book);
    }
}
