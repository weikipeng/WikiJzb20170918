package com.jzb.android.ui.recycler;

/**
 * Created by wikipeng on 2017/9/27.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class SimpleStringAdapter extends RecyclerView.Adapter<SimpleStringAdapter.ViewHolder> {
    private int               mBackground;
    private ArrayList<String> mValues;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public String   mBoundString;
        public TextView mTextView;

        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }

    public String getValueAt(int position) {
        return mValues.get(position);
    }

    public SimpleStringAdapter(Context context, String[] strings) {
        TypedValue val = new TypedValue();
        if (context.getTheme() != null) {
            context.getTheme().resolveAttribute(
                    android.R.attr.selectableItemBackground, val, true);
        }
        mBackground = val.resourceId;
        mValues = new ArrayList<String>();
        Collections.addAll(mValues, strings);
    }

    public void swap(int pos1, int pos2) {
        String tmp = mValues.get(pos1);
        mValues.set(pos1, mValues.get(pos2));
        mValues.set(pos2, tmp);
        notifyItemRemoved(pos1);
        notifyItemInserted(pos2);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder h = new ViewHolder(new TextView(parent.getContext()));
        h.mTextView.setMinimumHeight(128);
        h.mTextView.setPadding(20, 0, 20, 0);
        h.mTextView.setFocusable(true);
        h.mTextView.setBackgroundResource(mBackground);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 5;
        lp.topMargin = 20;
        lp.bottomMargin = 15;
        h.mTextView.setLayoutParams(lp);
        return h;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mBoundString = mValues.get(position);
        int backgroundColor = getBackgroundColor(position);
        holder.mTextView.setText(position + ":" + mValues.get(position));
        holder.mTextView.setMinHeight((200 + mValues.get(position).length() * 10));
        holder.mTextView.setBackgroundColor(backgroundColor);
        if (backgroundColor == Color.BLACK) {
            holder.mTextView.setTextColor(Color.WHITE);
        }else{
            holder.mTextView.setTextColor(Color.BLACK);
        }
    }

    private int getBackgroundColor(int position) {
        switch (position % 4) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.RED;
            case 2:
                return Color.DKGRAY;
            case 3:
                return Color.BLUE;
        }
        return Color.TRANSPARENT;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}