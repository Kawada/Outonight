package edu.fst.m2.ipii.outonight.ui.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.model.ui.DrawerItem;

public class DrawerItemViewHolder {

    private final Context context;
    private View drawerItemView;
    @InjectView(R.id.label_textview) TextView labelTextView;
    @InjectView(R.id.icon_imageview) ImageView iconImageView;

    public DrawerItemViewHolder(Context context) {
        this.context = context;
        createView();
    }

    private void createView() {
        drawerItemView = LayoutInflater.from(context).inflate(R.layout.drawer_item, null);
        ButterKnife.inject(this, drawerItemView);
    }

    public void updateView(DrawerItem drawerItem) {
        labelTextView.setText(drawerItem.getLabel());
        iconImageView.setImageResource(drawerItem.getIconResource());
    }

    public View getView() {
        return drawerItemView;
    }
}


