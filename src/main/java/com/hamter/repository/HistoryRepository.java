package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hamter.model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

}
