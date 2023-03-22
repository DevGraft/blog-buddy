# Blog Buddy

블로그 포스트를 검색하는 서비스 입니다.

> **📩 다운로드 링크** : [app-blog-buddy.jar](https://github.com/DevGraft/blog-buddy/releases/download/0.0.6/app-blog-buddy.jar)

👀 [Project Board 구경하기](https://github.com/users/DevGraft/projects/1/views/1)

## 📋 1. Spec

**📈 Version**

| Name | Version |
|:---|:---|
|Java | 17|
|Gradle | 7.5 |
|Spring Boot| 2.7.3|

**🌱 의존성 * 오픈소스 사용 목록**

| Source                                                                                                                        | Description                                                      |
|:------------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------|
| [spring-boot-stater-web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)                 | 웹 애플리케이션 스타터 패키지                                                 |
| [spring-boot-stater-data-jpa](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)       | jpa 사용을 위한 의존성                                                   |
| [com.querydsl:querydsl-jpa](https://mvnrepository.com/artifact/com.querydsl/querydsl-jpa)                                     | jpa기반 Querydsl 기능을 제공하는 모듈.<br>해당 모듈을 통해 인기 검색어 목록 기능 개발         |
| [com.querydsl:querydsl-apt](https://mvnrepository.com/artifact/com.querydsl/querydsl-apt)                                     | Querydsl에서 사용하는 어노테이션 프로세서를 적용하기 위한 의존성                          |
| [spring-cloud-starter-openfeign](https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign) | Feign HTTP Client 제공.<br>해당 기능을 통해 kakao, naver 통신 클라이언트 개발      |
| [github.openfeign:feign-okhttp](https://mvnrepository.com/artifact/io.github.openfeign/feign-okhttp)                          | Feign HTTP Client 기본 HTTP(URLConnection)을 대체하기 위한 OkHttp Client. |
| [github.openfeign:feign-jackson](https://mvnrepository.com/artifact/io.github.openfeign/feign-jackson)                        | Feign HTTP Client에서 JSON 데이터 처리를 위한 의존성                          |

## 📬 2. API 명세

### 🔎 블로그 검색

```
$ curl 'http://localhost:8080/search/blog?keyword=kakao?page=1?size=10?sort=accuracy' -i -X GET
```

**요청 정보**

| Parameter | Required | Description       | Forms                      |
|:----------|:---------|:------------------|:---------------------------
| keyword   | true     | 블로그 검색 질의어        | 공백, 빈값 불가능                 |
| page      | false    | 검색된 총 문서 페이지      | 1~50 입력                    |
| size      | false    | 한 페이지에 보일 수 있는 크기 | 1~50 입력                    |
| sort      | false    | 문서 정렬 방법(기본 정확도순) | accuracy=정확도순, recency=최신순 |

**요청 결과 예시**

````json
{
  "success": true,
  "message": "OK",
  "timestamp": "2023-03-22 16:31:33",
  "data": {
    "meta": {
      "totalCount": 4951784,
      "pageableCount": 800,
      "isEnd": false
    },
    "documents": [
      {
        "blogname": "ArtintheCity",
        "title": "Why <b>Love</b> Generative Art?",
        "contents": "https://www.artnome.com/news/2018/8/8/why-love-generative-art Why <b>Love</b> Generative Art? — Artnome Over the last 50 years, our world has turned digital at breakneck speed. No art form has captured this transitional time period - our time period - better than generative art. Generative art takes...",
        "url": "http://artinthecity.tistory.com/80",
        "thumbnail": "https://search2.kakaocdn.net/argon/130x130_85_c/JbBXo5vZRb9",
        "postDate": "2023-03-12"
      }
    ]
  }
}
````

**요청 결과**

| Path                        | Type     | Description                             |
|:----------------------------|:---------|:----------------------------------------|
| success                     | Boolean  | api 요청 성공 여부                            |
| message                     | String   | api 요청 결과 상태 메세지 성공=OK 실패=에러 메세지        |
| timestamp                   | DateTime | api 요청 반환 시간 (포맷 = yyyy-MM-dd hh:mm:ss) |
| data                        | Object   | Api 요청의 결과                              |
| data.meta                   | Object   | 블로그 검색 메타정보                             |
| data.meta.totalCount        | Number   | 총 검색 문서 수                               |
| data.meta.pageableCount     | Number   | 총 검색 문서 수                               |
| data.meta.isEnd             | Boolean  | 마지막 페이지 여부                              |
| data.documents              | Array    | 검색된 문서 목록                               |
| data.documents[i].blogame   | String   | 블로그 명                                   |
| data.documents[i].title     | String   | 문서 제목                                   |
| data.documents[i].url       | String   | 문서 주소                                   |
| data.documents[i].thumbnail | String   | 문서 미리보기 이미지 Url                         |
| data.documents[i].postDate  | Date     | 문서 작성 일자 (yyyy-MM-dd)                   |

---

### 🌟 인기 검색어 목록

```
$ curl 'localhost:8080/search/most-searched-blogs' -i -X GET
```

**요청 결과 예시**

```json
{
  "success": true,
  "message": "OK",
  "timestamp": "2023-03-22 16:18:11",
  "data": {
    "blogs": [
      {
        "keyword": "kakao Landing",
        "count": 17
      }
    ]
  }
}
```

**요청 결과**

| Path                   | Type     | Description                             |
|:-----------------------|:---------|:----------------------------------------|
| success                | Boolean  | api 요청 성공 여부                            |
| message                | String   | api 요청 결과 상태 메세지 성공=OK 실패=에러 메세지        |
| timestamp              | DateTime | api 요청 반환 시간 (포맷 = yyyy-MM-dd hh:mm:ss) |
| data                   | Object   | Api 요청의 결과                              |
| data.blogs             | Array    | 인기 검색어 목록                               |
| data.blogs[i].keyword  | String   | 인기 검색어                                  |
| data.blogs[i].count    | Number   | 인기 검색어 검색 회수                            |

## 🛠️ 3. 빌드

### 🪧 테스트 호출

```shell
./gradlew test
```

### 🏃 실행

**gradlew**
```shell
./gradlew clean
./gradlew build
./gradlew :application:app-blog-buddy:bootRun  
```

**java 실행**
```shell
java -jar app-blog-buddy.jar
```
**java 실행 (jar를 직접 만들 경우)**
```shell
# jar 없을 경우
./gradlew clean
./gradlew bootJar
cd applications/app-blog-buddy/build/libs
# 실행
java -jar app-blog-buddy.jar
```
