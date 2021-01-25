package com.boot.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.boot.entity.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {
	
@Query("select count(dateOfRegistration) from  Guest s group by month(dateOfRegistration)")
	Integer[] getNoOfGuestRegisterdPerMonth();

@Modifying
@Transactional
@Query("update Guest u set u.status=false where u.id=:id") 
void softDelete(Integer id);

List<Guest> findTop5ByOrderByIdDesc();
}
