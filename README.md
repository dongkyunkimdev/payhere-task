# payhere-task
> Kotlin, Spring Boot로 개발한 페이히어 과제

## 기능
- 이메일, 비밀번호를 통해 회원가입
- 이메일, 비밀번호를 통해 로그인하여 jwt 발급
- jwt를 통해 인증을 수행하여 아래와 같은 가계부 관리 기능 사용 가능
  - 금액, 메모가 적힌 가계부 작성
  - 가계부 수정
  - 가계부 삭제
  - 가계부 복구
  - 가계부 전체 목록 조회
  - 가계부 상세 조회

## 아키텍처
- 도메인 지식이 간단하여 Layered Architecture 개념을 적용
- Presentation -> Application ( -> Domain ) -> Infrastructure
- 추후 서비스가 성장하는 경우 MSA로의 전환이 용이하도록 고객, 가계부 도메인을 별도 디렉토리로 구성(단일 프로젝트 멀티 모듈로 구성하면 각 모듈(도메인)의 의존성도 별도로 가져갈 수 있기 때문에 더 좋은 방법일 듯)

## 프로젝트 주요 관심사
- 주어진 요구사항을 만족하는 코드 작성
- docker compose를 사용하여 컨테이너 기반 환경 구축
- application service별 단위 테스트 케이스 작성
- 토큰 기반 인증 체계 및 jwt 사용

## 실행하기
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) 설치
- ```sh gradle-build.sh``` 명령어 실행
- ```docker-compose up -d``` 명령어 실행
    - ```docker-compose up -d --build``` rebuild 하고자 하는 경우 해당 명령어 사용