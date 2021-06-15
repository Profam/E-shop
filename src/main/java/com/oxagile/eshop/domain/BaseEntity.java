package com.oxagile.eshop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EqualsAndHashCode()
@ToString
@Getter
public abstract class BaseEntity {
    @Id
    @Column(name = "id")
    protected int id;
}
