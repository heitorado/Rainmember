# Rainmember
*Remember remember, the rain of november* - A smart and helpful weather forecast Android app, written in Kotlin.

### What is Rainmember?
Rainmember is an Android app that shows you weather forecasts - but in a smart way. It can show you a clear comparative of weather between your current city and another one you choose and store/display weather data so you can plan yourself better. It can also recommend you clothing suitable for the day when your alarm rings, and remember you to dress appropriately via notifications.

### Who can use?
Anyone interested in a weather app integrated with an alarm app that, combined, can give you helpful hints and data about weather, the life, and everything.

### Haven't we been there, done that?
One of our inspirations is the [Swackett](https://sweaterjacketorcoat.com/apps/) app, that also gives us weather forecasts in a funny and different way, but it is for iOS only. Also, a weather forecast app really isn't much of a novelty. _HOWEVER_, we believe we can have an unique weather/alarm app here that shows you data in a more friendly way, aiming to be practical and help you make most of your day with clever insights, instead of just spitting out numbers and cloud/sun icons.

### Okay, but how does this work?
Let's put the cards on the table. We are a enhanced alarm app for your weathery needs.

First of all, I suppose we should say that we will consume the [OpenWeatherMap API](https://openweathermap.org/). Here is a return [example JSON](https://samples.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22) that we will work on.

We aim for simplicity. The app consists on one main screen with two tabs: *Alarms*  and *Dashboard*.

* **Alarms:** Here you can set up your favorite hours of your daily life to trigger some awesome music coupled with plenty of useful and customizable info about the weather of your location of choice. If you're not an early bird, you can choose to only display you a friendly and much more silent notification with the info you want.
* **Dashboard:** Here is where the fun happens. From this screen you can configure what will appear on your configured alarms/notifications. We offer a broad set of pre-made components to attend your forecasting urges:
  - 5 days temperature variation graph to a location of choice
  - Recommendation on how to dress for the weather for the given location
  - Umbrella or not umbrella? That is the question.
  - Recommendation on sunscreen or not, for that extra cancer prevention
  - Temperature comparative between two places of choice.
  - Suggestions on how to live our life: because we couldn't care more!
    - Good day to play with kite?
    - Good day to go to the beach?
    - Good day to stay at home with a ice cream bucket and Netflix movies?
    - and more!
   - Also jokes and daily quotes. Developers are funny people, sometimes. 

### Who are the evil minds behind all this?
We are two undergraduate students:
- [heitorado](https://github.com/heitorado), who takes care of UI, Services, and crappy puns.
- [rafaelmotaalves](https://github.com/rafaelmotaalves), who takes care of Database, System Architecture and filtering bad ideas.

But all in all, everybody does a little bit of this and a little bit of that.
