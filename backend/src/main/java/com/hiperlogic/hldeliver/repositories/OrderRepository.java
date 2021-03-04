package com.hiperlogic.hldeliver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiperlogic.hldeliver.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
}
