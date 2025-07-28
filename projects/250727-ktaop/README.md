# Kotlin 으로 Spring AOP 극복하기

## Spring AOP 아쉬운 점

1. 구현의 번거로움
   - Advice 클래스 정의
   - 익숙하지 않은 Pointcut 표현식
     - Pointcut 표현식으로 작성한 AOP가 실제 잘 적용되었는지 가시적으로 확인도 필요
2. 내부 함수 호출 적용 불가
3. 런타임 예외 발생 가능성
   - 문자열로 작성하게 되는 Pointcut 표현식
   - Advice 에서 JoinPoint의 인자를 Array<Any>로 꺼내오는 방식
   - @Cacheable 어노테이션에서 문자열 SpEL 을 통해 key 주입

## 내 의견

StopWatch 는 기존의 AOP 방식이 더 좋아보임

- 해당 기능은 실행되는 함수가 어떤 클래스의 어떤 함수인지 정보를 같이 보여줄 필요가 있다고 생각

전역 함수 방식의 테스트 불편

- 테스트 용이성
- 기존의 어노테이션 방식은 스프링 컨텍스트가 없으면 동작하지 않음 -> 단위 테스트 용이
- 전역 함수 방식은 스프링 컨텍스트가 없으면 예외가 발생하게 됨
- 의존성 주입 방식으로 사용하거나 혹은 스프링 컨텍스트가 없어도 예외가 발생하지 않는 방식이 더 좋아보임

트랜잭션 기능을 해당 내용에서 나온 것처럼 Kotlin AOP 로 구현하는 것이 좋을까?

- 단순히 스프링의 `TransactionTemplate` 을 사용할 수도 있어보임
- 글에서 말하는 의존성 주입의 불편함을 생각한다면 Kotlin AOP 가 더 편리하긴 함

## 활용

- 트랜잭션
- 캐싱
- 예외 스킵
- 인증

## 참고

- [카카오페이 기술 블로그: Kotlin으로 Spring AOP 극복하기!](https://tech.kakaopay.com/post/overcome-spring-aop-with-kotlin/)
