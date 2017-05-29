package net.dndlti.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

//데이터베이스와 연동하는 클래스라는 것을 표시하기 위해 
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
  
  //User 객체와 관계를 맺기 위해 필드 생성
  //question 과 user 객체는 ManyToOne 관계이다.
  //User 객체에 대한 외래키를 지정하기 위해 @JoinColumn 어노테이션 사용하여
  //foreign 속성을 사용할 수 있다.
  //User 테이블의 기본키인 id 컬럼을 참조하는 
  //User 객체의 인스턴스 객체 writer 생성
  @ManyToOne
  @JoinColumn(foreignKey=
  @ForeignKey(name="fk_question_writer"))
  private User writer;
  /*private String writer;*/
  
  private String title;
  
  //질문 작성 내용을 저장하는 데이터 형식을 CLOB 로 설정 
  @Lob
  private String contents;
  
  /*5-1 질문하기 입력 시간을 표시하는 컬럼 추가*/
  //LocalDateTime - Java 1.8 부터 지원하는 
  //다수의 스레드들로부터 안전하게 접근할 수 있고 변경이 불가능한 final class
  private LocalDateTime createDate;
  
  /*
  Answer 클래스에 있는 Question 인스턴스 객체 question와 
  mapping 되어야 한다. - mappedBy="question"
  @ManyToOne
  @JoinColumn(
  foreignKey=@ForeignKey(name="fk_answer_to_question"))
  private Question question;
  */
  //질문과 답변의 관계 - 일대다 관계
  //Answer 테이블의 id 컬럼을 내림차순으로 정렬 
  //show.html 에 mustache 템플릿을 사용하여 답변 목록을 만들 수 있다.
  @OneToMany(mappedBy="question")
  @OrderBy("id DESC")
  private List<Answer> answers;
  
  //jpa 에서는 매핑을 할 때 기본 생성자를 반드시 생성해야 한다.
  public Question() {
  }
  
  /*public Question(String writer, String title, 
  String contents) {*/
  //(가급적) User 객체를 사용하여 질문하기의 writer 입력란을 작성하도록 수정
  public Question(User writer, String title, 
  String contents) {
    this.writer = writer;
    this.title = title;
    this.contents = contents;
    //LocalDateTime 은 final class 이므로 바로 접근할 수 있다.
    //질문하기 입력한 시간을 표시
    this.createDate = LocalDateTime.now();
  }
  
  //mustache 에서도 getter 메소드 지원함 - 
  //getFormattedCreateDate() 사용하여 
  //결과 값을 index.html 로 데이터 전송하고 
  //formattedCreateDate 라는 이름으로 결과 값을 출력하는 문장 형식 사용
  public String getFormattedCreateDate() {
    if (createDate == null) {
      return "";
    }
    return createDate.format(
    DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
  }
  
  //질문을 입력한 작성자만 질문 제목,내용 수정
  public void update(String title, String contents) {
    this.title = title;
    this.contents = contents;
  }

  public boolean isSameWriter(User loginUser) {
    //User 인스턴스 객체 writer 와 
    //User 인스턴스 객체 loginUser 가 같은 객체에 해당하는가
    //결과가 false 가 나올 경우 해당 클래스(User 클래스) 내부에 
    //hashCode(), equals() 메소드를 재정의해야 한다.
    return this.writer.equals(loginUser);
  }
}
