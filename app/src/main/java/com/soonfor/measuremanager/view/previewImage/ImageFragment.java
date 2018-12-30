package com.soonfor.measuremanager.view.previewImage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.soonfor.measuremanager.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Soonfor on 2017/5/27.
 */

public class ImageFragment extends Fragment {


    private static final String IMAGE_URL = "image";
    private static final String DEFAULT_IMAGEID = "default_imageid";
    ImageView image;
    private String imageUrl;
    private int defaultId;//默认图片id

    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance(String param1, int defaultImageId) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, param1);
        args.putInt(DEFAULT_IMAGEID, defaultImageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(IMAGE_URL);
            defaultId = getArguments().getInt(DEFAULT_IMAGEID, R.mipmap.zanuw);
        }
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        image = (ImageView) view.findViewById(R.id.image);
        //Glide.with(getContext()).load(imageUrl).into(image);

        /**
         *
         * 1.1图片质量分类

         安卓图片显示的质量配置主要分为四种:

         ARGB_8888 :32位图,带透明度,每个像素占4个字节
         ARGB_4444 :16位图,带透明度,每个像素占2个字节
         RGB_565 :16位图,不带透明度,每个像素占2个字节
         ALPHA_8 :32位图,只有透明度,不带颜色,每个像素占4个字节
         (A代表透明度,RGB代表红绿蓝:即颜色)

         1.2图片默认质量

         Picasso的默认质量是 ARGB_8888
         Glide的默认质量则为 RGB_565
         *
         * **/

        //Glide.with(getContext()).load(imageUrl).placeholder(R.mipmap.soonfor_logo4).error(R.mipmap.soonfor_logo4).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(image);
        try {
            if(imageUrl==null||imageUrl.equals("")){
                image.setImageResource(defaultId);
            }else if (imageUrl.toLowerCase().contains("http")) {
                //网络图片
                if(defaultId == R.drawable.avatar_default){
                    Picasso.with(getContext())
                            .load(imageUrl)
                            .placeholder(defaultId)
                            .error(defaultId)
                            .into(image);
                }else {
                    Picasso.with(getContext())
                            .load(imageUrl)
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                            .error(defaultId)
                            .into(image);
                }
            } else {
                //本地文件
                File file = new File(imageUrl);
                Picasso.with(getContext())
                        .load(file)
                        .error(defaultId)
                        .into(image);
            }
        } catch (Exception e) {
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
