# Try-chat 채팅 어플리케이션 개발 (2024.04 ~ ING)

실시간 채팅 기능을 제공하는 채팅 애플리케이션.

### Skill & Tool

- SpringBoot, Spring Security, MySql, JPA, Junit5,  Swagger, Git, PostMan, Notion

### 개발 인원

- 프론트 1명, 백엔드 1명

### 기능 구현

- 웹소켓을 사용하여 1 : 1 채팅 기능 개발
- 회원 관련 기능 개발
- Swagger를 사용하여 API 문서화
- Junit5를 사용한 테스트 코드 작성
- Swagger UI : https://www.try-chat.co.kr/swagger-ui/index.html

### ERD(수정중)

### 문제해결 및 개선

- 프로젝트를 진행하면서 프론트의 개발 속도가 서로 많이 다르다는 문제가 발생. 해결 방법으로 Fake Api를 사전 작성 하는 방법을 적용.
- API에 대한 문서화를 Notion에서 Swagger로 변경. 문서화 작업에 투자하던 시간이 감축.
- EC2 프리티어에서 제공하는 부족한 메모리를 확장하기 위해서 리눅스에서 사용 가능한 SWAP 메모리라는 해결 방법을 찾아 해결.
    - https://repost.aws/ko/knowledge-center/ec2-memory-swap-file
- RestAssured 라이브러리를 통한 테스트 진행 중 배포 시에 해결 못할 치명적인 문제가 발생하여 MockMvc로 변경.
