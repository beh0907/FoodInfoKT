# <div align=center>Meal Recipe</div>

[The Meal DB](https://www.themealdb.com/)를 활용한 kotlin 언어 기반의 요리 레시피 제공 안드로이드 어플리케이션입니다.

# 특징

* 세계 요리 레시피 정보 제공
* 레시피 검색 기능
* 즐겨찾는 레시피 저장
* 유튜브 연계 요리 레시피 영상 제공

# 기술스택 및 라이브러리

* 최소 SDK 24 / 타겟 SDK 34
* kotlin 언어 기반, 비동기 처리를 위한 coroutine + LiveData
* 종속성 주입을 위한 [Dagger Hilt](https://dagger.dev/hilt/)
* JetPack
  * LifeCycle - Android의 수명 주기를 관찰하고 수명 주기 변경에 따라 UI 상태를 처리합니다.
  * ViewModel - UI와 DATA 관련된 처리 로직을 분리합니다.
  * ViewBinding - View(Compose)와 코드(kotlin)간의 상호작용을 원활하게 처리합니다.
  * Navigation - fragment 화면간의 이동을 처리하고 데이터 전달을 관리합니다.
  * Room - 로컬 DB SQLite를 추상화한 라이브러리로 뉴스 북마크 목록을 관리합니다.
* Architecture
  * MVVM 패턴 적용 - Model + View + ViewModel
  * Repository 패턴 적용
* [Retrofit2](https://github.com/square/retrofit) - The Meal DB API로부터 요리 레시피 정보를 요청하고 처리합니다.
* [Glide](https://github.com/bumptech/glide) - 효율적으로 이미지를 로드하고 적용합니다.

# 스크린샷

| 메인화면-1 | 메인화면-2 | 검색 화면 |
|---|---|---|
|![메인화면-1](https://github.com/user-attachments/assets/ee33beaa-8739-4667-8040-7eb72126c810)|![메인화면-2](https://github.com/user-attachments/assets/d9d8a3d9-8384-4429-9358-3e66b2753a3a)|![검색 화면](https://github.com/user-attachments/assets/17be2f86-e1a5-4684-a281-2db23b0698f5)|


|즐겨찾기 화면| 카테고리 화면                                                                                     | 레시피 화면                                                                                      |
| ------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------- |--------------------------------------------------------------------------------------------|
|![즐겨찾기 화면](https://github.com/user-attachments/assets/38e8f605-77fa-4e3e-a306-5979e71cfe32)|![카테고리 화면](https://github.com/user-attachments/assets/b36ee9a0-6423-40f8-a25c-d18896766158)|![레시피 화면](https://github.com/user-attachments/assets/18cf003e-4c49-4e73-bde7-4af25202daef)|
