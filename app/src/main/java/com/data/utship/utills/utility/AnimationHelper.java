package com.data.utship.utills.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.data.utship.R;


public class AnimationHelper {

    private static ObjectAnimator objectAnimator;
    private static AnimatorSet mAnimationSet;

    private AnimationHelper() {
    }


    public static float getTranslationY(View view) {
        return AnimatorProxy.NEEDS_PROXY ? AnimatorProxy.wrap(view).getTranslationY() : Honeycomb.getTranslationY(view);
    }

    public static void setTranslationY(View view, float translationY) {
        if (AnimatorProxy.NEEDS_PROXY) {
            AnimatorProxy.wrap(view).setTranslationY(translationY);
        } else {
            Honeycomb.setTranslationY(view, translationY);
        }
    }


    private static final class Honeycomb {


        static float getTranslationY(View view) {
            return view.getTranslationY();
        }

        static void setTranslationY(View view, float translationY) {
            view.setTranslationY(translationY);
        }
    }


    public static void rotationY(final View viewToFlip) {
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(viewToFlip, "rotationY", 0f, 360f);
        rotationY.setDuration(2000);
        rotationY.start();

    }


    public static void fadeInOut(View mView) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(mView, "alpha", 1f, 0f);
        fadeOut.setDuration(500L);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(mView, "alpha", 0f, 1f);
        fadeIn.setDuration(500L);

        mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeIn).after(fadeOut);

        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mAnimationSet != null)
                    mAnimationSet.start();
            }
        });
        mAnimationSet.start();
    }

    public static void cancelAnimationSet(View mView) {
        if (mAnimationSet != null && mAnimationSet.isRunning()) {
            mAnimationSet.cancel();
            mAnimationSet = null;
            mView.clearAnimation();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mView.setStateListAnimator(null);
            }

        }
    }

    private static void fadeIn(final View mButton) {
        objectAnimator = ObjectAnimator.ofFloat(mButton, "alpha", 0f, 1f);
        objectAnimator.setDuration(500L);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fadeOut(mButton);
            }
        });
        objectAnimator.start();
    }

    public static void fadeOut(final View mButton) {
        //mButton.clearAnimation();
        objectAnimator = ObjectAnimator.ofFloat(mButton, "alpha", 1f, 0f);
        objectAnimator.setDuration(500L);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fadeIn(mButton);
            }
        });
        objectAnimator.start();
    }

    public static void cancelAnimator(View mButton) {
        if (objectAnimator != null && objectAnimator.isRunning()) {
            objectAnimator.cancel();
            objectAnimator = null;
            mButton.clearAnimation();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mButton.setStateListAnimator(null);
            }

        }
    }





    // To reveal a previously invisible view using this effect:
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void show(final View view, long duration) {
        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight()) / 17;
        int cy = (view.getTop() + view.getBottom()) / 17;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                0, finalRadius);
        anim.setDuration(duration);

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    // To hide a previously visible view using this effect:
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void hide(final AppCompatActivity mActivity, final View view, long duration) {

        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight()) / 17;
        int cy = (view.getTop() + view.getBottom()) / 17;

        // get the initial radius for the clipping circle
        int initialRadius = view.getWidth();

        // create the animation (the final radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                initialRadius, 0);
        anim.setDuration(duration);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
                mActivity.finish();
            }
        });

        // start the animation
        anim.start();
    }
    public static void slideLeft(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit);
    }

    public static void slideRight(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void slideDown(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_down_enter, R.anim.slide_down_exit);
    }

    public static void slideUp(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_up_enter, R.anim.slide_up_exit);
    }

    public static void zoom(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }

    public static void fade(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit);
    }

    public static void windmill(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.windmill_enter, R.anim.windmill_exit);
    }

    public static void spin(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.spin_enter, R.anim.spin_exit);
    }

    public static void diagonal(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.diagonal_right_enter, R.anim.diagonal_right_exit);
    }

    public static void split(Context context){
        ((Activity) context).overridePendingTransition(R.anim.split_enter, R.anim.split_exit);
    }

    public static void shrink(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.shrink_enter, R.anim.shrink_exit);
    }

    public static void card(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.card_enter, R.anim.card_exit);
    }

    public static void inAndOut(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.in_out_enter, R.anim.in_out_exit);
    }

    public static void swipeLeft(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.swipe_left_enter, R.anim.swipe_left_exit);
    }

    public static void swipeRight(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.swipe_right_enter, R.anim.swipe_right_exit);
    }
}
