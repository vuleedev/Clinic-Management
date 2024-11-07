package com.hamter.model;

import java.util.Date;
import java.util.List;

import com.hamter.dto.SpecialtyDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "doctor_info")
public class DoctorInfo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;             // ID bác sĩ (liên kết với bảng DoctorInfo)

    private Long specialtyId;          // ID chuyên khoa (liên kết với bảng Specialty)

    private Long clinicId;             // ID phòng khám (liên kết với bảng Clinic)

    private String priceId;            // ID giá dịch vụ (liên kết với bảng Price)

    private Long provinceId;           // ID tỉnh thành nơi bác sĩ làm việc

    private String paymentId;          // Phương thức thanh toán (liên kết với bảng Payment)

    private String addressClinic;      // Địa chỉ phòng khám

    private String nameClinic;         // Tên phòng khám

    private String note;               // Ghi chú về bác sĩ hoặc phòng khám

    private Integer count;             // Số lượt khám hoặc số ca đã đặt

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;

    // Quan hệ với bảng Clinic
    @ManyToOne
    @JoinColumn(name = "clinic_id", insertable = false, updatable = false)
    private Clinics clinic;

    // Quan hệ với bảng Specialty
    @ManyToOne
    @JoinColumn(name = "specialty_id", insertable = false, updatable = false)
    private Specialties specialty;

    // Quan hệ với bảng Booking (một bác sĩ có thể có nhiều lịch hẹn)
    @OneToMany(mappedBy = "doctorInfo", cascade = CascadeType.ALL)
    private List<Bookings> bookings;
}
