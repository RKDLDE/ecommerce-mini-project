package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Category;
import kr.co.javaex.sec23.repository.CategoryRepository;

import java.util.Comparator;
import java.util.List;

public class CategoryService {
    private CategoryRepository categoryRepository = new CategoryRepository();
    private ProductService productService = new ProductService();

    /**
     * 카테고리 전체 조회
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * 카테고리 추가 메서드
     */
    public void addCategory(String name, Long topId, int sortOrder) {
        List<Category> categories = categoryRepository.findAll();

        // 가장 큰 값
        long maxId = 0L;

        for (Category c : categories) {
            // 제일 큰 값 저장
            if (c.getCategoryID() > maxId) {
                maxId = c.getCategoryID();
            }
        }
        // 새 ID
        long nextId = maxId + 1L;

        // 저장
        Category newCategory = new Category(nextId, topId, name, sortOrder);
        categories.add(newCategory);

        categoryRepository.saveAll(categories);
    }

    /**
     * 카테고리 수정 메서드
     */
    public boolean updateCategory(Long targetId, String newName, int newSortOrder) {
        List<Category> categories = categoryRepository.findAll();
        boolean isFound = false;

        for (Category c : categories) {
            if (c.getCategoryID().equals(targetId)) {
                c.setCategoryName(newName);
                c.setSortOrder(newSortOrder);
                isFound = true;
                break;
            }
        }

        if (isFound) {
            categoryRepository.saveAll(categories);
        }
        return isFound;
    }



    /**
     * 입력받은 ID가 실제로 존재하는지
     */
    public boolean isCategoryID(Long categoryId) {
        List<Category> categories = categoryRepository.findAll();
        boolean isFound = false;

        for(Category c : categories){
            if (c.getCategoryID().equals(categoryId)){
                isFound = true;
                break;
            }
        }
        return isFound;
    }

    /**
     * 대분류 확인용
     */
    public boolean isValidTopCategory(Long categoryId) {
        List<Category> categories = categoryRepository.findAll();
        for (Category c : categories) {
            if (c.getCategoryID().equals(categoryId) && c.getTopCategoryID() == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 하위 카테고리 존재하는 지 (삭제 방지용임)
     */
    private boolean hasSubCategories(Long targetId) {
        List<Category> categories = categoryRepository.findAll();
        for (Category c : categories) {
            if (c.getTopCategoryID() != null && c.getTopCategoryID().equals(targetId)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 카테고리 삭제
     * 하위 카테고리가 있으면 삭제 불가
     */
    public int deleteCategory(Long categoryId) {
        List<Category> allCategories = categoryRepository.findAll();

        Category target = null;
        for (Category c : allCategories) {
            if (c.getCategoryID().equals(categoryId)) {
                target = c;
                break;
            }
        }
        if (target == null) return 0; // 카테고리 없음

        // 대분류인데 하위 중분류가 있으면 삭제 방어
        if (target.getTopCategoryID() == null) {
            for (Category c : allCategories) {
                if (c.getTopCategoryID() != null && c.getTopCategoryID().equals(categoryId)) {
                    return -1; // 중분류 먼저 지우라고 알림
                }
            }
        }

        // 삭제 전 상품들을 미분류(0)로 이동
        productService.moveProductsToDefault(categoryId);

        allCategories.remove(target);
        categoryRepository.saveAll(allCategories);
        return 1; // 성공
    }
}