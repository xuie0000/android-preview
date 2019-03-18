package com.xuie.android.ui.palette;


import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.xuie.android.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class PaletteFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    private int position;
    private static int[] drawables = {R.mipmap.one, R.mipmap.two, R.mipmap.four, R.mipmap.three, R.mipmap.five};

    public static PaletteFragment newInstance(int position) {
        PaletteFragment f = new PaletteFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams
                .MATCH_PARENT);

        FrameLayout fl = new FrameLayout(Objects.requireNonNull(getActivity()));
        fl.setLayoutParams(params);
        fl.setBackgroundResource(drawables[position]);
        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics());

        TextView v = new TextView(getActivity());
        params.setMargins(margin, margin, margin, margin);
        v.setLayoutParams(params);
        v.setLayoutParams(params);
        v.setGravity(Gravity.BOTTOM);
        v.setText("CARD " + (position + 1));

        fl.addView(v);
        return fl;
    }

    public static int getBackgroundBitmapPosition(int selectViewPagerItem) {
        return drawables[selectViewPagerItem];
    }

}
