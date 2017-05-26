package net.dndlti.domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity 데이터베이스와 연동하는 클래스라는 것을 표시하기 위해 
//@Entity어노테이션 사용 
//클래스 이름으로 H2 데이터베이스의 Question 테이블 생성됨 
//- jpa 라이브러리 사용으로 가능함
//질문(하기) 테이블 생성
@Entity
public class Question {

  //id 컬럼을 primary key 로 사용하고 숫자를 자동증가하는 방식으로 타입 설정  
  //@GeneratedValue - 자동으로 1씩 증가
  //데이터형 Long 을 사용해야 함 - long 타입과 동일하지 않음
  @Id
  @GeneratedValue
  private Long id;
  
  private String writer;
  private String title;
  private String contents;
  
  //jpa 에서는 매핑을 할 때 기본 생성자를 반드시 생성해야 한다.
  public Question() {
  }
  
  public Question(String writer, String title, 
  String contents) {
    super();
    this.writer = writer;
    this.title = title;
    this.contents = contents;
  }
}
