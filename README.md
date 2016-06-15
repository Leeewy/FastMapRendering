# FastMapRendering for Android

## Introduction

This is open-source project for everyone who wants to show many markers on screen and doesn't want to wait for rendering them.

## Used libs

It used [Android Maps Utils API](https://github.com/bobzilladev/android-maps-utils) and [Google Maps Android
API](http://developer.android.com/google/play-services/maps.html).

## How to do it

First, you must extend ClusterManager class to be sure, that markers will be loaded when you swipe on map. You can do it like in this example:

```java
public class FastClusterManager<T extends ClusterItem> extends ClusterManager {
    private CameraPosition mPreviousCameraPosition;

    private boolean mManyMarkers;

    public FastClusterManager(Context context, GoogleMap map) {
        super(context, map);
    }

    public FastClusterManager(Context context, GoogleMap map, boolean mManyMarkers) {
        this(context, map);
        this.mManyMarkers = mManyMarkers;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if(mManyMarkers) {
            if (mPreviousCameraPosition != null && mPreviousCameraPosition.zoom == cameraPosition.zoom) {
                cluster();
            }
            mPreviousCameraPosition = cameraPosition;
        }

        super.onCameraChange(cameraPosition);
    }
}
```

Next step is to write your own DefaultClusterRenderer class. All you need to do is copy all code from original class from [Android Maps Utils API](https://github.com/googlemaps/android-maps-utils/blob/master/library/src/com/google/maps/android/clustering/view/DefaultClusterRenderer.java) and add mManyMarkers flag. You should set it while creating DefaultClusterRenderer object.

Now you must change condition for run RenderTask:

```java
if (clusters.equals(MyDefaultClusterRenderer.this.mClusters) && ((mManyMarkers && mMapZoom - mZoom != 0) || !mManyMarkers)) {
    mCallback.run();
    return;
}
```

In MarkerModifier class (add() method) you must to check if you want to add all existing markers:

```java
public void add(boolean priority, CreateMarkerTask c) {
    lock.lock();
    sendEmptyMessage(BLANK);
    if (priority) {
        mOnScreenCreateMarkerTasks.add(c);
    } else if(!mManyMarkers) {
        mCreateMarkerTasks.add(c);
    }
    lock.unlock();
}
```

The last thing to do is change performNextTask() method like this:

```java
private void performNextTask() {
    if (!mOnScreenRemoveMarkerTasks.isEmpty()) {
        removeMarker(mOnScreenRemoveMarkerTasks.poll());
    } else if (!mAnimationTasks.isEmpty()) {
        mAnimationTasks.poll().perform();
    } else if (!mOnScreenCreateMarkerTasks.isEmpty()) {
        mOnScreenCreateMarkerTasks.poll().perform(this);
    } else if (!mCreateMarkerTasks.isEmpty() && !mManyMarkers) {
        mCreateMarkerTasks.poll().perform(this);
    } else if (!mRemoveMarkerTasks.isEmpty()) {
        removeMarker(mRemoveMarkerTasks.poll());
    }
}
```

## Example
As you can see in app, when you try to show more than 500 markers on small area and use normal lib, it takes almost one minute to finish rendering
and looks like on the image below (10 000 markers).
![Many markers with normal lib image](images/slow_many_markers.png)

With deleting rendering outside the screen it takes only few seconds.

## Comments

Do not change switch while markers are rendering. If you do that, old markers may stay on screen.
(it is only example and I don't want to waste time for fix it).

Remember to change Google API key on yours in keys.xml file!