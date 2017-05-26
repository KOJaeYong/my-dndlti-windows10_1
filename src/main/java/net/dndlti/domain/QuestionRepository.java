package net.dndlti.domain;

import org.springframework.data.jpa.repository.JpaRepository;

//h2 데이터베이스의 question table을 조회하거나 테이블에 레코드를 추가할 수 
//있고 question 테이블과 연동할 수 있는 저장소 interface 생성
///H2 데이터베이스의 question 테이블과의 연동을 명시
//JpaRepository<테이블명:net.dndlti.domain.Question클래스, 
//Primary key ID 컬럼의 자료형>
public interface QuestionRepository extends 
JpaRepository<Question, Long> {

}
