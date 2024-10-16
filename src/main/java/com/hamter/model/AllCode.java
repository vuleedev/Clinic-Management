package com.hamter.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "allcodes")
public class AllCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyMap;

    private String type;

    private String valueEn;

    private String valueVi;

    @Column(name = "createdAt", nullable = false)
    private java.util.Date createdAt;

    @Column(name = "updatedAt", nullable = false)
    private java.util.Date updatedAt;

}
