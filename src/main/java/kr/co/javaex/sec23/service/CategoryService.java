package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Category;
import kr.co.javaex.sec23.repository.CategoryRepository;

import java.util.Comparator;
import java.util.List;

public class CategoryService {
    private CategoryRepository categoryRepository = new CategoryRepository();

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
     * 카테고리 삭제
     */


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
     * 입력받은 ID가 존재하며, 동시에 대분류(상위ID가 null)인지 확인
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
     * 특정 카테고리를 부모로 두고 있는 하위 카테고리가 존재하는지 확인 (내부용)
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
}