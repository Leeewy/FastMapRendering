# Google Maps Util for Android

## Introduction

This is library for everyone who wants to show many markers on screen. We must only set variable mManyMarkers.
If we want to show only markers on screen and have faster rendering we should set it to 'true'.
When we want to use normal lib, we should set it to false. But be carefull, all markers will be rendered,
but it will be very slowly.

## Using

It used [Android Maps Utils API](https://github.com/bobzilladev/android-maps-utils) and [Google Maps Android
API](http://developer.android.com/google/play-services/maps.html).

## Comments

Do not change switch while markers are rendering. If you do that, old markers may stay on screen.
(it is only example and I don't want to waste time for fix it).