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
	@Id
	@GeneratedValue
	private long id;
	
	//해당 컬럼의 값은 not null 로 설정
	//@Column default 은 true
	@Column(nullable=false, length=400)
	private String userId;
	
	private String password;
	private String name;
	private String email;
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
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
		this.userId = newUser.userId;
		this.name = newUser.name;
		this.email = newUser.email;
	}
}
