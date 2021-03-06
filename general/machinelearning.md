# 기계학습

### 데이터 정규화 (Normalization)
  값의 범위가 크게 다른 특징들을 입력 변수로 사용할 경우 -> 적절한 학습 안될 수 있음  
  각각의 특징들이 갖는 값들의 범위를 적당한 규모로 변경하는 작업  

### 평균제곱오차 (Mean Squared Error)
  오차 확인을 위함 (오차 제곱의 합)  

### 데이터 표준화 (Standardization)
  데이터를 정제할 때 평균과 분산을 이용해서 데이터 분포를 0으로 맞춤  

### 군집화
  k-nn(클래시피케이션, 지도)  
  k-means(클러스터링, 비지도)  

### k-NN 알고리즘
  기존 라벨링된 데이터를 기준으로 근접 k 개 확인 후 다수결로 결정  
  동률의 경우에는 거리로 확정  
  장점: 단순하고 직관적임, 준비 시간이 필요없음  
  단점: 모든 데이터에 대한 정보 필요, 많은 메모리와 계산시간 필요함  

### 표집편향 (샘플링 바이어스)
  표본 데이터가 편향된 경우 좋은 알고리즘을 사용해도 학습성능 안좋은 것  

### 정확도 / 정밀도 / 재현율
  정확도: 제대로 판정한 데이터 / 전체 데이터 의 비율  
  정밀도: 실제 양성비율 -> 양성으로 판단한 것 / 실제 양성 의 비율  
  재현율: 진짜 양성비율 -> 올바르게 양성판단 / (모델이)양성 판단 의 비율  
  재현율은 가로축, 정밀도는 세로축  
  TN FP  
  FN TP  

### F1 점수
  재현율과 정밀도 각각 지표는 하나의 척도로 측정하면 왜곡 발생 가능함  
  정밀도와 재현율의 조화평균을 구한 점수  
  = 쇠렌센-다이스 계수  
  혼동행렬을 만들어서 확인함  

### 앙상블
  머신러닝의 집단지성  
  여러 분류기가 각자 분류 결과를 내놓아 투표하듯 다수결  
  다양성을 확보하는 방법으로 -> 각각 다른 모델의 분류기 만듦, 서로 다른 학습데이터 학습  

### 배깅 기법
  어떤 분류기에 선택 데이터가 다른 분류기 학습에 사용될 수 있으면 배깅 기법  
  불가능하면 -> 페이스팅  
  배깅 기법을 개선한 방법이 부스팅  

### 부스팅
  아다부스트 가장 많이 사용  
  분류기를 순차적으로 학습시키면서 이전 분류기가 제대로 처리하지 못한 데이터의 가중치를 올리고, 다음 예측기는 높은 가중치에 더 집중해서 학습  

### 지도학습 비지도학습
  차이점은 소속 집단에 대한 정보 유무, 레이블의 유무 / 공통점은 집단으로 묶는 것  

### k-means 알고리즘
  k 개의 그룹으로 나누는 알고리즘  
  군집개수 k 를 알아야함  
  데이터분포 사전지식이 없어도 사용 가능함  

### 다항회귀
  데이터가 비선형 방성식 따르는 것에 대한 것  
  다항회귀는 더 좋은 예측성능을 가진 회귀함수를 찾을 수 있게 함  
  다항회귀 차수를 높이면 -> 오버피팅 문제, 계산복잡도 증가 문제  

### 과소적합 시
  입력 데이터 특징을 늘린다.  
  학습모델 차수 등 복잡도를 높인다.  

### 과적합 시
  데이터 특징을 줄인다.  
  학습모델 차수 등 복잡도를 낮춘다. -> 일반화 능력을 높인다. (정칙화 regularization)  
  데이터 량을 늘린다.  

### 결정트리
  질문 트리로 데이터를 분류하는 것  
  어떤 질문(속성)을 선택해야 정보이득이 높은, 트리가 넓게 퍼지고 깊게 안가는 것을 고르는가  
  정보이득 :  
  -> 특정한 속성에 따라 데이터를 나누었을 때 줄어든 엔트로피  
  -> 데이터 속성 중 어느 것이 중요한 것인지 판단하는 기준  
  ID3, CART 두개 많이 쓰임  

### 엔트로피
  정보량을 측정하기 위함  
  0.5가 가장 높고, 0 과 1 이 가장 낮음  

### ID3 알고리즘
  데이터를 쪼갤 때 가장 중요한 정보이득이 가장 큰 속성을 찾아서 결정 트리를 만드는 방법  
  엔트로피 (정보이득) 을 사용  

### CART 알고리즘
  지니불순도 개념을 사용 (ID3 와 유사)  

### SVM 서포트벡터머신
  데이터의 초평면(한차원 아래 평면)으로 분리하는데, 데이터에 닿지 않는 폭이 넓은 것 구함  
  -> 마진이 가장 넓은 것을 구함 (빡쎄면 하드마진, 섞이는거 허용하면 소프트마진)  
  어쨌든 마진을 최대화 하기 때문에 데이터들이 하나씩은 닿아있는데 그게 서포트벡터  
  소프트마진 사용 시 마진 내 가능 데이터 수 제어하는 변수가 슬랙(Slack)  

### 커널트릭 (kernel trick)
  다항특징변환 시 차수가 높아지면 입력 특징벡터 차원이 확 높아짐  
  효율적인 비선형 분류를 위해 커널트릭 사용함 (최적화 문제의 쌍대성)  
  쌍대문제을 통해서 특징 데이터 차원이 증가해도 내적 계산 시간이 증가하지 않는 커널 트릭 존재  
  다항 특징변환이 지나치게 차원 높이는 문제를 해결하려고 커널트릭 사용함  
  커널은: 변환된 두 벡터의 내적을 변환 없이 원래 벡터로 구하는 것  

### 차원의 저주
  차원: 데이터를 다룰 때 데이터를 표현하는 특징의 수  
  높은 차원 데이터를 다룰 때 중요하지 않은 차원 생략하는 차원축소 가 필요함  
  정보 손실을 최소화 하면서 특징의 수를 줄이는 방법이 필요함  
  차원이 높아지면 데이터들 사이 거리가 멀어지면서 데이터 밀도가 낮아짐 -> 데이터 사이의 관계를 파악하기가 힘들어짐 —> 차원의 저주  

### 특징 선택
  차원을 축소할 때 중복이나 불필요한 정보의 차원을 버리고 중요한 차원 선택하는 것  

### 투영
  데이터를 낮은 차원으로 떨어뜨리는 것  
  원본데이터의 분산을 최대한 유지하는 방향으로 투영이 일어나게 하는 것 -> 특징 투영  
  축을 하나 제외해서 가장 간단한 직교투영 가능  

### 주성분 분석 (PCA, Principle Component Analysis)
  가장 좋은 축을 새롭게 찾아 투영을 실시하는 것이 주성분분석  
  데이터의 분산을 (최대화) 가장 잘 유지하는 축들: 주성분  
  평균과 분산을 구해서 분산을 최대화  
  훈련집합의 공분산행렬을 구하고, 그 고유벡터를 구함 -> 고유값 클수록 중요도 큼  
  주성분분석 -> 특이값 분해  

### 특이값분해 (SVD, Singular Value Decomposition)
  특이값분해는 행렬M의 변환을: 회전 -> 크기변경 -> 다시 회전 으로 표현  
  행렬을 공간의 가중합으로 표현해서, 가중치는 특이값인데 특이값이 클수록 중요한 정보담은 공간  
  차원축소: 중요한 차원만큼 중요한 정보를 담고있는 공간을 선택하는 것  

### PCA 왜 하는가?
  데이터과적을 막기 위해 -> 차원을 축소 -> 최대분산 축을 유지하게 끔 줄인다.  
