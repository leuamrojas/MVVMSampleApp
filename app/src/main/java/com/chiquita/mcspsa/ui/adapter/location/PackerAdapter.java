package com.chiquita.mcspsa.ui.adapter.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.ui.adapter.CommonAdapterListener;
import com.chiquita.mcspsa.core.ui.widgets.CircleImageView;
import com.chiquita.mcspsa.data.model.location.CorePackerEntity;

import java.util.ArrayList;
import java.util.List;

public class PackerAdapter extends RecyclerView.Adapter<PackerAdapter.ObjectViewHolder>
        implements CommonAdapterListener<CorePackerEntity>, Filterable {

    private Context context;
    private List<CorePackerEntity> list;
    private List<CorePackerEntity> listFiltered;
    private CommonAdapterListener listener;
    private boolean isMultiSelectionEnabled;

    public PackerAdapter(Context context, List<CorePackerEntity> list, CommonAdapterListener listener, boolean isMultiSelectionEnabled) {
        this.context = context;
        this.listener = listener;
        this.list = list;
        this.listFiltered = list;

        this.isMultiSelectionEnabled = isMultiSelectionEnabled;
    }

    @Override
    public PackerAdapter.ObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item_location_adapter, parent, false);

        return new PackerAdapter.ObjectViewHolder(itemView, this);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(PackerAdapter.ObjectViewHolder holder, final int position) {
        final CorePackerEntity row = listFiltered.get(position);

        holder.txv_description.setText(row.getCCR_DESCRIPTION());
        holder.iv_letter.setImageResource(R.drawable.cate);

        holder.mItem = row;
        holder.setChecked(holder.mItem.isSelected());

    }

    @Override
    public int getItemCount() {
        return listFiltered == null ? 0 : listFiltered.size();
    }

    @Override
    public void onSelected(CorePackerEntity item) {
        if (!isMultiSelectionEnabled) {
            for (CorePackerEntity selectableItem : list) {
                if (!selectableItem.equals(item)
                        && selectableItem.isSelected()) {
                    selectableItem.setSelected(false);
                } else if (selectableItem.equals(item)
                        && item.isSelected()) {
                    selectableItem.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        listener.onSelected(item);
    }

    @Override
    public int getItemViewType(int position) {
        if (isMultiSelectionEnabled) {
            return PackerAdapter.ObjectViewHolder.MULTI_SELECTION;
        } else {
            return PackerAdapter.ObjectViewHolder.SINGLE_SELECTION;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listFiltered = list;
                } else {
                    List<CorePackerEntity> filteredList = new ArrayList<>();
                    for (CorePackerEntity row : list) {
                        if (row.getCCR_DESCRIPTION().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    listFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listFiltered = (ArrayList<CorePackerEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ObjectViewHolder extends RecyclerView.ViewHolder {

        public static final int MULTI_SELECTION = 2;
        public static final int SINGLE_SELECTION = 1;

        public TextView txv_tittle, txv_description;
        public CircleImageView iv_letter;
        public CardView cv_background;
        CommonAdapterListener itemSelectedListener;
        CorePackerEntity mItem;

        public ObjectViewHolder(View view, CommonAdapterListener listener) {
            super(view);
            this.itemSelectedListener = listener;

            txv_tittle = view.findViewById(R.id.txv_tittle);
            txv_description = view.findViewById(R.id.txv_description);
            iv_letter = view.findViewById(R.id.iv_letter);
            cv_background = itemView.findViewById(R.id.card_view);

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            iv_letter.setColorFilter(filter);

            cv_background.setOnClickListener(viewS -> {
                if (mItem.isSelected() && getItemViewType() == MULTI_SELECTION) {
                    setChecked(false);
                } else {
                    setChecked(true);
                }
                listener.onSelected(mItem);
            });

        }

        public void setChecked(boolean value) {
            if (value) {
                //txv_tittle.setTextColor(context.getResources().getColor(R.color.white));
                cv_background.setCardBackgroundColor(Color.parseColor("#4A7393"));
                iv_letter.setColorFilter(null);

            } else {
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                iv_letter.setColorFilter(filter);
                cv_background.setCardBackgroundColor(Color.WHITE);
                //txv_tittle.setTextColor(context.getResources().getColor(R.color.black));
            }

            mItem.setSelected(value);
        }
    }
}
