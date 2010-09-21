/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.apis.animation;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import android.animation.Animator;
import android.animation.ObjectAnimator;
import com.example.android.apis.R;

import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * This application demonstrates how to use LayoutTransition to automate transition animations
 * as items are removed from or added to a container.
 */
public class LayoutAnimations extends Activity {

    private int numButtons = 1;
    ViewGroup container = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animations);

        container = new FixedGridLayout(this.getApplicationContext());
        ((FixedGridLayout)container).setCellHeight(50);
        ((FixedGridLayout)container).setCellWidth(100);
        final LayoutTransition transitioner = new LayoutTransition();
        container.setLayoutTransition(transitioner);

        ViewGroup parent = (ViewGroup) findViewById(R.id.parent);
        parent.addView(container);
        Button addButton = (Button) findViewById(R.id.addNewButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button newButton = new Button(getApplicationContext());
                newButton.setText("Click To Remove " + (numButtons++));
                newButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        container.removeView(v);
                    }
                });
                container.addView(newButton, Math.min(1, container.getChildCount()));
            }
        });

        CheckBox customAnimCB = (CheckBox) findViewById(R.id.customAnimCB);
        customAnimCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                long duration;
                if (isChecked) {
                    transitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
                    transitioner.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
                    setupAnimations(transitioner);
                    duration = 500;
                } else {
                    transitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 0);
                    transitioner.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 0);
                    transitioner.setAnimator(LayoutTransition.APPEARING, null);
                    transitioner.setAnimator(LayoutTransition.DISAPPEARING, null);
                    transitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, null);
                    transitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, null);
                    duration = 300;
                }
                transitioner.setDuration(duration);
            }
        });
    }

    private void setupAnimations(LayoutTransition transition) {
        // Changing while Adding
        PropertyValuesHolder<Integer> pvhLeft =
                new PropertyValuesHolder<Integer>("left", 0, 1);
        PropertyValuesHolder<Integer> pvhTop =
                new PropertyValuesHolder<Integer>("top", 0, 1);
        PropertyValuesHolder<Integer> pvhRight =
                new PropertyValuesHolder<Integer>("right", 0, 1);
        PropertyValuesHolder<Integer> pvhBottom =
                new PropertyValuesHolder<Integer>("bottom", 0, 1);
        PropertyValuesHolder<Float> pvhScaleX =
                new PropertyValuesHolder<Float>("scaleX", 1f, 0f, 1f);
        PropertyValuesHolder<Float> pvhScaleY =
                new PropertyValuesHolder<Float>("scaleY", 1f, 0f, 1f);
        final ObjectAnimator changeIn =
                new ObjectAnimator(transition.getDuration(LayoutTransition.CHANGE_APPEARING),
                        this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhScaleX, pvhScaleY);
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);
        changeIn.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setScaleX(1f);
                view.setScaleY(1f);
            }
        });

        // Changing while Removing
        Keyframe kf0 = new Keyframe(0f, 0f);
        Keyframe kf1 = new Keyframe(.9999f, 360f);
        Keyframe kf2 = new Keyframe(1f, 0f);
        PropertyValuesHolder<Keyframe> pvhRotation =
                new PropertyValuesHolder<Keyframe>("rotation", kf0, kf1, kf2);
        final ObjectAnimator changeOut =
                new ObjectAnimator(transition.getDuration(LayoutTransition.CHANGE_DISAPPEARING),
                        this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhRotation);
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);
        changeOut.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotation(0f);
            }
        });

        // Adding
        ObjectAnimator<Float> animIn =
                new ObjectAnimator<Float>(transition.getDuration(LayoutTransition.APPEARING),
                        null, "rotationY", 90f, 0f);
        transition.setAnimator(LayoutTransition.APPEARING, animIn);
        animIn.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotationY(0f);
            }
        });

        // Removing
        ObjectAnimator<Float> animOut =
                new ObjectAnimator<Float>(transition.getDuration(LayoutTransition.DISAPPEARING),
                        null, "rotationX", 0f, 90f);
        transition.setAnimator(LayoutTransition.DISAPPEARING, animOut);
        animIn.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotationX(0f);
            }
        });

    }
}