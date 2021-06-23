package com.oxagile.eshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

import static com.oxagile.eshop.controllers.pools.ParamsPool.ENTITY_ID_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ORDER_LIST_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ORDER_ORDER_ID_COLUMN;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ORDER_PRODUCTS_TABLE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ORDER_USER_ID_COLUMN;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRODUCT_ID_COLUMN;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = ORDER_LIST_ATTRIBUTE)
public class Order extends BaseEntity {
    @Column(name = ORDER_USER_ID_COLUMN)
    private int userId;
    private Date date;
    private int price;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = ORDER_PRODUCTS_TABLE,
            joinColumns = {
                    @JoinColumn(name = ORDER_ORDER_ID_COLUMN, referencedColumnName = ENTITY_ID_PARAM)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = PRODUCT_PRODUCT_ID_COLUMN, referencedColumnName = ENTITY_ID_PARAM)
            }
    )
    private List<Product> products;

    private Order(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.date = builder.date;
        this.price = builder.price;
        this.products = builder.products;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private int id;
        private int userId;
        private Date date;
        private int price;
        private List<Product> products;

        private Builder() {
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder withDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder withPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder withProducts(List<Product> products) {
            this.products = products;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}