package kr.co.javaex.sec23.domain;

public class Category {
    private Long categoryID;        // 카테고리ID
    private Long topCategoryID;     // 상위 카테고리 ID
    private String categoryName;    // 카테고리명
    private int sortOrder;          // 정렬순번?

    public Category() {
    }

    public Category(Long categoryID, Long topCategoryID, String categoryName, int sortOrder) {
        this.categoryID = categoryID;
        this.topCategoryID = topCategoryID;
        this.categoryName = categoryName;
        this.sortOrder = sortOrder;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public Long getTopCategoryID() {
        return topCategoryID;
    }

    public void setTopCategoryID(Long topCategoryID) {
        this.topCategoryID = topCategoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
