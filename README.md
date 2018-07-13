# PopularMovies - Stage 1

![Screenshot](https://user-images.githubusercontent.com/892302/42668034-6334e49c-8603-11e8-8afc-88f66a441e87.gif)

---

## To Add API Key:

### Option 1: Create "sensitive_data.txt" file

Something with key-value pairs like:

`theMovieDbApiKey = "54215e8745_YOUR_API_KEY_56987gh7"`

### Option 2: Edit gradle.build (Module: app) file

In this part:

```java
//TODO: ADD YOUR API HERE!
 if (noKeyFound == theMovieDbApiKey) {
     theMovieDbApiKey = '"REVIEWERS_THEMOVIEDBAPIKEY_GOES_HERE"'
 }
 ```

 ---


## What works:

1. Retrofit for json
2. Picasso for images
3. Collapsing Toolbar in detail view
4. Auto-fitting poster columns in RecyclerView

## Still to-do:

5. Loading additional pages
6. Choosing image sizes
7. Generate palette based on loaded image