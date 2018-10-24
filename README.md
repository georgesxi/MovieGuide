# MovieGuide
A movie catalog that uses the TMDB API to retrieve popular movies, search for specific movies and more..!

# Prerequisites

1) In order to run the app you need an API key from TMDB 
(get one from here -> https://developers.themoviedb.org/3/getting-started/introduction)

2) Insert your TMDB API key in the `gradle.properties` file in the field:
`apiKey = "insert_your_api_key_here"`

# Features

•	Users can check the list of the most popular movies using an infinite recycler view.

•	Each Movie item can be opened and provide further details about the movie.

•	Users can mark a movie as a favorite as he continues browsing or while reading the Movie details.

•	Search box where users can search as they type

•	Users can pull down to refresh the movie list at any time

•	App handles gracefully the loss of network connection at most stages 

# Gradle

•	Target android SDK was set to 28 and minSDK was set to 23. 

•	API key was included in the build file instead of hardcoding it in the app. 


# Coding

•	Whole project was written in Kotlin using most of the latest features.

•	SharedPrefs are used for saving favorite movies

•	Koin was used for Dependency Injection.

•	Glide library was used for image fetching

•	Retrofit 2 was used to handle network calls

•	RxJava 2 was used to handle the asynchronous calls on the TMDB API

•	Whole project was structured using MVVM / Clean architecture 

•	Some features the UI uses: Coordinator Layout, Collapsing Toolbar, SwipeRefresh, RecyclerView, CardView
