package com.hamster.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "allcodes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Allcodes {

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