Nextstep 우테캠 프로 인프라 관련 교육 중 부분을 정리

[https://edu.nextstep.camp/](https://edu.nextstep.camp/)

클라우드 서비스를 사용하는 것에 대하여.. 조금 지나서 다시 읽어보니 이해되는 내용이 더 많아서 정리하기 좋았음

블로그에서도 볼 수 있음 -> https://itdar.tistory.com/


# Docker 컨테이너에 관하여

### 우선 컨테이너란?
- Servlet container, IoC container, Bean container 등  
  -> 각 무엇인가의 라이프사이클을 관리함  
  -> 생성 - 운영 - 제거 까지 생애주기를 관리하는 것은 각각의 격리가 필요하다.  

#### Docker container는 무엇을 해결하려고 나왔나?
```
** 기존에는.....


HW장비 마련하고,		(OS 가상화로 해결 가능)

OS 설치하고,		(OS 가상화로 해결 가능)

부팅하고,

각종 설정들을 하고,

애플리케이션 코드 받아 빌드,

실행
```


## 컨테이너는, Process를 관리한다.
### 그렇다면 Process란?
```
  -> 실행 중인 프로그램 (프로그램이 OS에 의해 실행되는 단위)
  -> 프로그램의 명령과 실행 시 필요한 정보 조합의 객체

  HDD, SSD 등 저장장치의 프로그램을 실행하면
  -> 실행할 때의 명령과 실행에 필요한 데이터를 메모리에 적재한다.
```
프로세스는..  

1개 CPU는 동시에 1개 프로세스 작업 가능 (20ms 정도 작업 후 대기하는 다른 프로세스 작업 수행)    
  컨테이너는 프로세스가 생성-운영-제거 라이프사이클을 관리함. (격리가 필요하다!)  
  컨테이너 안의 프로세스는 제한된 자원 내에 제한된 사용자만 접근 가능  

### 가상 머신과 컨테이너의 차이는?
>  가상머신은 하이퍼바이저 위에 게스트OS를 실행해서 격리함  
  컨테이너는 OS위에 컨테이너 엔진 위에서 각 컨테이너로 격리됨

### 그렇다면 Host OS는 컨테이너를 어떻게 바라보는가?
> 직접 실행하나, 컨테이너 안에서 실행하나 결국 똑같은 프로세스로 바라본다.

### 그래서 컨테이너를 사용하는 이유는?
> 컨테이너 단위로 사고하고, 각 프로세스의 라이프사이클을 관리하기 위해서이다.

### 그러면 컨테이너 사이의 네트워크는??
>  컨테이너 생성시 veth, 컨테이너 내에는 ethO가 있다.  
  가운데에 dockerO (gateway) 를 통해서 컨테이너들 간에 veth - ethO 까지 통신 가능

### 외부의 요청을 컨테이너에 물리기는 어려워 보이는데?
>  포트포워딩을 사용한다.  
  컨테이너를 시작할 때, OS포트번호:컨테이너포트번호 를 포트포워딩 해준다.

### 그래서 컨테이너를 사용하는 방법은?
>  명령어를 입력해서 Docker daemon 의 API를 호출해서 쓴다.

### Docker image란?
> 도커는 여러 레이어를 합쳐 (Union) 파일 시스템을 이룬다.  
union file system 인데, git commit 들이 모여서 애플리케이션 코드를 이루듯, docker commit 들이 모여 컨테이너를 이룬다.  
컨테이너도 빌드해서 실행한다 (애플리케이션 코드 빌드해서 실행하듯)
> 
> 컨테이너를 제거하면 메모리에서 사라짐 (읽기 전용)  
-> 영속적인 데이터를 다룰 수 없음
> 
> ** 컨테이너 자체는 stateless 하게 만들고, 상태를 정하는 데이터는 외부 파일 시스템에서 제공받도록 구성해야 한다.





