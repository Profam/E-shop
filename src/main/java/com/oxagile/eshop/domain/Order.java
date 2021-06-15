package com.oxagile.eshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(name = "user_id")
    private int userId;
    private Date date;
    private int price;
    @ManyToMany(mappedBy = "orders")
    @ToString.Exclude
    private List<Product> products;

    public int getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }


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