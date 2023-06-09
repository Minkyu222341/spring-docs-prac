package com.prac.restdocs.repository;

import com.prac.restdocs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.prac.restdocs.repository
 * fileName       : UserRepository
 * author         : MinKyu Park
 * date           : 2023-06-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-06-09        MinKyu Park       최초 생성
 */
public interface UserRepository  extends JpaRepository<User,Long> {
}
