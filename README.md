### 프로젝트 디렉토리 구조 (Project Structure)

```text
kr.co.javaex.sec23
│
├─ controller (View & Control)
│   │  - MenuManager.java        : 전체 프로그램 흐름 제어 및 권한별 메뉴 분기
│   │  - UserController.java      : 회원가입, 로그인, 정보 수정 인터페이스
│   │  - ProductController.java   : 상품 목록, 상세 보기, 관리자 상품 제어
│   │  - CategoryController.java  : 대분류/중분류 카테고리 관리 인터페이스
│   │  - CartController.java      : 장바구니 관리 및 체크박스 기반 주문 제어
│   └─ OrderController.java       : 주문 내역 확인 및 영수증 출력
│
├─ service (Business Logic)
│   │  - UserService.java         : 유효성 검증(Regex), 로그인/중복 체크 로직
│   │  - ProductService.java      : 상품 CRUD 및 카테고리 삭제 시 미분류 이동 로직
│   │  - CategoryService.java     : 계층형 카테고리 구조 및 유효성 관리
│   └─ CartService.java           : 장바구니 수량 합산 및 선택 상태 토글 로직
│
├─ repository (Data Access)
│   │  - UserRepository.java      : users.json 파일 입출력 및 데이터 관리
│   │  - ProductRepository.java   : products.json 파일 입출력
│   └─ ... (각 도메인별 Repository를 통한 JSON 데이터베이스 구현)
│
├─ domain (Model / VO)
│   │  - User.java, Product.java, Cart.java, Order.java (객체 정의)
│   └─ ProductStatus.java, UserAuth.java (상태값 관리를 위한 Enum)
│
├─ util (Common Utilities)
│   └─ ConsoleUtil.java           : Scanner 버퍼 처리 및 입력 예외 방어 도구
│
└─ Ecommerce.java (Main)          : 프로그램의 Entry Point (실행 클래스)
```
### 상세 메뉴 구조 (Menu Tree)
시스템은 비회원, 일반 회원, 관리자의 3단계 권한에 따라 독립적인 메뉴를 제공합니다.

🟢 LEVEL 1: 초기 접속 (Guest Menu)
1. 로그인: 등록된 이메일/PW 인증 (성공 시 권한에 따라 메뉴 전환)
2. 회원가입:
- ID/PW 정규식 검사 (영문 대소문자, 숫자 포함 5~15자)
- 이메일 및 ID 중복 방지 로직 적용
3. 상품 목록: 현재 판매 중인 전체 상품 리스트 출력
0. 종료: 시스템 안전 종료

🔵 LEVEL 2: 일반 회원 전용 (User Menu)
1. 상품 쇼핑: 상품 목록 → ID 선택 → 상세 페이지 → 장바구니 담기
2. 장바구니:
- 목록 조회 (실시간 체크박스 [ V ] 상태 표시)
- 상품 선택/해제 (Toggle)
- 수량 변경 / 개별 삭제 / 장바구니 비우기 
- 선택 상품 주문: 체크박스가 선택된 항목만 선별 결제 및 영수증 생성
3. 내 정보 수정: 이름, 전화번호, 이메일 업데이트
4. 비밀번호 변경: 기존 비밀번호 검증 후 보안 규칙에 따른 새 비밀번호 적용
9. 로그아웃: 사용자 세션 초기화 및 게스트 메뉴 귀환

🔴 LEVEL 2: 관리자 전용 (Admin Menu)
1. 카테고리 관리:
- 대분류/중분류 추가 및 수정 
- 카테고리 삭제 (삭제 시 해당 상품을 '미분류'로 자동 이동시키는 데이터 보호 로직)
2. 상품 관리:
- 신규 상품 등록 (카테고리 매핑)
- 상품 정보 수정 (이름, 설명, 가격, 재고, 판매상태 제어)
- 상품 데이터 삭제
3. 회원 목록: 시스템에 가입된 모든 유저의 상세 정보 리스트 출력
9. 로그아웃: 관리자 세션 종료