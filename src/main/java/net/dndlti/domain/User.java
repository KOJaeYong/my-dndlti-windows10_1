package net.dndlti.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity 데이터베이스와 연동하는 클래스라는 것을 표시하기 위해 
//@Entity어노테이션 사용 
//클래스 이름으로 H2 데이터베이스의 User 테이블 생성됨 
//- jpa 라이브러리 사용으로 가능함
@Entity
public class User {
	
	//숫자를 자동증가하는 방식으로 primary key 로 사용하려면
	//@GeneratedValue 자동으로 1씩 증가
  //데이터형 Long 을 사용해야 함 - long 타입과 동일하지 않음
	@Id
	@GeneratedValue
	private Long id;
	
	//해당 컬럼의 값은 not null 로 설정 
	//@Column nullable default 은 true,
	//unique 은 컬럼의 값은 유일해야 한다는 속성값 변경, default 은 false
	@Column(nullable=false, length=400, unique=true)
	private String userId; //User 테이블의 Userid
	private String password;
	private String name;
	private String email;
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}
	
	/*4-3 자기 자신에 한 해 개인정보수정*/
	//데이터형 Long 을 사용해야 함 - long 타입과 동일하지 않음
	/*public Long getId() {
	  return id;
	}*/
	
	public boolean getId(Long newId) {
	  if (newId == null || !newId.equals(id)) {
	    return false;
    }
	  return true;
	}
	
	/*public String getPassword() {
		return password;
	}*/
	
	public boolean matchPassword(String newPassword) {
	  if (!newPassword.equals(password)
	      ||newPassword == null) {
	    return false;
	  }
	  return true;
	}
	
	/* 4-5 강의에서 추가 질문하기, 질문목록 기능 구현 
	세션로그인을 한 후 질문하기 한 회원의 userid 조회 */
	public String getUserId() {
    return userId;
  }
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void update(User newUser) {
		this.password = newUser.password;
		this.name = newUser.name;
		this.email = newUser.email;
	}

  
	

  
}
