package net.dndlti.domain;

import org.springframework.data.jpa.repository.JpaRepository;

//데이터베이스의 table을 조회하거나 테이블에 레코드를 추가하려고
//interface 추가
//H2 데이터베이스의 User 테이블과의 연동을 명시
//JpaRepository<테이블명:net.dndlti.domain.User클래스, Primary key ID 컬럼의 자료형>
public interface UserRepository 
extends JpaRepository<User, Long> {
	//findBy 한 다음 ctrl + space -> findByUserId
	User findByUserId(String userId);
	/*User findByPassword(String password);*/
}
