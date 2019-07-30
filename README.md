# Pinterest

## Usage
#### Using Kotlin Extensions
```java
imageView.placeholder = resources.getDrawable(R.drawable.placeholder)
imageView.source = imageUrl // Url or drawable
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
     .resize(300, 300) // Resize image
     .disableCache() // Disable cache. By default its enabled
     .source(imageUrl)
     .loadImage(imageView)
