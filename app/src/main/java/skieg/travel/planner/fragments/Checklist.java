package skieg.travel.planner.fragments;

public class Checklist {
    private String item;
    private String id;
    private boolean isChecked;

    Checklist() {
        item = "";
        id = "";
        isChecked = false;
    }

    Checklist(String id) {
        this.id = id;
    }

    Checklist(String item, String id, boolean isChecked) {
        this.item = item;
        this.id = id;
        this.isChecked = isChecked;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
