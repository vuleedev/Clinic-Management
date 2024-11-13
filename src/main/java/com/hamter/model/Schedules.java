package com.hamter.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Schedules")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer currentNumber;

    private Integer maxNumber;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String timeType;

    private Long doctorId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;
}
