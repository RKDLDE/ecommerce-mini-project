package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.Category;
import kr.co.javaex.sec23.service.CategoryService;
import kr.co.javaex.sec23.util.ConsoleUtil;
import java.util.List;

public class CategoryController {
    private CategoryService categoryService = new CategoryService();
    private ConsoleUtil consoleUtil = new ConsoleUtil();

    /**
     * 카테고리 메뉴 보여주기
     */
    public void showMenu() {
        while(true) {
            System.out.println("\n[ 관리자 - 카테고리 관리 ]");
            System.out.println("1. 카테고리 출력 | 2. 카테고리 수정 | 3. 카테고리 추가 | 4. 카테고리 삭제 | 0. 이전 메뉴");
            int choice = consoleUtil.readInt("선택: ");

            switch (choice){
                case 1:
                    printCategory();
                    break;
                case 2:
                    updateCategory();
                    break;
                case 3:
                    addCategory();
                    break;
                case 4:
//                    deleteCategory();
                    break;
                case 0:
                    System.out.println("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    /**
     * 카테고리 출력
     */
    private void printCategory() {
        System.out.println("\n============================= 카테고리 목록 =============================");
        List<Category> categories = categoryService.getAllCategories();

        if (categories.isEmpty()) {
            System.out.println("등록된 카테고리가 없습니다.");
            System.out.println("=========================================================================");
            return;
        }

        for (Category c : categories) {
            // 대분류 먼저 출력
            if (c.getTopCategoryID() == null) {
                System.out.println("[대분류] ID: " + c.getCategoryID() + " | 이름: " + c.getCategoryName() + " | 정렬: " + c.getSortOrder());
                // 해당 대분류의 중분류
                for (Category sub : categories) {
                    if (sub.getTopCategoryID() != null && sub.getTopCategoryID().equals(c.getCategoryID())) {
                        System.out.println("  ㄴ [중분류] ID: " + sub.getCategoryID() + " | 상위ID: " + sub.getTopCategoryID() + " | 상위카테고리: " + c.getCategoryName() + " | 이름: " + sub.getCategoryName());
                    }
                }
            }
        }
        System.out.println("=========================================================================");
    }

    /**
     * 카테고리 수정
     */
    private void updateCategory() {
        printCategory();
        System.out.println("변경할 정보를 입력하세요.");

        long targetId;
        while(true) {
            targetId = consoleUtil.readLong("수정할 카테고리 ID 입력: ");
            if(categoryService.isCategoryID(targetId)) {
                break;
            } else {
                System.out.println("해당하는 카테고리가 없습니다. 다시 입력해주세요.");
            }
        }

        String newName = consoleUtil.readString("새 카테고리명: ");
        int newSortOrder = consoleUtil.readInt("새 정렬 순번: ");

        boolean isUpdate = categoryService.updateCategory(targetId, newName, newSortOrder);

        if(isUpdate) {
            System.out.println("수정되었습니다.");
            printCategory();
        } else {
            System.out.println("수정 중 오류가 발생했습니다.");
        }
    }

    /**
     * 카테고리 추가
     */
    private void addCategory() {
        System.out.println("\n========== 카테고리 추가 ==========");

        String name = consoleUtil.readString("카테고리명: ");
        int sortOrder = consoleUtil.readInt("정렬 순번: ");

        int type = 0;
        while (true) {
            System.out.println("추가할 종류 선택");
            System.out.println("1. 대분류");
            System.out.println("2. 중분류");
            type = consoleUtil.readInt("선택: ");

            if (type == 1 || type == 2) {
                break;
            } else {
                System.out.println("1(대분류) 또는 2(중분류)만 입력해주세요.\n");
            }
        }

        Long topId = null;

        if (type == 2) {
            // 현재 대분류가 있는지...
            if (!printOnlyTopCategories()) {
                System.out.println("등록된 대분류가 없습니다.");
                return;
            }

            while (true) {
                long parentId = consoleUtil.readLong("상위(대분류) 카테고리 ID 입력: ");
                topId = parentId;

                if (categoryService.isValidTopCategory(topId)) {
                    break;
                } else {
                    System.out.println("목록에 있는 대분류 ID를 다시 입력해주세요.");
                }
            }
        }

        categoryService.addCategory(name, topId, sortOrder);
        System.out.println("새 카테고리가 성공적으로 추가되었습니다!");
    }

    /**
     * 대분류만 출력
     */
    private boolean printOnlyTopCategories() {
        System.out.println("\n[ 선택 가능한 대분류 목록 ]");
        List<Category> categories = categoryService.getAllCategories();
        boolean hasTopCategory = false;

        for (Category c : categories) {
            if (c.getTopCategoryID() == null) {
                System.out.println("ID: " + c.getCategoryID() + " | 이름: " + c.getCategoryName());
                hasTopCategory = true;
            }
        }
        return hasTopCategory;
    }

    /**
     * 카테고리 삭제 (예외 처리 적용)
     */
//    private void deleteCategory() {
//        System.out.println("\n========== 카테고리 삭제 ==========");
//        printCategory();
//
//        long targetId = consoleUtil.readLong("삭제할 카테고리 ID 입력: ");
//
//        String confirm = consoleUtil.readString("정말 삭제하시겠습니까? (Y/N): ");
//        if (!confirm.equalsIgnoreCase("Y")) {
//            System.out.println("삭제가 취소되었습니다.");
//            return;
//        }
//
//        try {
//            categoryService.deleteCategory(targetId);
//            System.out.println("✅ 카테고리가 성공적으로 삭제되었습니다.");
//        } catch (IllegalStateException | IllegalArgumentException e) {
//            System.out.println("❌ 삭제 실패: " + e.getMessage());
//        }
//    }
}