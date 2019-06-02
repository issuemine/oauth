Naver OAuth2.0
=============
네이버의 OAuth를 이용한 계정 관리 API 서버어플리케이션입니다.

패키지 및 클래스 구성은 다음과 같습니다.

- common (공통 패키지)
  - controller - HomeController (User 정보 관리 컨트롤러)
  - dto - Error (Exception Error 정보 관리객체)
  - exception - 사용자 정의 Excpetion 정의
  - handler - GlobalExceptionHandler (Exception 관리 controller advice)
  - util - Jackson (JSON 형태의 String을 JSON 혹은 특정 객체로 변경)

- user (계정 관리)
  - controller - LoginController (OAuth 관련 컨트롤러)
  - constants - SessionConstants (LoginController에서 공통으로 쓰이는 문자열 정의)
  - domain - User (User 정보 관리 객체)
  - service - UserService (OAuth 관련 로직 제공)
  - respository - UserRepository (User 관련 DB 접근 제공)

### API 설명
해당 어플리케이션은 다음과 같은 API를 제공합니다.
  - /users (로그인이 되어 있지 않으면 /login API로 redirect 후 로그인 절차 진행, 로그인이 되어 있으면 user 정보 JSON 형태 출력)
  - /login (OAuth와 통신하여 사용자에게 로그인을 제공해주는 API)
  - /users/logout (로그인한 유저 로그아웃 처리)
  
### 어플리케이션 실행 방법 및 사용법
  - IDE에 해당 프로젝트 Import 후 스프링부트 내장 톰캣 실행
  - 기본적으로  80번 포트로 설정되어 있습니다. 변경시 application.properties의 server.port를 변경해주시면 됩니다.
  - 로컬 MySQL을 사용합니다. 스키마는 /oauth를 사용합니다.(JPA의 table 자동 옵션이 활성화될 경우  스키마의 table이 지워질 수도 있습니다. 주의하여주시길 바랍니다.)
  - 위의 설정을 변경하고 싶으시면 application.peroperties 파일의 spring.datasource를 변경해주시길 바랍니다.
  - 로그인을 원하시면 http://localhost/users 를 이용하시면 됩니다.