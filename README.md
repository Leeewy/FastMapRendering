# Google Maps Util for Android

## Introduction

This is library for everyone who wants to show many markers on screen. You must only set a value for variable mManyMarkers.
If you want to show only markers on screen and have faster rendering you should set it to 'true'.
When you want to use normal lib, you should set it to false. But be carefull, all markers will be rendered and it will be very slowly.

## Used libs

It used [Android Maps Utils API](https://github.com/bobzilladev/android-maps-utils) and [Google Maps Android
API](http://developer.android.com/google/play-services/maps.html).

## Comments

Do not change switch while markers are rendering. If you do that, old markers may stay on screen.
(it is only example and I don't want to waste time for fix it).