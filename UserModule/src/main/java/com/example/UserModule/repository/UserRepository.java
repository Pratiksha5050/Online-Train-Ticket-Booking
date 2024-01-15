package com.example.UserModule.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.UserModule.entity.UserInfo;


@Repository
public interface UserRepository extends MongoRepository<UserInfo, String> {
	@Query("{'userName' : :#{#userName}}")
	Optional<UserInfo> findByName(@Param("userName") String userName);
}
