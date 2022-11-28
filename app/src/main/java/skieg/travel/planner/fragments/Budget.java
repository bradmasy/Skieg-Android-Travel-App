package skieg.travel.planner.fragments;

/**
 * Budget class.
 */
public class Budget {
    private String id;
    private String item;
    private double amount;

    /**
     * Budget Constructor.
     * @param id the id of the budget item.
     * @param item represents the item in the budget.
     * @param amount the value of that budget item.
     */
    Budget(String id, String item, double amount) {
        this.id = id;
        this.item = item;
        this.amount = amount;
    }

    /**
     * Gets the budget item.
     *
     * @return the budget item.
     */
    public String getItem() {
        return item;
    }

    /**
     * Sets the budget item.
     *
     * @param item represents the budget item.
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * Gets the budgets id.
     *
     * @return the budgets id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the budgets id.
     *
     * @param id the id of the budget.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the budget amount.
     *
     * @return the budget amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the budget amount.
     *
     * @param amount the budget amount we are setting.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
