package com.bankaya.demo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankaya.demo.domain.RequestEntity;

public interface RequestRepository extends JpaRepository<RequestEntity, UUID> {

}
