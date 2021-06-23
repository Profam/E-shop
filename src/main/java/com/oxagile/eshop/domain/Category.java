package com.oxagile.eshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORIES_LIST_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_IMAGE_PATH_COLUMN;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_PARAM;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = CATEGORIES_LIST_ATTRIBUTE)
public class Category extends BaseEntity {
    private String name;
    @Column(name = CATEGORY_IMAGE_PATH_COLUMN)
    private String imagePath;
    private int rating;
    @OneToMany(mappedBy = CATEGORY_PARAM, fetch = FetchType.EAGER)
    private List<Product> productList;

    private Category(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.imagePath = builder.imagePath;
        this.rating = builder.rating;
        this.productList = builder.productList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private int id;
        private String name;
        private String imagePath;
        private int rating;
        private List<Product> productList;

        private Builder() {
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder withRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder withProductList(List<Product> productList) {
            this.productList = productList;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
