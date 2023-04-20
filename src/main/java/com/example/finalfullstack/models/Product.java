package com.example.finalfullstack.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Наименование товара не может быть пустым")
    @Column(name = "title", nullable = false, columnDefinition = "text", unique = true)
    private String title;

    @NotEmpty(message = "Описание товара не может быть пустым")
    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "price", nullable = false)
    @Min(value = 1, message = "Цена должна быть больше 0")
    private float price;

    @Column(name = "warehouse", nullable = false)
    @NotEmpty(message = "Укажите склад")
    private String warehouse;

    @NotEmpty(message = "Укажите продавца")
    @Column(name = "seller", nullable = false)
    private String seller;

    @ManyToOne(optional = false)
    private Category category;

    private LocalDateTime dateTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> imageList = new ArrayList<>();

    public void addImageToProduct(Image image){
        image.setProduct(this);
        imageList.add(image);
    }

    @ManyToMany()
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> personList;

    @ManyToMany()
    @JoinTable(name = "product_order", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "orders_id"))
    private List<Order> orderList;

    @PrePersist
    private void init(){
        dateTime = LocalDateTime.now();
    }

    public Product() {
    }

    public Product(String title, String description, float price, String warehouse, String seller, Category category, LocalDateTime dateTime, List<Image> imageList) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.warehouse = warehouse;
        this.seller = seller;
        this.category = category;
        this.dateTime = dateTime;
        this.imageList = imageList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}
