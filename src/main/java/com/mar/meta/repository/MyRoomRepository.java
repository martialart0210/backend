package com.mar.meta.repository;

import com.mar.meta.entity.MyRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRoomRepository extends JpaRepository<MyRoomEntity,Long> {

}
