# PopularMovies - Stage 2

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
5. Outlining the togglable title text

## Still to-do:

1. Database for offline viewing
2. Mark/Unmark favorites
3. Toggle display only favorites
4. Read reviews
5. Play trailers

## Planned extras:
1. Loading additional pages
2. Increase/Decrease poster image sizes
3. Generate palette based on loaded image