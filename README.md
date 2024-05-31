# flow-assignment
확장자 판별 과제

# 개발 환경
```
SpringBoot 2.7 , Java 11, MariaDB, Maven, IntellJ, GIT, SourceTree

개발서버: localhost:9098
운영서버: https://54.180.45.23:9098
```

# Welcome API 실행 (health-check)

```
$ curl ${context}:9098/flow
>>> Welcome Json
```

# 디렉토리 구조
``` 
[PROJECT]
├─main
│  ├─java
│  │  ├─madras
│  │  │  └─flow
│  │  │      └─assignment
│  │  │          ├─common
│  │  │          │   ├─aop 
│  │  │          │   ├─config 
│  │  │          │   ├─enums
│  │  │          │   ├─exception
│  │  │          │   │   ├─custom
│  │  │          │   │   ├─dto
│  │  │          │   │   └─handler
│  │  │          │   └─util
│  │  │          │   
│  │  │          ├─extension
│  │  │          │   ├─all 
│  │  │          │   │  ├─controller
│  │  │          │   │  ├─dto
│  │  │          │   │  ├─repository
│  │  │          │   │  └─service
│  │  │          │   │  
│  │  │          │   ├─custom 
│  │  │          │   │  ├─controller
│  │  │          │   │  ├─dto
│  │  │          │   │  ├─repository
│  │  │          │   │  └─service
│  │  │          │   │  
│  │  │          │   └─fix 
│  │  │          │      ├─controller
│  │  │          │      ├─dto
│  │  │          │      ├─repository
│  │  │          │      └─service
│  │  │          │     
│  │  │          │     
│  │  │          │     
│  │  │          │     
│  │  │          └─welcome
│  │  │              └─controller
│  │  │              
│  │  └─resources
│  │      ├─log 
│  │      ├─sql
│  │      └─static
│  │ 
│  │      apllication-${spring.profiles.active}.propertis
│  └─test
│      └─java
│          └─madras
│              └─flow
│                  └─assignment
│                       └─ [상단 서비스]
│                           └─ repository
│                           └─ service
```

# 서비스 구조
```
WEB (apache 2.4) - Client 정적 리소스
 │
 ├─ AJP CONNECTION
 │
WAS (tomcat 9) - Server 리소스

```

# 빌드 및 배포
```
$ git pull origin prd

$ mvn clean [install or package]

$ java -jar ./target/assignment-0.0.1-SNAPSHOT.jar                            
```

# 개발 원칙
```
1. 코드 작성 시 madras.flow.assignment 하위에 패키지 만들어 진행합니다. 
ex) welcome 관련 기능은  madras.flow.assignment.welcome

2. 특정 기능 내에 controller, dto, repository, service 패키지를 만들어 역할을 관리하고, 
파일 구조는 다음과 같이 정의합니다.
ex) madras.flow.assignment.extension.all.controller
=> AllExtensionController
madras.flow.assignment.extension.all.service 
=> AllExtensionService
madras.flow.assignment.extension.all.service.impl
=> AllExtensionServiceImpl
madras.flow.assignment.extension.all.dto 
=> AllExtensionReqDto
=> AllExtensionResDto
madras.flow.assignment.extension.all.repository 
=> AllExtensionRepository

3. 기능들에 꼭 설명과 각주를 달아줍시다.
ex) 
title :
description :
reference :
author :
date :
```