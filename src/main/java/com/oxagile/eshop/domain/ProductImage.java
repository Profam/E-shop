package com.oxagile.eshop.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_IMAGES_PATH_COLUMN;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_IMAGES_PRODUCT_ID_COLUMN;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_IMAGES_TABLE;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = PRODUCT_IMAGES_TABLE)
public class ProductImage extends BaseEntity {
    @Column(name = PRODUCT_IMAGES_PRODUCT_ID_COLUMN, insertable = false, updatable = false)
    private int productId;
    @Column(name = PRODUCT_IMAGES_PATH_COLUMN)
    private String imagePath;
    @ManyToOne
    @JoinColumn(name = PRODUCT_IMAGES_PRODUCT_ID_COLUMN)
    private Product product;

    private ProductImage(Builder builder) {
        this.id = builder.id;
        this.productId = builder.productId;
        this.imagePath = builder.imagePath;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private int id;
        private int productId;
        private String imagePath;

        private Builder() {
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withProductId(int productId) {
            this.productId = productId;
            return this;
        }

        public Builder withImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public ProductImage build() {
            return new ProductImage(this);
        }
    }
}
