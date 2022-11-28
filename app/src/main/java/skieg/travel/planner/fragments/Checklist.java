package skieg.travel.planner.fragments;

/**
 * Checklist class.
 */
public class Checklist {
    private String item;
    private String id;
    private boolean isChecked;

    /**
     * Constructor for checklist.
     */
    Checklist() {
        item = "";
        id = "";
        isChecked = false;
    }

    /**
     * Constructor for checklist.
     * @param id
     */
    Checklist(String id) {
        this.id = id;
    }

    /**
     * Constructor for checklist.
     *
     * @param item represents the checklist item
     * @param id the id of the checklist item.
     * @param isChecked a boolean representing if the item is checked or not.
     */
    Checklist(String item, String id, boolean isChecked) {
        this.item = item;
        this.id = id;
        this.isChecked = isChecked;
    }

    /**
     * Gets the checklist item.
     *
     * @return returns the checklist item.
     */
    public String getItem() {
        return item;
    }

    /**
     * Sets the checklist item.
     *
     * @param item the item we are setting.
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * Gets the id of the checklist item.
     *
     * @return the id of the checklist item.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the checklist item.
     *
     * @param id the id of the checklist item.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the boolean for if the checklist item has been checked.
     *
     * @return a boolean representing the status of the checklist item.
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * Sets the checklist items is checked value.
     *
     * @param checked a boolean representing if the item is checked.
     */
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
