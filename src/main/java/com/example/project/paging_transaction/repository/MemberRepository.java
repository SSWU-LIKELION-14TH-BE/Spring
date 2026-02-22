package com.example.project.paging_transaction.repository;

import com.example.project.paging_transaction.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}