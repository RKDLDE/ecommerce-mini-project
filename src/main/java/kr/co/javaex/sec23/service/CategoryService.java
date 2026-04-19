package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Category;
import kr.co.javaex.sec23.repository.CategoryRepository;

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
        categoryRepository.save(name, topId, sortOrder);
    }

    /**
     * 카테고리 수정 메서드
     */
    public boolean updateCategory(Long targetId, String newName, int newSortOrder) {
        return categoryRepository.update(targetId, newName, newSortOrder);
    }



    /**
     * 입력받은 ID가 실제로 존재하는지
     */
    public boolean isCategoryID(Long categoryId) {
        return categoryRepository.findById(categoryId) != null;
    }

    /**
     * 대분류 확인용
     */
    public boolean isValidTopCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId);
        return category != null && category.getCategoryTopId() == null;
    }

    /**
     * 하위 카테고리 존재하는 지 (삭제 방지용임)
     */
    private boolean hasSubCategories(Long targetId) {
        return categoryRepository.countByTopId(targetId) > 0;
    }


    /**
     * 카테고리 삭제
     * 하위 카테고리가 있으면 삭제 불가
     */
    public int deleteCategory(Long categoryId) {
        Category target = categoryRepository.findById(categoryId);
        if (target == null) return 0; // 카테고리 없음

        // 대분류인데 하위 중분류가 있으면 삭제 방어
        if (target.getCategoryTopId() == null) {
            if(hasSubCategories(categoryId)){
                return -1;// 중분류 먼저 지우라고 알림
            }
        }

        // 삭제 전 상품들을 미분류로
        productService.moveProductsToDefault(categoryId);
        categoryRepository.deleteById(categoryId);
        return 1; // 성공
    }
}