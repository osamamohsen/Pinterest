# Pinterest

# MVVM + LiveData + Miva Image Loader

Languages, libraries and tools used

- [Kotlin](https://kotlinlang.org/)
- [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid) 
- Dependency Injection with [Dagger 2](http://google.github.io/dagger/)
- [Room](https://developer.android.com/topic/libraries/architecture/room.html) Persistence Library
- Network communication with [Retrofit 2](http://square.github.io/retrofit/)


# Miva Image Loader

#### Using Kotlin Extensions
```java
imageView.placeholder = resources.getDrawable(R.drawable.placeholder)
imageView.source = imageUrl // Url or drawable resource
```
#### Using Mural Builder
```java
Mural.with(this)
     .placeholder(R.drawable.placeholder)
     .source(imageUrl)
     .loadImage(imageView)
```

##### Other options
```java
Mural.with(this)
     .placeholder(R.drawable.placeholder)
     .resize(300, 300) // here resize image default (500 x 500)
     .disableCache() // Disable cache. By default its enabled
     .setMaxCapacityCache(your_float_here) // default memoryClass * 1024 * 1024 as float
     .source(imageUrl)
     .loadImage(imageView)
