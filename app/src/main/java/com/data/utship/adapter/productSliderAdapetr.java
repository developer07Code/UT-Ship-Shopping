package com.data.utship.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.data.utship.R;
import com.data.utship.model.ProductDetailBean;

import java.util.List;

public class productSliderAdapetr extends PagerAdapter {
    Context c;
    RequestOptions options;
    LayoutInflater layoutInflater;
    private List<ProductDetailBean.DataDTO.ImgsDTO> _imagePaths;
    private LayoutInflater inflater;

    public productSliderAdapetr(Context c, List<ProductDetailBean.DataDTO.ImgsDTO> imagePaths) {
        this._imagePaths = imagePaths;
        this.c = c;

        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        options = new RequestOptions().centerCrop().placeholder(R.drawable.app_logo).error(R.drawable.app_logo);


    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imgDisplay, imgDisplay1;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.beanviewpager, container,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.view_pager_img);

        Glide.with(c).load(_imagePaths.get(position).getImg()).apply(options).into(imgDisplay);


//        Glide.with(c).load(_imagePaths.get(0).getImg()).apply(options).into(imgDisplay);
//        Glide.with(c).load(_imagePaths.get(1).getImg()).apply(options).into(imgDisplay1);


        (container).addView(viewLayout);
        return viewLayout;

    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((RelativeLayout) object);

    }


}
