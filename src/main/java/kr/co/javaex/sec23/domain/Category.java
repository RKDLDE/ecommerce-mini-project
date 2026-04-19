package kr.co.javaex.sec23.domain;

public class Category {
    private Long categoryId;        // 카테고리ID
    private String categoryName;    // 카테고리명
    private Long categorySort;      // 정렬순번?
    private Long categoryTopId;     // 상위 카테고리 ID

    public Category() {
    }

    public Category(Long categoryId, String categoryName, Long categorySort, Long categoryTopId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categorySort = categorySort;
        this.categoryTopId = categoryTopId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategorySort() {
        return categorySort;
    }

    public void setCategorySort(Long categorySort) {
        this.categorySort = categorySort;
    }

    public Long getCategoryTopId() {
        return categoryTopId;
    }

    public void setCategoryTopId(Long categoryTopId) {
        this.categoryTopId = categoryTopId;
    }
}
