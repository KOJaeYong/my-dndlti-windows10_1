package net.dndlti.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

//@Entity 데이터베이스와 연동하는 클래스라는 것을 표시하기 위해 
//@Entity어노테이션 사용 
//클래스 이름으로 H2 데이터베이스의 User 테이블 생성됨 
//- jpa 라이브러리 사용으로 가능함
@Entity
public class User {
	
	//숫자를 자동증가하는 방식으로 primary key 로 사용하려면
	//@GeneratedValue 자동으로 1씩 증가
  //데이터형 Long 을 사용해야 함 - long 타입과 동일하지 않음
  //@JsonProperty 은 해당 정보를 Json 데이터로 전송 가능하도록 설정
	@Id
	@GeneratedValue
	@JsonProperty
	private Long id;
	
	//해당 컬럼의 값은 not null 로 설정 
	//@Column nullable default 은 true,
	//unique 은 컬럼의 값은 유일해야 한다는 속성값 변경, default 은 false
	@Column(nullable=false, length=400, unique=true)
	@JsonProperty
	private String userId; //User 테이블의 Userid
	
	//@JsonIgnore 은 Json 데이터로 전송하지 않도록 설정
	@JsonIgnore
	private String password;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String email;
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}
	
	/*Question 클래스에 있는 isSameWriter(User loginUser) 메소드에 있는
	 * writer.equals(loginUser) - 
	 * equals() 메소드를 사용하여 정확한 결과가 나오도록  
	 * hashCode(), equals() 메소드를  override 하여 추가
	public boolean isSameWriter(User loginUser) {
    return this.writer.equals(loginUser);
  }
  */
	//equals 메소드를 사용하기 위한 hashCode(), equals() 메소드 추가
	//Alt+Shift+S - Generate hashcode() and equals() 선택하고
	//id 만 체크하고 finish
	@Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
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
	  return newId.equals(id);
	}
	
	/*public String getPassword() {
		return password;
	}*/
	
	public boolean matchPassword(String newPassword) {
	  if (!newPassword.equals(password)
	      ||newPassword == null) {
	    return false;
	  }
	  return newPassword.equals(password);
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
