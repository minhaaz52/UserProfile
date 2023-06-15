# UserProfile
 Here, we are fetching details of a random user and displaying it on screen.


# some of the dependencies which needs to be added for this app. (Add in build.gradle (Module:app))

    implementation("com.android.volley:volley:1.2.1")     // to retrieve data from web url
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'   // for swipe down to refresh function
    implementation 'com.squareup.picasso:picasso:2.71828'   // to change profile picture using url


# Here we are using "https://randomuser.me/"  website for fetching details of random users.