package com.hamter.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hamter.dto.SpecialtyDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "doctor_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorInfo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;             

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
