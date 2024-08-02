# ALREM

<br><br>

## 1. Description
#### 알람 시스템
* 수면 주기에 최적화된 알람 앱입니다.
* 기본적인 알람 기능을 제공하고 음성으로 시간 안내를 도와줍니다.
* 반복으로 설정 가능하여 계속 알람이 울릴 수 있도록 합니다.

<br>

#### REM 수면 주기 기반 알람
* 사용자의 REM 수면 주기에 맞춰 최적의 기상시간을 추천해 줍니다.
* 개인화된 수면 패턴에 따른 알람 설정이 가능합니다.

<br>

#### 수면 데이터 시각화
* 사용자의 수면 시간을 기록합니다.
* 직관적인 그래프로 수면 패턴 분석을 제공합니다.

<br><br><br><br>

## 2. Commits

### 1. First Commit ( MultiModules, Version Catalog, DB Schema )
* 클린 아키텍쳐 기반 멀티 모듈화(레이어 구성)<br><br>
* version catalog를 이용해 gradle 라이브러리 관리<br><br>
* Room 라이브러리 이용해 DB 구성<br><br>

<br>

### 2. Second Commit ( UI Design )
* MainScreen UI 프로그래밍<br><br>
* AlarmAddScreen UI 프로그래밍<br><br>

<br>

### 3. Third Commit ( UI Design, implement functions, bug fixes )
* AlarmAddScreen 세부 기능 개발<br><br>
* 여러 SettingItem 기능 개발<br><br>
* hilt와 hiltViewmodel을 이용하여 세팅 값 저장하기<br><br>

### MusicScreen UI 및 기능 구현<br>
* 권한 설정<br><br>
* 앱, 시스템 및 내부 저장소 음악 목록<br><br>

### 오류 개선<br>
* 예외 발생 오류 개선<br><br>

<br>

### 4. Fourth Commit ( SQL DB, bug fixes )
* alarm 정보 DB에 저장 기능 개발<br><br>
* alarm 정보 update 및 delete 기능 개발<br><br>
* alarmAddScreen 기능 버그 수정<br><br>

<br>

### 5. Fifth Commit ( UI Design, implement functions )
* RemScreen 및 여러 Layout UI Design<br><br>
* Rem 시간에 맞춰 시간 계산 기능 개발<br><br>

<br>

### 6. Sixth Commit ( UI Design, implement functions )
* RemLayout의 BarChart 및 Graph UI 개발<br><br>
* PreferencesScreen 기능 추가<br><br>

<br>

### 7. Seventh Commit ( UI Design, implement functions, bug fixes, optimization )
* 알람 화면 개발<br><br>
* 알람 및 푸시 알림 기능 개발<br><br>
* 푸시 알림에 대한 이슈 대응<br><br>
* 데이터를 활용해 그래프 적용<br><br>
* 그 외 데이터 처리 최적화<br><br>

<br>

### 8. Eighth Commit ( UI Design, implement functions, bug fixes, optimization )
* 온 보딩 화면 개발<br><br>
* 권한 설정 리팩토링<br><br>
* 버그 수정<br><br>
* UI 수정<br><br>

<br>

### 9. Version 1.0.0 ( release, proguard )
* release 모드 변경<br><br>
* proguard-rules.pro 작성<br><br>


<br>

### 10. Version 1.0.1 ( bug fixes )
* 진동 버그 수정<br><br>
* 알림 기능 수정<br><br>

<br>

### 11. Version 1.0.2 ( bug fixes )
* 권한 삭제<br><br>
* 알람 버그 수정<br><br>

<br>

### 12. Version 1.0.3 ( bug fixes )
* 알람 설정 안전성 개선<br><br>

<br>

### 13. Splash API & Contents Label
* 구글 플레이 안정성 및 호환성 문제 수정<br><br>

<br><br>

## 3. Multi Modules ( MVVM Clean Architecture ) & Design
| Feature | Image |
|:--:|:--:|
| Modules ( Architecture ) | <img src="https://github.com/user-attachments/assets/b7bef1a9-f59a-496e-b19f-c7f0203ff6de" alt="App Architecture Diagram" width="700"/> |
| Design | <img src="https://github.com/user-attachments/assets/8e19e0bd-6035-4deb-a91b-a36cf62bd2a3" alt="ALREM Figma" width="700"/> |


<br><br>


## 4. UI & Description
| <div align="center">UI</div> | <div align="center">Description</div> |
|:--|:--|
| <table><tr><td><img src="https://github.com/user-attachments/assets/12b2a7c0-ae76-4b2f-9ad3-31b715e1cdf4" alt="Screenshot_20240716_162031_ALREM" width="100"/></td><td><img src="https://github.com/user-attachments/assets/24c89952-44e9-4fc6-8b90-0d86e0510b45" alt="Screenshot_20240716_162036_ALREM" width="100"/></td><td><img src="https://github.com/user-attachments/assets/461cb5eb-5d2c-4700-954d-68475946c745" alt="Screenshot_20240716_162040_ALREM" width="100"/></td></tr></table> | ALREM을 사용하기 전 기본적인 내용을 가이드 합니다. <br><br> ALREM을 이용하기 전 사전에 권한을 설정할 수 있도록 합니다. |
| <table><tr><td><img src="https://github.com/user-attachments/assets/a6d57811-0d75-41f7-8113-8ed0ba02bb52" alt="Screenshot_20240712_143834_ALREM" width="100"/></td><td><img src="https://github.com/user-attachments/assets/448c6ed7-b61d-4766-b361-c262380a5c61" alt="Screenshot_20240712_144534_ALREM" width="100"/></td><td><img src="https://github.com/user-attachments/assets/07386dad-7955-4706-9a6e-5d8b69e3be97" alt="Screenshot_20240712_183943_ALREM" width="100"/></td></tr></table> | ALREM의 메인 화면입니다. <br><br> 사용자는 알람 목록과 수면 시간 기록을 확인할 수 있습니다. |
| <table><tr><td><img src="https://github.com/user-attachments/assets/8f331d72-007f-459f-ad61-2045a9cd179b" alt="Screenshot_20240712_143719_ALREM" width="100"/></td><td><img src="https://github.com/user-attachments/assets/edec3461-4cb8-42f1-a72e-9e6eac707763" alt="Screenshot_20240717_162837_ALREM" width="100"/></td><td><img src="https://github.com/user-attachments/assets/40da99be-fd62-4fb6-831b-d0c83f170223" alt="Screenshot_20240717_162903_ALREM" width="100"/></td></tr></table> | 사용자는 여러 알람 기능을 선택하여 알람을 생성할 수 있도록 합니다. <br><br> 기본적으로 제공되는 앱 음악 이외에도 시스템 음악 자신의 내부 저장소에 있는 음악을 사용할 수 있도록 합니다. |
| <table><tr><td><img src="https://github.com/user-attachments/assets/ffe57441-8c69-4de9-a469-4e50b7992464" alt="Screenshot_20240712_183958_ALREM" width="100"/></td><td><img src="https://github.com/user-attachments/assets/41ffd37f-5718-4df8-93da-87d89e7c6ac2" alt="Screenshot_20240712_183954_ALREM" width="100"/></td></tr></table> | 사용자는 REM 수면 주기를 고려하여 최적의 기상시간을 선택하고 알람으로 설정할 수 있도록 합니다. |

<br><br>

## 5. Sources
* 폰트 : [FontSource](https://www.clipartkorea.co.kr/)
* 아이콘 : [Sun](https://www.flaticon.com/kr/authors/rizky-maulidhani)
* 아이콘 : [Moon](https://www.freepik.com/)




