# security-test

- Junit5 변경 사항
  - @RunWith(SpringRunner.class) -> @ExtendWith(SpringExtension.class)
  ![image](https://user-images.githubusercontent.com/62360009/132111192-1677662d-7d37-4686-b814-82506bb96c11.png)
  - @WithMockUser를 위해 testImplementation 'org.springframework.security:spring-security-test' 추가
 
- Spring Security 의존성 추가 시(security, security-test) 기본적으로 모든 URL을 보호해준다.
  - 따라서 localhost:8080 기본 접속 시 로그인 페이지(default page)로 이동한다.
  - 로그인하지 않으면 /login으로 이동해서 로그인할 수 있도록 만들어놨다.
  - application.properties에 다음과 같이 password를 설정할 수 있다.
<img src="https://user-images.githubusercontent.com/62360009/132111243-2bc66d31-aca9-44c4-aa7a-4eb70f10c668.png" width="490" height="70"/>

  - 다시 앱 실행 후 user, password를 폼에 넣어주면 정상 동작한다.
  - Spring Security 적용하면 모든 url에 인증이 필수가 된다. 인증 없이 서버에 요청 시 401(Unauthorized)코드를 받게 된다. /는 인증 없이 모든 사람이 볼 수 있도록 설정을 바꿔줘야 한다.

- WebSecurityConfigurerAdapter
  - 이를 상속받는 설정 클래스(WebSecurityConfig)를 통해 접근 관리 규칙을 변경한다.
  - http.authorizeRequests() : 접근 관리 시작
  - .antMatchers("/").permitAll() : "/" 요청에 대해 허락
  - .anyRequest().authenticated() : 그 외의 모든 요청은 인증 요구
  - .httpBasic() : BasicAuth를 사용해서 로그인을 요구하는 규칙. 팝업을 통해 id / password 물어보게 됨

- @WithUserMock
  - @WithUserMock(value = "fake-user")를 통해 가상의 로그인된 사용자가 있다고 가정한다.
  - 이 때 roles가 없으면 default가 USER이다. value는 그냥 지정된 이름인 듯 하다.
  
<img src="https://user-images.githubusercontent.com/62360009/132111371-172f7939-6444-412a-be11-99987b7d7ef3.png" width="1000" height="250"/>
     
  - .antMatchers("/admin").hasRole("ADMIN") : admin roles가 있는 사람만 접근 가능한 규칙
  - .antMatchers("/private").hasRole("USER") : user roles가 있는 사람만 접근 가능한 규칙
  - 규칙을 설정할 때는 순서와 표현식이 중요하다. 규칙 평가 시 위에서 부터 순서대로 평가한다.
  - 따라서 가장 세세한 규칙이나 예외 규칙이 먼저 오고, 가장 포괄적인 규칙이 마지막에 오도록 작성한다!
  - .antMatchers("/admin/**").hasRole("ADMIN") : /** 표현식은 /밑의 모든 url을 나타낸다.

<img src="https://user-images.githubusercontent.com/62360009/132111411-0f0fa1b8-1d67-4b0e-9544-f428ec02d556.png" width="490" height="70"/>
  - 같은 규칙이지만 순서가 바뀌면 전혀 다른 이야기가 된다. 다음을 실행하면 /admin 요청을 받아도, /** 표현식이 /admin을 표현하기 때문에 첫번째 규칙에 해당되어, 사용자라면 /admin에 접근이 가능하다. 

  - @WithMockUser(value="fake-user", roles = "ADMIN")을 통해 테스트 실행 시 fake-user에 ADMIN 권한을 부여할 수 있다.
  
