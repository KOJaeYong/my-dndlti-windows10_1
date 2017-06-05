package net.dndlti.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

//데이터베이스와 연동하는 클래스라는 것을 표시하기 위해 
//@Entity어노테이션 사용 
//클래스 이름으로 H2 데이터베이스의 Answer 테이블 생성됨 
//- jpa 라이브러리 사용으로 가능함
//답변(하기) 테이블 생성
@Entity
public class Answer {
  
  //id 컬럼을 primary key 로 사용하고 숫자를 자동증가하는 방식으로 타입 설정  
  //@GeneratedValue - 자동으로 1씩 증가
  //데이터형 Long 을 사용해야 함 - long 타입과 동일하지 않음
  //@JsonProperty 은 해당 정보를 Json 데이터로 전송 가능하도록 설정
  @Id
  @GeneratedValue
  @JsonProperty
  private Long id;
  
  //User 객체와 관계를 맺기 위해 필드 생성
  //answer 와 user 객체는 ManyToOne 관계이다.
  //User 객체에 대한 외래키를 지정하기 위해 @JoinColumn 어노테이션 사용하여
  //foreignKey 속성을 사용할 수 있다.
  //User 테이블의 기본키인 id 컬럼을 참조하는 
  //User 객체의 인스턴스 객체 writer 생성
  @ManyToOne
  @JoinColumn(
  foreignKey = @ForeignKey(name="fk_answer_writer"))
  @JsonProperty
  private User writer;
  
  //하나의 질문(question) 에는 여러 개의 답변(answer)이 달리기 때문에
  //answer 와 question 객체는 ManyToOne 관계이다.
  //question 객체에 대한 외래키를 지정하기 위해 @JoinColumn 어노테이션 사용하여
  //foreignKey 속성을 사용할 수 있다.
  //Question 테이블의 기본키인 id 컬럼을 참조하는 
  //Question 객체의 인스턴스 객체 question 생성
  @ManyToOne
  @JoinColumn(
  foreignKey=@ForeignKey(name="fk_answer_to_question"))
  @JsonProperty
  private Question question;
  
  //질문 작성 내용을 저장하는 데이터 형식을 CLOB 로 설정 
  @Lob
  @JsonProperty
  private String contents;
  
  //mustache 에서도 getter 메소드 지원함 - 
  //getFormattedCreateDate() 사용하여 
  //결과 값을 show.html 로 데이터 전송하고 
  //formattedCreateDate 라는 이름으로 결과 값을 출력하는 문장 형식 사용
  public String getFormattedCreateDate() {
    if (createDate == null) {
      return "";
    }
    return createDate.format(
    DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
  }
  
  /*5-1 질문하기 입력 시간을 표시하는 컬럼 추가*/
  //LocalDateTime - Java 1.8 부터 지원하는 
  //다수의 스레드들로부터 안전하게 접근할 수 있고 변경이 불가능한 final class
  private LocalDateTime createDate;
  
  public Answer() {
  }
  
  //답변을 입력하는 세션로그인 작성자, 내용을 저장 
  public Answer(User writer, Question questionId, 
  String contents) {
    this.writer = writer;
    this.question = questionId;
    
    contents = contents.replace("  ", "&nbsp;&nbsp;");
    contents = contents.replaceAll("\r\n", "<br/>");
    this.contents = contents;
    
    this.createDate = LocalDateTime.now();
  }
  
  //User 인스턴스 객체 writer 필드와
  //User 인스턴스 객체 loginUser 가 같은 객체에 해당하는가
  //결과가 false 가 나올 경우 해당 클래스(User 클래스) 내부에 
  //hashCode(), equals() 메소드를 재정의해야 한다.
  public boolean isSameWriter(User loginUser) {
    return loginUser.equals(this.writer);
  }
  
  @Override
  public String toString() {
    return "Answer [id=" + id + ", writer=" + writer + ", contents=" + contents + ", createDate=" + createDate + "]";
  }
}
