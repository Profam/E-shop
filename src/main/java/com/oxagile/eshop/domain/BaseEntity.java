package com.oxagile.eshop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import static com.oxagile.eshop.controllers.pools.ParamsPool.ENTITY_ID_PARAM;

@MappedSuperclass
@EqualsAndHashCode()
@ToString
@Getter

public abstract class BaseEntity {
    @Id
    @Column(name = ENTITY_ID_PARAM)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
}
