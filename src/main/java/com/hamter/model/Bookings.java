package com.hamter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Data
@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statusId;
    
    private String status2Id;

    private Integer doctorId;

    private String patientId;
    
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String timeType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;

}
