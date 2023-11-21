# 계층형 아키텍처 패턴


<img src = "./rd_images/img1.jpg">

## Presentation Layer(@Controller)

1.  Client로부터 요청을 받아 Application Layer에 처리를 위함
2.  Application Layer의 결과를 최종 Client로 전달

--------------------


@Controller vs @RestController
- 일단 요청을 받아서 처리하는 공통 기능을 수행하지만, 응답하는 형태가 다름
- Controller : view를 응답(html 파일 등)
- RestController : data를 응답(문자열, Json, xml 등)


------------------------


@RequestMapping : Client의 특정 요청이 있을 때 Spring Framework에 의해 호출됨
@RequestParam : key와 value값을 이용해 요청 전달
 - @RequestParam의 요소들
   1. name : query string의 key
   2. required : 필수 여부
   3. defaultValue : 데이터가 없을 경우 기본 값

-> 이 두 가지는 Request에 대한 요청을 처리한다.
-> 그런데 이보다 큰 데이터를 전달하고자 한다면 ,
-> @RequestBody 사용 가능

--------------------------

### Controller 개발 - RequestDTO, ResponseDTO

- DTO : 데이터를 전송하여 다른 곳으로 전송하기 위한 목적의 객체
-> REST API에서 요청 데이터나 응답 데이터를 전송하는데 사용



-----------------------------------------

## Application Layer(@Service)

1. 어플리케이션을 위한 다양한 비즈니스 로직을 처리
2. 파라미터로 전달 된 데이터들의 검증 작업 수행
3. 어플리케이션의 세부 영역 별로 클래스를 생성하여 구현
-> 이 과정에서 객체 생성이 필수로 들어가야 함
-> 하지만 @Service을 이용하여 자동 객체 생성을 사용하게됨

### 의존성 주입(DI)
- 객체 생성을 외부에서 대신 수행
- 활용할 객체에 대한 의존성(참조) 설정을 외부에서 대신 해줌
- 활용할 클래스(인터페이스) 타입의 멤버 변수만 선언 후 생성자 구현(= new 키워드를 사용한 객체 생성이 더이상 필요없음)
- 클래스 상단에서 사용

### @Configuration과 @Bean Annotation
- @Configuartion 클래스 내에 @Bean을 사용한 메소드로 Bean 생성 가능
- 단순히 Bean으로 등록할 객체를 생성 후 Return하면 됨
- 메소드 상단에서 사용


-----------------------------------------

## Data Access Layer

1. Database에 접근하여 데이터 저장, 조회 등을 수행하는 역할
2. Service와 Database 사이의 추상화 된 계층


### 데이터를 접근하기 위한 기술

1. JDBC - Database에 접근하기 위한 JAVA 표준 API
2. JDBC Template - JDBC를 효율적으로 사용하기 위한 Spring API
4. SQL Mapper - SQL과 JAVA를 매핑하는 기술 ( ex. MyBatis )
5. ORM 
   - RDBMS의 테이블과 JAVA 객체를 매핑하는 기술
   - ORM Framework이 SQL를 자동으로 생성
   - ex) Hibernate, Spring Data JPA
   
   

   


