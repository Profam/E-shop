package com.oxagile.eshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_CATEGORY_ID_COLUMN;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_LIST_ATTRIBUTE;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = PRODUCT_LIST_ATTRIBUTE)
public class Product extends BaseEntity {

    @Column(name = PRODUCT_CATEGORY_ID_COLUMN, insertable = false, updatable = false)
    private int categoryId;
    private String name;
    private String description;
    private int price;
    @ManyToOne
    @JoinColumn(name = PRODUCT_CATEGORY_ID_COLUMN)
    @ToString.Exclude
    private Category category;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = PRODUCT_LIST_ATTRIBUTE)
    @ToString.Exclude
    private List<Order> orders;
    @OneToMany(mappedBy = PRODUCT_ATTRIBUTE, fetch = FetchType.EAGER)
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
