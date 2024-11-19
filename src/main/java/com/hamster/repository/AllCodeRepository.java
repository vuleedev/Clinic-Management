package com.hamster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamster.model.Allcodes;

public interface AllCodeRepository extends JpaRepository<Allcodes, Long> {

}
