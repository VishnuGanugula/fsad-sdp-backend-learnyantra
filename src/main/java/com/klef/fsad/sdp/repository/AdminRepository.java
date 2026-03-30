package com.klef.fsad.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsad.sdp.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,String>
{ 
   // select a from Admin a where a.username=?1 and a.password=?1
   Admin findByUsernameAndPassword(String username, String password);
}
