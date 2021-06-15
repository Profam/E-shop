package com.oxagile.eshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(name = "category_id", insertable = false, updatable = false)
    private int categoryId;
    private String name;
    private String description;
    private int price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "orders_products",
            joinColumns = {
                    @JoinColumn(name = "order_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "product_id")
            }
    )
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<ProductImage> images;

    private Product(Builder builder) {
        this.id = builder.id;
        this.categoryId = builder.categoryId;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.images = builder.images;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private int id;
        private int categoryId;
        private String name;
        private String description;
        private int price;
        private List<ProductImage> images;

        private Builder() {
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withCategoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder withImages(List<ProductImage> images) {
            this.images = images;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
