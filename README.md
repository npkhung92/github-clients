# github-clients
This project is about a minimalistic GitHub client, which allows user to search through GitHub users and view their profiles.


<img src="https://github.com/npkhung92/github-clients/blob/master/github-images/screenshot2.jpg" width="400">
<img src="https://github.com/npkhung92/github-clients/blob/master/github-images/screenshot1.jpg" width="400">


## Functionality
- Search Github users with on-the-fly searching
- See Github user detail information such as number of followers, following, location, company & biography
## Apply Clean Architect
![Clean Architect Android](https://github.com/npkhung92/github-clients/blob/master/github-images/clean_architecture_reloaded_layers.png)

This project is inspired by Clean Architect with 3 layers:
1 Domain:
- UseCase to handle business with repository contract
- Models
- Repository
2 Data:
- Mapper to map from DTO to Model
- Data source from remote (get by repository implementer)
3 Presentation:
- All related to User interface
## Frameworks & Libraries
- Databinding & Viewbinding
- Jetpack Navigation
- Paging 3
- Hilt dependency injection
- Retrofit & Gson Converter
- Kotlin Coroutines
- Glide
- Stetho (API Debugging)
