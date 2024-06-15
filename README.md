![image](https://github.com/Artecrowd/Artecrowd_Android/assets/127479677/a3211ff2-2c99-4a44-9f7b-9b92d938937c)

### Introduce

안녕하세요!
가천대학교 아르테크네는 학생들이 자주 이용하는 공용 학습 공간입니다. 
<br>본 프로젝트는 아르테크네의 혼잡도를 실시간으로 감지하여 사용자들에게 제공하는 서비스를 개발하는 것을 목적으로 합니다.
<br>이 서비스는 혼잡도를 영상 감지를 통해 분석하고, 이를 어플리케이션을 통해 사용자에게 시각적으로 전달합니다.</br>

### Title

##### Artecrowd

가천대학교 아르테크네 공용 학습 공간의 혼잡도를 실시간으로 감지하여 사용자들에게 제공하는 서비스입니다. 
이 서비스는 CCTV 영상을 분석하여 현재의 혼잡도를 파악하고, 이를 모바일 어플리케이션을 통해 사용자에게 실시간으로 전달합니다. 
이를 통해 사용자는 학습 공간의 혼잡도를 미리 확인하고, 최적의 학습 환경을 선택할 수 있습니다.

### Main Function

+ 실시간 영상 감지
  + 학습 공간의 CCTV 영상을 실시간으로 분석하여 현재의 혼잡도를 파악합니다.
+ 혼잡도 분석
  + 딥러닝 알고리즘을 사용하여 영상 속 사람의 수를 계산하고 혼잡도를 분석합니다.
+ 어플리케이션 연동
  + 분석된 혼잡도를 모바일 어플리케이션을 통해 사용자에게 제공합니다.
+ 혼잡도 시각화
  + 어플리케이션 내에서 혼잡도를 그래프 및 색상 코드로 직관적으로 시각화합니다.
 
### System Configuration

+ 영상 입력
  + 학습 공간에 설치된 CCTV 카메라
+ 서버
  + 영상 데이터 수신 및 분석, 결과 데이터 전송
+ 딥러닝 모델
  + 사람 수를 세기 위한 객체 감지 모델 (예: YOLO, SSD)
+ 모바일 어플리케이션
  + 사용자에게 혼잡도 정보를 제공하는 클라이언트 앱 (Android 지원)
 
### Architecture & Database

<img src="https://github.com/Artecrowd/Artecrowd_Android/assets/127479677/563f917f-c6c7-402d-a7ae-73b31b32388c" width="70%" height="50%" align="center">

<img src="https://github.com/Artecrowd/Artecrowd_Android/assets/127479677/412844f9-7122-487d-822f-f027f147745b" width="20%" height="10%" align="right">


### Use Example

+ 어플리케이션 주요 페이지

<img src="https://github.com/Artecrowd/Artecrowd_Android/assets/127479677/d1480edc-3a7f-486d-a730-94a79b2ce10a" width="37.5%" height="47.5%" align="center">

<img src="https://github.com/Artecrowd/Artecrowd_Android/assets/127479677/50c2d4d8-c4fb-43f4-9ccb-42223ebb051c" width="35%" height="45%" align="center">
<br></br>

  + 아르테크네 위치 선택
  + 해당 아르테크네에 대한 자리 도면과 자리 사용 유무 표시
  + 아르테크네의 혼잡도를 바로 표시

