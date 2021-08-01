# 네트워크 망 구성에 관하여

- VPC 생성 (클래스 설정, CIDR 설정)
  - Subnet 생성
    - 용도에 맞도록.. AZ 분리, 몇개씩 할당해줄지 설정
  - Route table 확인
  - Internet Gateway 생성, 연결 (모르는 ip 전체를 받을 곳)  
   -> 이 후 aws 에서 관리해주어서 신경 안써도 됨
  - Routing table 에서 routes 수정  
   -> local 로 받을 destination 기본으로 있고,  
   -> 나머지들 다 internet gateway 로 받도록 설정 (0.0.0.0/0 을 위에 만든 igw 로 연결)  
  - Routing table 에서 subnet 연결 편집  
   -> 위에서 만든 subnet 중, 해당 라우팅테이블에 넣고 싶은 subnet 들을 연결해준다.  
    -> (e.g. public / private / internal 등)
   - 라우팅테이블 나누고 싶은 만큼 반복해서 위의 subnet 들을 연결해준다.

> VPC는 리젼을 다르게 설정

- VPC 안에 Subnet 생성 (외부망, 내부망, 관리망 등)
- Subnet 들이 외부와 연결 될 Gateway 설정
- Route table 설정
- 각 Subnet에 적용할 Security group 설정


## 관리망에 들어갈 베스쳔 서버에 관하여 (Bastion)
- 모든 서버에 동일한 보안 수준을 맞추기 어려움.  
- 악성 루트킷, 랜섬웨어 등에서 피해를 보아도, 베스쳔 서버만 재구성하면 됨
- 서비스의 정상 트래픽과 관리자용 트래픽을 구분할 수 있다. (DDoS 공격 등에서 대역폭을 다 차지해도 별도 경로로 접속 가능)

### Reverse Proxy
- 베스쳔 서버에 설치해서 입구를 하나로 만들고 요청과 분산하고 응답을 해줄 수 있다.  
- WAS는 비지니스 로직만 담당하도록 구성하기 위해서. TLS와 같은 부수 기능으로 애플리케이션 영향 주기 싫음

> 통상의 Proxy Server는 LAN -> WAN 요청을 대리로 수행함  
>
> Reverse Proxy는 WAN -> LAN 요청을 대리함

#### Reverse Proxy와 Load Balancer는 어떤 차이?
- Reverse Proxy : 보안성 향상, 확장성 향상, 웹 가속(압축/SSL 처리로 백엔드 리소스 확보/캐싱)
- Load Balancer : 부하분산, 서버상태 체크, 세션 관리

