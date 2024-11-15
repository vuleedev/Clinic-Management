package com.hamter.model;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "bookings")
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statusId;

    private Integer doctorId;

    private String patientId;
    
    private String email;
    
    private String cancelReason;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String timeType;
    
    @ManyToOne
    @JoinColumn(name = "timeSlot_id", nullable = false)
    private TimeSlot timeSlot;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;

}