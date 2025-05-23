# Spring Boot Kotlin Template

## 사용 전 설정 필요

- Database 명
  - [docker-compose](./docker-compose.yml)
  - [db-core local profile properties](./storage/db-core/src/main/resources/db-core.yml)
- Example 제거
  - db-core
  - client
  - core-api
  - core-enum
- 패키지/그룹 명 수정
  - [logging](./support/logging/src/main/resources/logback)
  - [gradle.properties](./gradle.properties)
