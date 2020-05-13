package com.chiquita.mcspsa.ui.adapter.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.ui.adapter.CommonAdapterListener;
import com.chiquita.mcspsa.data.model.CoreMenuEntity;

import java.util.List;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.DashboardViewHolder> {

    private Context context;
    private List<CoreMenuEntity> menuItemList;
    private CommonAdapterListener<CoreMenuEntity> listener;

    public class DashboardViewHolder extends RecyclerView.ViewHolder {

        ImageView img_dashboard_item_logo;
        TextView txv_app_description, txv_object_description;

        public DashboardViewHolder(View view) {
            super(view);

            txv_object_description = itemView.findViewById(R.id.txv_object_description);
            img_dashboard_item_logo = itemView.findViewById(R.id.img_dashboard_item_logo);
            txv_app_description = itemView.findViewById(R.id.txv_app_description);

            view.setOnClickListener(view1 -> listener.onSelected(menuItemList.get(getAdapterPosition())));
        }
    }

    public MainMenuAdapter(Context context, List<CoreMenuEntity> ItemList, CommonAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.menuItemList = ItemList;
    }

    @Override
    public DashboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_main_menu, parent, false);

        return new DashboardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DashboardViewHolder holder, final int position) {
        final CoreMenuEntity row = menuItemList.get(position);

        holder.img_dashboard_item_logo.setImageDrawable(getMenuDrawable(row.getObjectCode()));

        if (row != null) {
            if (!row.getObjectCode().toLowerCase().isEmpty() && !row.getObjectName().toLowerCase().isEmpty()) {
                holder.txv_object_description.setText(row.getObjectName().toLowerCase());
                holder.txv_app_description.setText(row.getObjectCode().toLowerCase());
            }
        }
    }

    private Drawable getMenuDrawable(String object) {
        switch (object) {

            case "OPLT3050":
                return context.getResources().getDrawable(R.drawable.ic_inventory);

            case "OPLT3060":
                return context.getResources().getDrawable(R.drawable.ic_vessel_download);

            case "OPLT3140":
                return context.getResources().getDrawable(R.drawable.ic_vessel_load);

            case "OPLT3150":
                return context.getResources().getDrawable(R.drawable.ic_banana_ltr);

            case "OPLT3090":
                return context.getResources().getDrawable(R.drawable.ic_dispatch);

            case "OPLT3120":
                return context.getResources().getDrawable(R.drawable.ic_scan);

            case "MAFS0040":
                return context.getResources().getDrawable(R.drawable.ic_banana_ltr);

            case "MAFS0050":
                return context.getResources().getDrawable(R.drawable.ic_banana_ltr);

            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

}
