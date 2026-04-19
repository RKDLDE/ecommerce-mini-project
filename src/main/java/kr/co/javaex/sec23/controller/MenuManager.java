package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.domain.UserType;
import kr.co.javaex.sec23.util.ConsoleUtil;

public class MenuManager {
    // 현재 로그인한 사용자 정보
    private User currentUser = null;

    private UserController userController = new UserController();
    private ProductController productController = new ProductController();
    private CategoryController categoryController = new CategoryController();
    private CartController cartController = new CartController();
    private OrderController orderController = new OrderController();

    private ConsoleUtil consoleUtil = new ConsoleUtil();

    /**
     * 프로그램 시작
     */
    public void start() {
        System.out.println("=========================================");
        System.out.println("       ️⌨️도은샵에 오신 것을 환영합니다⌨️       ");
        System.out.println("=========================================");

        while (true) {
            // 권한에 따른 메뉴 출력
            if (currentUser == null) {
                showGuestMenu();
            } else if (currentUser.getUserType() == UserType.ADMIN) {
                showAdminMenu();
            } else {
                showUserMenu();
            }
        }
    }

    /**
     * 비회원 메뉴
     */
    private void showGuestMenu() {
        System.out.println("\n[ 게스트 메뉴 ]");
        System.out.println("1. 로그인 | 2. 회원가입 | 3. 상품 목록 보기 | 0. 종료");
        int choice = consoleUtil.readInt("선택: ");

        switch (choice) {
            case 1:
                currentUser = userController.login(); // 로그인 성공 시 유저 정보 저장
                break;
            case 2:
                userController.signUp();
                break;
            case 3:
                 productController.printProduct();
                break;
            case 0:
                System.out.println("프로그램을 종료합니다.");
                System.exit(0);
            default:
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
        }
    }

    /**
     * 관리자 메뉴
     */
    private void showAdminMenu() {
        System.out.println("\n[ 관리자 메뉴 ]");
        System.out.println("1. 카테고리 관리 | 2. 상품 관리 | 3. 회원 출력 | 4. 내 정보 수정 | 5. 비밀번호 변경 | 9. 로그아웃 | 0. 종료");
        int choice = consoleUtil.readInt("선택: ");

        switch (choice) {
            case 1:
                 categoryController.showMenu();
                break;
            case 2:
                 productController.showMenu();
                break;
            case 3:
                userController.printAllUsers();
                break;
            case 4:
                userController.updateInfo(currentUser);
                break;
            case 5:
                userController.updatePw(currentUser);
                break;
            case 9:
                logout();
                break;
            case 0:
                System.out.println("프로그램을 종료합니다.");
                System.exit(0);
            default:
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
        }
    }

    /**
     * 일반 사용자 메뉴
     */
    private void showUserMenu() {
        System.out.println("\n[ 회원 메뉴 ]");
        System.out.println("1. 상품 쇼핑 | 2. 장바구니 | 3. 내 정보 수정 | 4. 비밀번호 변경 | 9. 로그아웃 | 0. 종료");
        int choice = consoleUtil.readInt("선택: ");

        switch (choice) {
            case 1:
                 productController.showShoppingMenu(currentUser);
                break;
            case 2:
                 cartController.showMenu(currentUser);
                break;
            case 3:
                userController.updateInfo(currentUser);
                break;
            case 4:
                userController.updatePw(currentUser);
                break;
            case 9:
                logout();
                break;
            case 0:
                System.out.println("프로그램을 종료합니다.");
                System.exit(0);
            default:
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
        }
    }

    /**
     * 로그아웃
     */
    private void logout() {
        currentUser = null; // 로그인 유저 정보 초기화
        System.out.println("성공적으로 로그아웃 되었습니다.");
    }
}