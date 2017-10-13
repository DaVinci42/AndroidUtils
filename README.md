```groovy
compile 'com.davinci42:android-utils:0.7.1'
```


* `GradientMotionDrawable`

  ```kotlin
  val d = AnimationDrawable()
          .gradientMotion(1000, arrayListOf(
                  GradientDrawable(GradientDrawable.Orientation.BL_TR,
                          intArrayOf(Color.parseColor("#f44336"), Color.parseColor("#F4511E"))),
                  GradientDrawable(GradientDrawable.Orientation.BL_TR,
                          intArrayOf(Color.parseColor("#0288D1"), Color.parseColor("#C2185B"))),
                  GradientDrawable(GradientDrawable.Orientation.BL_TR,
                          intArrayOf(Color.parseColor("#424242"), Color.parseColor("#673AB7")))
          ))
  imageView.setImageDrawable(d)
  d.start()
  ```

  `AnimationDrawable` , elegant way to show the color motion background on the Instagram login page

* `CommandExecutor`

  Executing shell command on Android, using RxJava

  ```kotlin
  CommandExecutor(this, command, false)
                  .execute()
  ```

  This will show the output line by line as the text of a snackbar, which is why it requires activity as a paremeter, cause the snackbar attached to the contentView.

  Full constructor:

  ```kotlin
  CommandExecutor(activity: Activity, command: String, customObserver: Observer<String>? = null, requireRoot: Boolean = false)
  ```

  You could implement your own `Observer<String>` to handle the output.

  `requireRoot` is for geeks

* `RoundImageView`

* `DividerItemDecoration` for `RecyclerView`

* `ScreenShotFragment` using `MediaProjection` Api