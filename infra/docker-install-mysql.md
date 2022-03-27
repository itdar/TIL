# Docker 이용해서 MySQL 설치 후 접속

<br>

- m1 실리콘칩 맥북 기준 도커에서 mysql:latest 버전 이미지 다운로드
```shell
docker pull --platform linux/amd64 mysql

## 보통 피씨에서는
# docker pull mysql
```

<br>

- docker-compose 파일 구성
  - 파일명: docker-compose-mysql-8.yml (?)
```shell
version: '3.1'

services:

  db:
    image: mysql
    container_name: testmysql
    ports:
      - "3306:3306"
    command:
      # 인증 플러그인 암호화방식 변경
      - --default-authentication-plugin=mysql_native_password
      # 문자열 인코딩 이모지 포함
      - --character-set-server=utf8mb4
      # 문자열 정렬순서 collation
      - --collation-server=utf8mb4_unicode_ci
    environment:
      MYSQL_ROOT_PASSWORD: "password"
```

<br>

- docker-compose 통해서 이미지 실행
```shell
docker-compose -f docker-compose-mysql-8.yml up -d
```

<br>

- docker 의 mysql 컨테이너 접속
```shell
docker exec -it testmysql bash
```

<br>

- 도커 컨테이너 내에서 mysql 접속 (위 yml 에서 root 패스워드로 설정해두었던 것으로 접속)
```shell
mysql -u root -p
```

<br>

