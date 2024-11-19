package com.hamster.interfaceService;

import java.util.List;
import java.util.Optional;

import com.hamster.dto.DoctorInfoDTO;
import com.hamster.model.DoctorInfo;

public interface IDoctorInfoService {

	List<DoctorInfoDTO> getAllDoctors();

    // Lấy thông tin bác sĩ theo ID dưới dạng DTO
    Optional<DoctorInfoDTO> getDoctorById(Long id);

    // Tạo mới một bác sĩ, sử dụng DTO
    DoctorInfoDTO createDoctor(DoctorInfoDTO doctorInfoDTO);

    // Cập nhật thông tin của bác sĩ, sử dụng DTO
    DoctorInfoDTO updateDoctor(Long id, DoctorInfoDTO doctorInfoDTO);

    // Xóa bác sĩ theo ID
    void deleteDoctor(Long id);
}
