package com.scaler.EcomProductService.service;

import com.scaler.EcomProductService.demo.Author;
import com.scaler.EcomProductService.demo.AuthorRepository;
import com.scaler.EcomProductService.demo.Book;
import com.scaler.EcomProductService.model.Category;
import com.scaler.EcomProductService.model.Order;
import com.scaler.EcomProductService.model.Price;
import com.scaler.EcomProductService.model.Product;
import com.scaler.EcomProductService.repository.CategoryRepository;
import com.scaler.EcomProductService.repository.OrderRepository;
import com.scaler.EcomProductService.repository.PriceRepository;
import com.scaler.EcomProductService.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitServiceImpl implements InitService{

    private CategoryRepository categoryRepository;
    private PriceRepository priceRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private AuthorRepository authorRepository;

    public InitServiceImpl(CategoryRepository categoryRepository, PriceRepository priceRepository, ProductRepository productRepository, OrderRepository orderRepository, AuthorRepository authorRepository) {
        this.categoryRepository = categoryRepository;
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void initialise() {
        // Electronics Category
        Category electronics = new Category();
        electronics.setCategoryName("Electronics");
        electronics = categoryRepository.save(electronics);

        // Adding Prices for few products
        Price iPhonePrice = new Price();
        iPhonePrice.setCurrency("INR");
        iPhonePrice.setAmount(100000);
        iPhonePrice.setDiscount(0);
        iPhonePrice = priceRepository.save(iPhonePrice);

        Price macBookPrice = new Price();
        macBookPrice.setCurrency("INR");
        macBookPrice.setAmount(200000);
        macBookPrice.setDiscount(0);
        macBookPrice = priceRepository.save(macBookPrice);

        Price watchPrice = new Price();
        watchPrice.setCurrency("INR");
        watchPrice.setAmount(40000);
        watchPrice.setDiscount(0);
        watchPrice = priceRepository.save(watchPrice);

        Price ps5Price = new Price();
        ps5Price.setCurrency("INR");
        ps5Price.setAmount(60000);
        ps5Price.setDiscount(0);
        ps5Price = priceRepository.save(ps5Price);

        // Adding Products
        Product iphone = new Product();
        iphone.setCategory(electronics);
        iphone.setPrice(iPhonePrice);
        iphone.setDescription("Best Iphone ever");
        iphone.setImage("www.someIphoneImage.com/images");
        iphone.setTitle("Iphone 15 Pro");
        iphone = productRepository.save(iphone);

        Product macBook = new Product();
        macBook.setCategory(electronics);
        macBook.setPrice(macBookPrice);
        macBook.setDescription("Best macBook ever");
        macBook.setImage("www.somemacBookImage.com/images");
        macBook.setTitle("macBook 16 Pro");
        macBook = productRepository.save(macBook);

        Product watch = new Product();
        watch.setCategory(electronics);
        watch.setPrice(watchPrice);
        watch.setDescription("Best watch ever");
        watch.setImage("www.somewatchImage.com/images");
        watch.setTitle("apple smart watch ");
        watch = productRepository.save(watch);

        Product ps5 = new Product();
        ps5.setCategory(electronics);
        ps5.setPrice(ps5Price);
        ps5.setDescription("Best Play station ever");
        ps5.setImage("www.somepSImage.com/images");
        ps5.setTitle("PlayStation 5");
        ps5 = productRepository.save(ps5);

        //Orders
        Order order = new Order();
        order.setProducts(List.of(iphone,macBook,watch));
        orderRepository.save(order);

        //Custom Queries
        Product prod = productRepository.findByTitleLike("Iphone");
        System.out.println(prod);

        Product prod1 = productRepository.findByDescriptionTitleLike("Best","Station");
        System.out.println(prod1);

        //Author&Books for CascadeType & FetchType demo

        Author author1 = new Author("Girish Kumar" ,null);
        Author author2 = new Author("Ranjith" , null);

        Book book1 = new Book("Book 1" , author1);
        Book book2 = new Book("Book 2" , author1);
        Book book3 = new Book("Book 3" , author1 );
        Book book4 = new Book("Book 4 ", author2);
        Book book5 = new Book("Book 5 ", author2);

        List<Book> gBooks = new ArrayList<>();
        gBooks.add(book1);
        gBooks.add(book2);
        gBooks.add(book3);
        author1.setBooks(gBooks);

        //Cascading Demo for C U D operations
        authorRepository.save(author1);// cascade ALL -> If we save author , all dependent objects objects are also saved.
        author2.setBooks(List.of(book4,book5));
        authorRepository.save(author2);

        // authorRepository.delete(author1); // Cascade remove -> deletes dependent entities as well
        author1.removeBook(book3); // book3 is orphan now as it is detached from author1
        authorRepository.save(author1); // without orphanRemoval , book3 will not be removed. OrphanRemoval removes orphaned entities.


        //FetchType Demo for  R operation
        Author savedAuthor = authorRepository.findById(2).get();
        // If FetchType is eager , loads books attribute as well for that author using join query.
        //If FetchType is lazy , only author table is loaded , and the below line gives LazyInitialization Exception
        //If we make this method as Transactional , all the queries will be treated as single transaction and so it makes a open connection with DB to load all related data, so as to avoid LazyInitialization Exception, So @Transactional methods act like eager loading.
        List<Book> books = savedAuthor.getBooks();
        System.out.println("id - 2 books : -"+books);

    }
}
