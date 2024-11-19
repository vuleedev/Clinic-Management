package com.hamster.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamster.dto.DoctorInfoDTO;
import com.hamster.interfaceService.IDoctorInfoService;
import com.hamster.model.DoctorInfo;
import com.hamster.repository.DoctorInfoRepository;

@Service
public class DoctorInfoService implements IDoctorInfoService {
	
	private final DoctorInfoRepository doctorRepository;
    private final Validator validator;
    
    @Autowired
    public DoctorInfoService(DoctorInfoRepository doctorInfoRepository) {
        this.doctorRepository = doctorInfoRepository;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public List<DoctorInfoDTO> getAllDoctors() {
        // Chuyển đổi danh sách DoctorInfo sang DoctorInfoDTO
        return doctorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DoctorInfoDTO> getDoctorById(Long id) {
    	Optional<DoctorInfo> doctorInfo = doctorRepository.findById(id);
        return doctorInfo.map(this::convertToDTO);
    }

    @Override
    public DoctorInfoDTO createDoctor(DoctorInfoDTO doctorInfoDTO) {
        // Chuyển đổi DTO sang entity
        DoctorInfo doctorInfo = convertToEntity(doctorInfoDTO);
        
        // Kiểm tra validation
        Set<ConstraintViolation<DoctorInfo>> violations = validator.validate(doctorInfo);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation errors occurred", violations);
        }
        
        if (doctorInfo.getName() == null || doctorInfo.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor's name cannot be empty");
        }
        
        if (!isValidEmail(doctorInfo.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (!isValidPhoneNumber(doctorInfo.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        
        // Lưu entity và chuyển đổi kết quả về DTO
        return convertToDTO(doctorRepository.save(doctorInfo));
    }

    @Override
    public DoctorInfoDTO updateDoctor(Long id, DoctorInfoDTO doctorInfoDTO) {
        // Chuyển đổi DTO sang entity
        DoctorInfo doctorInfo = convertToEntity(doctorInfoDTO);
        
        if (doctorRepository.existsById(id)) {
            doctorInfo.setId(id); // Set ID của bác sĩ từ request
            return convertToDTO(doctorRepository.save(doctorInfo));
        } else {
            throw new RuntimeException("Doctor not found with id " + id);
        }
    }

    @Override
    public void deleteDoctor(Long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Doctor not found with id " + id);
        }
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }
    
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Có thể thay đổi quy tắc kiểm tra số điện thoại nếu cần
        String phoneRegex = "^[0-9]{10,12}$"; // Chỉ cho phép 10-12 chữ số
        Pattern pattern = Pattern.compile(phoneRegex);
        return phoneNumber != null && pattern.matcher(phoneNumber).matches();
    }
    
    private DoctorInfo convertToEntity(DoctorInfoDTO doctorInfoDTO) {
        DoctorInfo doctorInfo = new DoctorInfo();
        doctorInfo.setName(doctorInfoDTO.getName());
        doctorInfo.setEmail(doctorInfoDTO.getEmail());
        doctorInfo.setPhoneNumber(doctorInfoDTO.getPhoneNumber());
        doctorInfo.setGender(doctorInfoDTO.getGender());
        doctorInfo.setProfilePicture(doctorInfoDTO.getProfilePicture());
        // Các trường khác nếu có...
        return doctorInfo;
    }
    
    private DoctorInfoDTO convertToDTO(DoctorInfo doctorInfo) {
        DoctorInfoDTO doctorInfoDTO = new DoctorInfoDTO();
        doctorInfoDTO.setId(doctorInfo.getId());
        doctorInfoDTO.setName(doctorInfo.getName());
        doctorInfoDTO.setEmail(doctorInfo.getEmail());
        doctorInfoDTO.setPhoneNumber(doctorInfo.getPhoneNumber());
        doctorInfoDTO.setGender(doctorInfo.getGender());
        doctorInfoDTO.setProfilePicture(doctorInfo.getProfilePicture());
        // Các trường khác nếu có...
        return doctorInfoDTO;
    }

}
