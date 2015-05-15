package edu.fst.m2.ipii.outonight.model.ui;

/**
 * Created by Dimitri on 14/05/2015.
 */
public class DrawerItem {

    private int iconResource;
    private String label;

    public DrawerItem(int iconResource, String label) {
        this.iconResource = iconResource;
        this.label = label;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
