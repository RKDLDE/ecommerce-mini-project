package kr.co.javaex.sec23.domain;

public class Category {
    private String categoryID;       // 카테고리ID
    private String topCategoryID;   // 상위 카테고리 ID
    private String categoryName;    // 카테고리명
    private int sortOrder;          // 정렬순번?

    public Category() {
    }

    public Category(String categoryID, String topCategoryID, String categoryName, int sortOrder) {
        this.categoryID = categoryID;
        this.topCategoryID = topCategoryID;
        this.categoryName = categoryName;
        this.sortOrder = sortOrder;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getTopCategoryID() {
        return topCategoryID;
    }

    public void setTopCategoryID(String topCategoryID) {
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
