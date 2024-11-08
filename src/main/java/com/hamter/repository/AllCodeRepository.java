package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hamter.model.Allcodes;

public interface AllCodeRepository extends JpaRepository<Allcodes, Long> {

}
