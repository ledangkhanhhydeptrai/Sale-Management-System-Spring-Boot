package com.example.salemanagement.Enum;

public enum PlanType {

    FREE(50, 1, 2),
    BASIC(500, 3, 10),
    PRO(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

    private final int maxProducts;
    private final int maxWarehouses;
    private final int maxStaff;

    PlanType(int maxProducts, int maxWarehouses, int maxStaff) {
        this.maxProducts = maxProducts;
        this.maxWarehouses = maxWarehouses;
        this.maxStaff = maxStaff;
    }

    public int getMaxProducts() {
        return maxProducts;
    }

    public int getMaxWarehouses() {
        return maxWarehouses;
    }

    public int getMaxStaff() {
        return maxStaff;
    }
}
